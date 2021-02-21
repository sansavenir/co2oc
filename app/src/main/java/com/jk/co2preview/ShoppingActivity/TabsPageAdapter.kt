package com.jk.co2preview



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jk.co2preview.data_representation.Shopping

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int, shopping: List<Shopping>) : FragmentStateAdapter(fm, lifecycle) {

    val shopping = shopping
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                val recyclerView = ShoppingRV(shopping)
                return recyclerView
            }
            1 -> {
                val genInfo = SeasonalVegies()
                return genInfo
            }
            else -> return SeasonalVegies()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}