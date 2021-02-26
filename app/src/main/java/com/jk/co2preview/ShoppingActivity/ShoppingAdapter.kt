package com.jk.co2preview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.jk.co2preview.BuyItemActivity.BuyItemActivity
import com.jk.co2preview.data_representation.Shopping


class ShoppingAdapter (private val mShoopings: List<Shopping>) : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    var context : Context? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val monthTextView = itemView.findViewById<TextView>(R.id.shopping_month)
        val dayTextView = itemView.findViewById<TextView>(R.id.shopping_day)
        val costTextView = itemView.findViewById<TextView>(R.id.shopping_cost)
        val co2TextView = itemView.findViewById<TextView>(R.id.shopping_co2)
        val recyclerView = itemView.findViewById<LinearLayout>(R.id.shopping_recycler)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val shoppingView = inflater.inflate(R.layout.shopping_item, parent, false)
        // Return a new holder instance
        return ViewHolder(shoppingView)
    }

    // Involves populating data into the item through holder
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ShoppingAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val shopping: Shopping = mShoopings[position]
        this.context?.let { shopping.init_items_from_db(it) }
        // Set item views based on your views and data model
        val dateView = viewHolder.monthTextView
        dateView.text = shopping.get_month().take(3)
        val costView = viewHolder.costTextView
        costView.text = "${"%.2f".format(shopping.get_cost())}.-"
        val dayView = viewHolder.dayTextView
        dayView.text = shopping.get_day()
        val co2View = viewHolder.co2TextView
        co2View.text = "${shopping.get_sum_co2().sum().round(1)} kg of co2"

        viewHolder.recyclerView.setOnClickListener{
            val intent = Intent(context, BuyItemActivity::class.java)
            intent.putExtra("shoppingList", shopping.get_items().toTypedArray())
            context?.startActivity(intent)
        }
    }


    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mShoopings.size
    }
}

private fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}