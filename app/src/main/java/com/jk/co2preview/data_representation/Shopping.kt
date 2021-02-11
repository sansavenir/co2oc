package com.jk.co2preview.data_representation

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.lang.Math.round
import java.time.LocalDate


class Shopping(items: MutableList<BuyItem>) {
    private var items: MutableList<BuyItem> = mutableListOf()

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
    ) {
    }

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
        override fun createFromParcel(parcel: Parcel): BuyItem {
            return BuyItem(parcel)
        }

        override fun newArray(size: Int): Array<BuyItem?> {
            return arrayOfNulls(size)
        }
    }
}