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
import com.jk.co2preview.data_representation.BuyItem
import com.jk.co2preview.database.DBHandler
import com.jk.co2preview.database.DatabaseItem


class ItemActivity : Activity() {
    @SuppressLint("WrongViewCast", "WrongConstant")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)
        val bundle = intent.extras
        val item = bundle?.get("item") as BuyItem
        val nameTextView = findViewById<TextView>(R.id.item_name)
        val priceTextView = findViewById<TextView>(R.id.item_price)
        val quantTextView = findViewById<TextView>(R.id.item_quant)
        val descTextView = findViewById<TextView>(R.id.item_desc)
        val linkTextView = findViewById<TextView>(R.id.item_link)
        val originTextView = findViewById<TextView>(R.id.item_origin)


        nameTextView.text = item.name
        priceTextView.text = item.price.toString()
        quantTextView.text = item.quantity.toString()

        val dbHandler = DBHandler(this, null, null, 1)

        val product: DatabaseItem? = item.name?.let { dbHandler.findProduct(it) }

        if(product != null) {
            val nutrients: MutableList<List<String>>? = product.get_nutrients()

            if (nutrients != null) {
                val tableView = findViewById<TableLayout>(R.id.nutrients_table)
                tableView.visibility = 1
                for (i in nutrients.indices) {
                    val inner: List<String> = nutrients[i]
                    for (j in inner.indices) {
                        val textView = findViewById<TextView>(resources.getIdentifier("z$i$j", "id", this.getPackageName()))
                        textView.text = inner[j]
                    }

                }
            }
            val link: String? = product.get_link()
            if(link != null){
                linkTextView.visibility = 1
                linkTextView.setOnClickListener{
                    setContentView(R.layout.item_webview)
                    val webView: WebView = findViewById(R.id.webview)
                    webView.settings.javaScriptEnabled = true
                    webView.webViewClient = WebViewClient()
                    webView.loadUrl(link)
                }
            }
            val desc: String? = product.get_desc()
            if (desc != null){
                descTextView.text = desc
            }
            val origin: String? = product.get_origin()
            if (desc != null){
                originTextView.text = origin
            }
        }


    }


}