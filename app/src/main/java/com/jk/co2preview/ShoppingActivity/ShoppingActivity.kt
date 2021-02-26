package com.jk.co2preview

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jk.co2preview.data_representation.Parser


class ShoppingActivity : AppCompatActivity() {
    var tab_layout : TabLayout? = null
    var tabs_viewpager : ViewPager2? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_item_activity)

        var parser = Parser()

        parser.get_permissions(this)
        val list_of_shoppings = parser.parse_csv()

        // Tabs Customization
        tab_layout = findViewById(R.id.tab_layout)
        tabs_viewpager = findViewById(R.id.tabs_viewpager)


        tab_layout!!.setSelectedTabIndicatorColor(Color.WHITE)
        tab_layout!!.tabTextColors = ContextCompat.getColorStateList(this, android.R.color.white)

        val numberOfTabs = 2

        // Show all Tabs in screen
        tab_layout!!.tabMode = TabLayout.MODE_FIXED

        // Scroll to see all Tabs
//        tab_layout!!.tabMode = TabLayout.MODE_SCROLLABLE

        // Set Tab icons next to the text, instead above the text
        tab_layout!!.isInlineLabel = true


        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs, list_of_shoppings)
        tabs_viewpager!!.adapter = adapter

        // Enable Swipe
        tabs_viewpager!!.isUserInputEnabled = true

        TabLayoutMediator(tab_layout!!, tabs_viewpager!!) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Previoussly bought items"
                }
                1 -> {
                    tab.text = "Seasonal Veggies"
                }
            }
        }.attach()

//        setCustomTabTitles()

    }

    override fun onBackPressed() {
        if (tabs_viewpager?.currentItem == 0) {
            super.onBackPressed()
        } else {
            tabs_viewpager?.currentItem = 0
        }
    }



    private fun setCustomTabTitles() {
        val vg = this.tab_layout?.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount

        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup

            val tabChildCount = vgTab.childCount

            for (i in 0 until tabChildCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {

                    // Change Font and Size
                    tabViewChild.typeface = Typeface.DEFAULT_BOLD
//                    val font = ResourcesCompat.getFont(this, R.font.myFont)
//                    tabViewChild.typeface = font
//                    tabViewChild.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
                }
            }
        }
    }
}


