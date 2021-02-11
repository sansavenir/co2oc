package com.jk.co2preview

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jk.co2preview.data_representation.Parser


class MainActivity : Activity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var parser = Parser()

        parser.get_permissions(this)
        val list_of_shoppings = parser.parse_csv()

        val rvShopping = findViewById<View>(R.id.rvShopping) as RecyclerView
        val adapter = ShoppingAdapter(list_of_shoppings)
        rvShopping.adapter = adapter
        rvShopping.layoutManager = LinearLayoutManager(this)

    }

}


