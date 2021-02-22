package com.jk.co2preview.data_representation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.jk.co2preview.database.DatabaseItem
import java.io.BufferedReader
import java.io.FileReader
import java.time.LocalDate

class Parser {

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var fileReader: BufferedReader? = null
    var line: String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    fun parse_csv(): MutableList<Shopping> {
        val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        try {
            fileReader = BufferedReader(FileReader("$downloadFolder/receipts-details.csv"))
        } catch (e: Exception){
//            TODO(handle exception )
        }
        // Read CSV header
        fileReader!!.readLine()

        var single_shopping = Shopping(mutableListOf())
        val list_of_shoppings = mutableListOf<Shopping>()
        var current_date: LocalDate? = null

        // Read the file line by line starting from the second line
        line = fileReader!!.readLine()
        while (line != null) {
            val tokens = line!!.split(";")
            if (tokens.size > 0) {
                val sep = tokens[0].split('.')
                val date = LocalDate.of(sep[2].toInt(),sep[1].toInt(),sep[0].toInt())

                if(date != current_date){
                    if(current_date != null){
                        list_of_shoppings.add(single_shopping)
                    }
                    single_shopping = Shopping(mutableListOf())
                    current_date = date
                }
                single_shopping.add_item(
                    DatabaseItem(
                        date = date,
                        name = tokens[5],
                        quantity = tokens[6].toFloat(),
                        action = tokens[7].toFloat(),
                        paid_price = tokens[8].toFloat(),
                    )
                )

            }
            line = fileReader!!.readLine()
        }
        list_of_shoppings.add(single_shopping)
        return list_of_shoppings
    }

    fun get_permissions(context: Context){
        val permission = ActivityCompat.checkSelfPermission(context, PERMISSIONS_STORAGE.toString())

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                context as Activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}