package com.jk.co2preview.data_representation

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.jk.co2preview.database.DBHandler
import com.jk.co2preview.database.DatabaseItem
import com.jk.co2preview.extras.extras
import java.lang.Math.round
import java.time.LocalDate


class Shopping(items: MutableList<BuyItem>) {
    private var items: MutableList<BuyItem> = mutableListOf()
    private var dbItems: MutableList<DatabaseItem> = mutableListOf()

    init {
        this.items = items
    }

    fun add_item(item: BuyItem){
        items.add(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun get_date():String{
        val date = items.first().date
        return "${date.dayOfMonth}.${date.monthValue}.${date.year}"
    }

    fun get_cost(): Double {
        return round(items.sumByDouble { it.price.toDouble() } * 100) / 100.0
    }

    fun get_items(): MutableList<BuyItem> {
        return items
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun init_db_items(context: Context) {
        var res : MutableList<DatabaseItem> = mutableListOf()
        val dbHandler = DBHandler(context, null, null, 1)
        for(i in items){
            i.name?.let { dbHandler.findProduct(it)?.let {
                it ->
                it.update(i)
                res.add(it) }
            }
        }
        dbItems = res
    }

    fun get_db_items(): MutableList<DatabaseItem> {
        return dbItems
    }

    fun get_all_origins(): String{
        var res : MutableList<String> = mutableListOf()
        for(i in dbItems){
            i.get_origin()?.let { res.add(it) }
        }
        return res.distinct().joinToString()
    }

    fun get_sum_nutrients(): List<List<Float>> {
        var res : MutableList<Float> = MutableList(8){ 0F }
        for(i in dbItems){
            val nut = i.get_nutrients()
            if(nut == null){
                continue
            }
            for(a in res.indices){
                res[a] += nut!![1][a]
            }
        }
        val ext = extras()
        var perc = res.mapIndexed { ind, it -> it / ext.daily_nutrion[ind] * 100 }
        return listOf(res,perc)
    }

}

//@Parcelize
data class BuyItem(
    val date: LocalDate,
    val name: String?,
    val quantity: Float,
    val action: Float,
    val price: Float,
): Parcelable {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this(
//        TODO("date"),
        parcel.readSerializable() as LocalDate,
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(date)
        parcel.writeString(name)
        parcel.writeFloat(quantity)
        parcel.writeFloat(action)
        parcel.writeFloat(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BuyItem> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun createFromParcel(parcel: Parcel): BuyItem {
            return BuyItem(parcel)
        }

        override fun newArray(size: Int): Array<BuyItem?> {
            return arrayOfNulls(size)
        }
    }
}