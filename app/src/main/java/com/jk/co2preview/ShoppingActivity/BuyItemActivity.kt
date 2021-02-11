package com.jk.co2preview.ShoppingActivity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jk.co2preview.BuyItemAdapter
import com.jk.co2preview.R
import com.jk.co2preview.data_representation.BuyItem
import com.jk.co2preview.data_representation.Shopping

class BuyItemActivity() : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_item_activity)
        val bundle = intent.extras
        var shoppingList = bundle?.getParcelableArray("shoppingList")?.map { it -> it as BuyItem }
        val shopping = Shopping(shoppingList as MutableList<BuyItem>)

        val rvBuyItem = findViewById<View>(R.id.rvBuyItem) as RecyclerView
        val adapter = BuyItemAdapter(shopping.get_items())
        rvBuyItem.adapter = adapter
        rvBuyItem.layoutManager = LinearLayoutManager(this)
    }

}