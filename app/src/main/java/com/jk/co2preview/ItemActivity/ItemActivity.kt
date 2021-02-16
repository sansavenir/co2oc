package com.jk.co2preview.ItemActivity

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.jk.co2preview.R
import com.jk.co2preview.data_representation.BuyItem

class ItemActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)
        val bundle = intent.extras
        val item = bundle?.get("item") as BuyItem
        val nameTextView = findViewById<TextView>(R.id.item_name)
        val priceTextView = findViewById<TextView>(R.id.item_price)
        val quantTextView = findViewById<TextView>(R.id.item_quant)
        val reductionTextView = findViewById<TextView>(R.id.item_reduction)

        nameTextView.text = item.name
        priceTextView.text = item.price.toString()
        quantTextView.text = item.quantity.toString()
        reductionTextView.text = item.action.toString()


    }


}