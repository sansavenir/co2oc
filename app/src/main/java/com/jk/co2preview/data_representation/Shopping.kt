package com.jk.co2preview.data_representation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.jk.co2preview.database.DBHandler
import com.jk.co2preview.database.DatabaseItem
import com.jk.co2preview.extras.extras
import java.lang.Math.round


class Shopping(items: MutableList<DatabaseItem>) {
    private var items: MutableList<DatabaseItem> = mutableListOf()

    init {
        this.items = items
    }

    fun add_item(item: DatabaseItem){
        items.add(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun get_date():String{
        val date = items.first().get_date()!!
        return "${date.dayOfMonth}.${date.monthValue}.${date.year}"
    }

    fun get_cost(): Double {
        return round(items.sumByDouble { it.get_price()!!.toDouble() } * 100) / 100.0
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun init_items_from_db(context: Context) {
        val dbHandler = DBHandler(context, null, null, 1)
        for(i in items){
            dbHandler.completeProduct(i)
        }
    }

    fun get_items(): MutableList<DatabaseItem> {
        return items
    }

    fun get_all_origins(): String{
        var res : MutableList<String> = mutableListOf()
        for(i in items){
            i.get_origin()?.let { res.add(it) }
        }
        return res.distinct().joinToString()
    }

    fun get_sum_nutrients(): List<List<Float>> {
        var res : MutableList<Float> = MutableList(8){ 0F }
        for(i in items){
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