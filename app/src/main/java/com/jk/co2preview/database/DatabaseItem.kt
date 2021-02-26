package com.jk.co2preview.database

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
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
    private var co2: String? = null,
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
        parcel.readString(),
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

    fun get_date(): LocalDate? {
        return this.date
    }

    fun update(new: DatabaseItem){
        if(this.id == null){
            this.id = new.id
        }
        if(this.name == null){
            this.name = new.name
        }
        if(this.desc == null){
            this.desc = new.desc
        }
        if(this.gen_info == null){
            this.gen_info = new.gen_info
        }
        if(this.nutrients == null){
            this.nutrients = new.nutrients
        }
        if(this.origin == null){
            this.origin = new.origin
        }
        if(this.price == null){
            this.price = new.price
        }
        if(this.orig_price == null){
            this.orig_price = new.orig_price
        }
        if(this.link == null){
            this.link = new.link
        }
        if(this.season == null){
            this.season = new.season
        }
        if(this.quantity == null){
            this.quantity = new.quantity
        }
        if(this.old_name == null){
            this.old_name = new.old_name
        }
        if(this.date == null){
            this.date = new.date
        }
        if(this.action == null){
            this.action = new.action
        }
        if(this.paid_price == null){
            this.paid_price = new.paid_price
        }
        if(this.weight == null){
            this.weight = new.weight
        }
        if(this.co2 == null){
            this.co2 = new.co2
        }
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

    fun get_co2(): List<Float>? {
        if(this.co2 == null){
            return null
        }
        val w = this.get_weight()
        return this.co2!!.split(',').map { it.toFloat() }.map { it * w / 1000}
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
        parcel.writeString(co2)
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