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
import com.jk.co2preview.ShoppingActivity.BuyItemActivity
import com.jk.co2preview.data_representation.Shopping


class ShoppingAdapter (private val mShoopings: List<Shopping>) : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    var context : Context? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val dateTextView = itemView.findViewById<TextView>(R.id.shopping_date)
        val costTextView = itemView.findViewById<TextView>(R.id.cost)
        val recyclerView = itemView.findViewById<RelativeLayout>(R.id.recycler)
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ShoppingAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val shopping: Shopping = mShoopings.get(position)
        // Set item views based on your views and data model
        val dateView = viewHolder.dateTextView
        dateView.text = shopping.get_date()
        val costView = viewHolder.costTextView
        costView.text = "${shopping.get_cost()}.-"

        viewHolder.recyclerView.setOnClickListener{
//            var buyItemActivity = BuyItemActivity(shopping)
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