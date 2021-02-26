package com.jk.co2preview.BuyItemActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jk.co2preview.R
import com.jk.co2preview.data_representation.Shopping

class BuyItemInfo(shopping: Shopping) : Fragment() {

    val shopping = shopping
    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.buy_item_info, container, false)
        val originTextView = view.findViewById<TextView>(R.id.item_origin)
        originTextView.text = shopping.get_all_origins()

        val nutrients = shopping.get_sum_nutrients()
        val tableView = view.findViewById<TableLayout>(R.id.nutrients_table)
        tableView.visibility = 1
        for (i in nutrients.indices) {
            for (j in nutrients[i].indices) {
                val textView = view.findViewById<TextView>(
                        resources.getIdentifier(
                                "n$j${i+1}",
                                "id",
                                this.context?.getPackageName()
                        )
                )
                textView.text = nutrients[i][j].round(1).toString()
            }
        }

        val co2TextView = view.findViewById<TextView>(R.id.item_co2)
        val co2 = shopping.get_sum_co2()
        val txt = co2.map{it.round(1)}.joinToString ( "," )
        co2TextView.text = txt



        return view
    }

}

private fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

