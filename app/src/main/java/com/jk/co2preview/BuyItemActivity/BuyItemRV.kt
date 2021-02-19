package com.jk.co2preview.BuyItemActivity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jk.co2preview.DatabaseItemAdapter
import com.jk.co2preview.R
import com.jk.co2preview.data_representation.Shopping


class BuyItemRV(shopping: Shopping) : Fragment() {
    val shopping = shopping

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.buy_item_rv, container, false)
        val rvShopping = view.findViewById<View>(R.id.rvShopping) as RecyclerView
        val db_items = this.context?.let { shopping.get_db_items(it) }
        val adapter = db_items?.let { DatabaseItemAdapter(it) }
        rvShopping.adapter = adapter
        rvShopping.layoutManager = LinearLayoutManager(this.context)

        return view
    }


}