package com.jk.co2preview.ItemActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TableLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.jk.co2preview.R
import com.jk.co2preview.database.DatabaseItem
import kotlin.math.roundToInt


class ItemActivity : Activity() {
    @SuppressLint("WrongViewCast", "WrongConstant")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)
        val bundle = intent.extras
        val item = bundle?.get("item") as DatabaseItem?
        val nameTextView = findViewById<TextView>(R.id.item_name)
        val priceTextView = findViewById<TextView>(R.id.item_price)
        val quantTextView = findViewById<TextView>(R.id.item_quant)
        val descTextView = findViewById<TextView>(R.id.item_desc)
        val linkTextView = findViewById<TextView>(R.id.item_link)
        val co2TextView = findViewById<TextView>(R.id.item_co2)

        if(item != null) {
            nameTextView.text = item.get_name()
            priceTextView.text = item.get_price().toString()
//        quantTextView.text = item.quantity.toString()

            val nutrients: List<List<Float>>? = item.get_nutrients()

            if (nutrients != null) {
                val textView = findViewById<TextView>(R.id.weight)
                textView.text = item.get_weight().toString()
                val tableView = findViewById<TableLayout>(R.id.nutrients_table)
                tableView.visibility = 1
                for (i in nutrients.indices) {
                    for (j in nutrients[i].indices) {
                        val textView = findViewById<TextView>(
                                resources.getIdentifier(
                                        "n$j$i",
                                        "id",
                                        this.getPackageName()
                                )
                        )
                        textView.text = ((nutrients[i][j] * 10.0).roundToInt() / 10.0).toString()
                    }
                }
            }
            val link: String? = item.get_link()
            if (link != null) {
                linkTextView.visibility = 1
                linkTextView.setOnClickListener {
                    setContentView(R.layout.item_webview)
                    val webView: WebView = findViewById(R.id.webview)
                    webView.settings.javaScriptEnabled = true
                    webView.webViewClient = WebViewClient()
                    webView.loadUrl(link)
                }
            }
            val desc: String? = item.get_desc()
            if (desc != null) {
                descTextView.text = desc
            }
            val co2: String? = item.get_co2()?.map{(it*10).toInt()/10F}?.joinToString(",")
            if (co2 != null) {
                co2TextView.text = co2
            }

        }


    }


}