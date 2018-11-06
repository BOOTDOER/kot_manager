package com.manager.shop.model

import java.io.Serializable


/**
 * @description 商品
 * @author lin
 * @version 1.0
 */
data class Produce (
    var produceId: Long = 0,
    //所属商铺id
    var belongShopId: Long = 0,
    var belongShopName: String? = null,
    var produceName: String? = null,
    var produceImg: String? = null,
    var produceIntro: String? = null,
    //商品类型
    var produceType: String? = null,
    var producePrice: Double = 0.toDouble(),
    var produceCount: Int = 0,
    var produceSell: Int = 0,
    var produceNice: Int = 0,
    var produceBad: Int = 0
) : Serializable
