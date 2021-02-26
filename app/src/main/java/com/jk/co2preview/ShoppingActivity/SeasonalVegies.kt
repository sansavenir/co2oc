package com.jk.co2preview

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jk.co2preview.database.DBHandler
import java.time.LocalDate


class SeasonalVegies : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.seasonal_veggies, container, false)
        val rvShopping = view.findViewById<View>(R.id.seasonalVeggiesRV) as RecyclerView
        val month = LocalDate.now().monthValue - 1
        val dbHandler = this.context?.let { DBHandler(it, null, null, 1) }
        val products = dbHandler?.getSeasonalProducts(month)
        val adapter = products?.let { DatabaseItemAdapter(it) }
        rvShopping.adapter = adapter
        rvShopping.layoutManager = LinearLayoutManager(this.context)

        return view
    }

}


