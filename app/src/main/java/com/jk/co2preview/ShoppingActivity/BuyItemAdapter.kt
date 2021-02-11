package com.jk.co2preview

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.jk.co2preview.data_representation.BuyItem


class BuyItemAdapter (private val mBuyItems: List<BuyItem>) : RecyclerView.Adapter<BuyItemAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    var context : Context? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.name)
        val costTextView = itemView.findViewById<TextView>(R.id.cost)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyItemAdapter.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val buyItemView = inflater.inflate(R.layout.buy_item, parent, false)
        // Return a new holder instance
        return ViewHolder(buyItemView)
    }

    // Involves populating data into the item through holder
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: BuyItemAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val buyItem: BuyItem = mBuyItems.get(position)
        // Set item views based on your views and data model
        val nameView = viewHolder.nameTextView
        nameView.text = buyItem.name
        val costView = viewHolder.costTextView
        costView.text = buyItem.price.toString()

    }


    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mBuyItems.size
    }
}