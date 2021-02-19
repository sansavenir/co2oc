package com.jk.co2preview.database


//@Parcelize
class DatabaseItem{
    private var id : Int = -1
    private var name: String? = null
    private var gen_info: String? = null
    private var desc: String? = null
    private var nutrients: String? = null
    private var origin: String? = null
    private var price: Float? = null
    private var orig_price: Float? = null
    private var link: String? = null
    private var season: String? = null

    constructor(id: Int, name: String?, desc: String?, gen_info: String?, nutrients: String?, origin: String?, price: Float?, orig_price: Float?, link: String?, season: String?){
        this.id = id
        this.name= name
        this.desc= desc
        this.gen_info= gen_info
        this.nutrients= nutrients
        this.origin= origin
        this.price= price
        this.orig_price= orig_price
        this.link= link
        this.season= season
    }

    fun get_nutrients(): MutableList<List<String>>? {
        if(nutrients == null){
            return null
        }
        var res : MutableList<List<String>> = emptyList<List<String>>().toMutableList()
        for(nut in this.nutrients?.split(';')!!.dropLast(1)){
            res.add(nut.split(','))
        }
        return res
    }

    fun get_link(): String?{
        return this.link
    }

    fun get_desc(): String?{
        return this.desc
    }

    fun get_origin(): String?{
        return this.origin
    }

    fun get_price(): Float?{
        return this.price
    }

    fun get_name(): String?{
        return this.name
    }
}