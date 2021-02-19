package com.jk.co2preview

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
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
        val recyclerView = itemView.findViewById<RelativeLayout>(R.id.buy_item_recycler)

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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: DatabaseItemAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val databaseItem: DatabaseItem = mDatabaseItems[position]
        // Set item views based on your views and data model
        val nameView = viewHolder.nameTextView
        nameView.text = databaseItem.get_name()
        val costView = viewHolder.costTextView
        costView.text = databaseItem.get_price().toString()
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