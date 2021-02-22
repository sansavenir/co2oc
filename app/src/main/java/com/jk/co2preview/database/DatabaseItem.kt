package com.jk.co2preview.database

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.jk.co2preview.data_representation.BuyItem
import com.jk.co2preview.extras.extras
import java.time.LocalDate


//@Parcelize
data class DatabaseItem(
    private var id : Int = -1,
    private var name: String? = null,
    private var desc: String? = null,
    private var gen_info: String? = null,
    private var nutrients: String? = null,
    private var origin: String? = null,
    private var price: Float? = null,
    private var orig_price: Float? = null,
    private var link: String? = null,
    private var season: String? = null,
    private var quantity: Float? = null,
    private var old_name: String? = null,
    private var date: LocalDate? = null,
    private var action: Float? = null,
    private var paid_price: Float? = null,
    private var weight: Float? = null,
): Parcelable {

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readSerializable() as? LocalDate,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
    )
    fun get_nutrients(): List<List<Float>>? {
        if (nutrients == null) {
            return null
        }
        val w = this.get_weight()
        val per100 = nutrients!!.split(',').map { it.toFloat() }

        var tot = per100.map { it * w / 100}
        val ext = extras()
        var perc = tot.mapIndexed { ind, it -> it / ext.daily_nutrion[ind] * 100}

        return listOf(per100, tot, perc)
    }

    fun get_weight(): Float {
        var w = 100F
        if(this.weight != null){
            w = 1F
            w *= this.weight!!
        }
        if(this.quantity != null){
            w *= this.quantity!!
        }
        return w
    }

    fun update(buyItem: BuyItem){
        this.quantity = buyItem.quantity
        this.old_name = buyItem.name
        this.date = buyItem.date
        this.action = buyItem.action
        this.paid_price = buyItem.price
    }

    fun get_link(): String? {
        return this.link
    }

    fun get_desc(): String? {
        return this.desc
    }

    fun get_origin(): String? {
        return this.origin
    }

    fun get_price(): Float? {
        return this.paid_price
    }

    fun get_name(): String? {
        return if(this.old_name != null){
            this.old_name
        } else {
            this.name
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeString(gen_info)
        parcel.writeString(nutrients)
        parcel.writeString(origin)
        parcel.writeValue(price)
        parcel.writeValue(orig_price)
        parcel.writeString(link)
        parcel.writeString(season)
        parcel.writeValue(quantity)
        parcel.writeString(old_name)
        parcel.writeSerializable(date)
        parcel.writeValue(action)
        parcel.writeValue(paid_price)
        parcel.writeValue(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DatabaseItem> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun createFromParcel(parcel: Parcel): DatabaseItem {
            return DatabaseItem(parcel)
        }

        override fun newArray(size: Int): Array<DatabaseItem?> {
            return arrayOfNulls(size)
        }
    }
}