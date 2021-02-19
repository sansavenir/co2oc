package com.jk.co2preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jk.co2preview.data_representation.Shopping


class ShoppingRV(list_of_shoppings: List<Shopping>) : Fragment() {
    val list_of_shoppings = list_of_shoppings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.shopping_rv, container, false)
        val rvShopping = view.findViewById<View>(R.id.rvShopping) as RecyclerView
        val adapter = ShoppingAdapter(list_of_shoppings)
        rvShopping.adapter = adapter
        rvShopping.layoutManager = LinearLayoutManager(this.context)

        return view
    }

}


