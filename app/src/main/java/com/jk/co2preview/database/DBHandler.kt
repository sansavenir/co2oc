package com.jk.co2preview.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.database.getFloatOrNull
import androidx.core.database.getStringOrNull
import java.lang.Character.isDigit

class DBHandler(context: Context, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
//    val dbObj = SQLiteDatabase.openDatabase(DATABASE_NAME.toString(), null, SQLiteDatabase.OPEN_READONLY);


    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun completeProduct(product: DatabaseItem) {
        val name = product.get_name()?.toLowerCase()?.replace("mclass", "m-classic")?.replace("mbud", "m-budget")
        var q : List<CharSequence> = name!!.split(' ')
        q = q.filter {!it.any{it2 -> isDigit(it2)}}
//        var q_str = q.joinToString( "%\" AND $COLUMN_PRODUCTNAME LIKE \"%", "$COLUMN_PRODUCTNAME LIKE \"%", "%\"")
        var q_str = "name like \"%${q.joinToString(" ")}%\""
//        var q_str = "name == \"${q.joinToString(" ")}\""
        val query = "SELECT * FROM $TABLE_PRODUCTS WHERE $q_str ORDER BY ABS(${product.get_price()} - price)"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        var res: DatabaseItem? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            res = get_db_item(cursor)

            cursor.close()
        }

        db.close()
        if (res != null) {
            product.update(res)
        }

    }

    fun getSeasonalProducts(month: Int): MutableList<DatabaseItem> {

        val query = "SELECT * FROM $TABLE_PRODUCTS WHERE season like \'% $month %\'"

        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)

        var products : MutableList<DatabaseItem> = mutableListOf()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                products.add(get_db_item(cursor))
                cursor.moveToNext();
            }
            cursor.close()
        }

        db.close()
        return products
    }

    private fun get_db_item(cursor: Cursor): DatabaseItem {
        val id = Integer.parseInt(cursor.getStringOrNull(0))
        val name : String? = cursor.getStringOrNull(1)
        val desc : String? = cursor.getStringOrNull(2)
        val gen_info : String? = cursor.getStringOrNull(3)
        val nutrients : String? = cursor.getStringOrNull(4)
        val origin : String? = cursor.getStringOrNull(5)
        val price : Float? = cursor.getFloatOrNull(6)
        val orig_price : Float? = cursor.getFloatOrNull(7)
        val link : String? = cursor.getStringOrNull(8)
        val season : String? = cursor.getStringOrNull(9)
        val weight : Float? = cursor.getFloatOrNull(10)
        val co2 : String? = cursor.getStringOrNull(11)

        val product = id?.let { DatabaseItem(id=it, name=name, desc=desc, gen_info=gen_info,
                nutrients=nutrients, origin=origin, price=price, orig_price=orig_price,
                link=link, season=season, null, null, null, null, null, weight=weight, co2=co2) }
        return product
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "full.db"
        val TABLE_PRODUCTS = "items"

        val COLUMN_ID = "id"
        val COLUMN_PRODUCTNAME = "name"
//        val COLUMN_QUANTITY = "quantity"
    }
}