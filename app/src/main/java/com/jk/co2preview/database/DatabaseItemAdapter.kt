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
import com.jk.co2preview.ItemActivity.ItemActivity
import com.jk.co2preview.database.DatabaseItem


class DatabaseItemAdapter (private val mDatabaseItems: List<DatabaseItem>) : RecyclerView.Adapter<DatabaseItemAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    var context : Context? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.buy_item_name)
        val costTextView = itemView.findViewById<TextView>(R.id.buy_item_cost)
        val co2TextView = itemView.findViewById<TextView>(R.id.buy_item_c02)
        val seasonTextView = itemView.findViewById<TextView>(R.id.buy_item_season)
        val recyclerView = itemView.findViewById<LinearLayout>(R.id.buy_item_recycler)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabaseItemAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val buyItemView = inflater.inflate(R.layout.buy_item, parent, false)
        // Return a new holder instance
        return ViewHolder(buyItemView)
    }

    // Involves populating data into the item through holder
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get the data model based on position
        val databaseItem: DatabaseItem = mDatabaseItems[position]
        // Set item views based on your views and data model
        val nameView = viewHolder.nameTextView
        nameView.text = databaseItem.get_name()

        val costView = viewHolder.costTextView
        val price = databaseItem.get_price()
        if (price != null){
            costView.text = "${"%.2f".format(price)}.-"
        }

        val co2View = viewHolder.co2TextView
        val co2 = databaseItem.get_co2()
        if (co2 != null){
            co2View.text = "${"%.2f".format(co2.sum().round(2))} kg co2"
        } else{
            co2View.text = ""
        }

        val seasonView = viewHolder.seasonTextView
        val season = databaseItem.get_season()
        if (season != null){
            seasonView.text = season
        }

        viewHolder.recyclerView.setOnClickListener{
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra("item", databaseItem)
            context?.startActivity(intent)
        }

    }


    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mDatabaseItems.size
    }
}

private fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

