package com.manager.shop.model

import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.Date

/**
 * @description 订单
 * @author lin
 * @version 1.0
 */
data class Order (
    var orderId: Long = 0,
    //买家
    var orderUserId: Long = 0,
    //商铺
    var orderShopId: Long = 0,
    //快递员
    var orderCourierId: Long = 0,
    var orderRemarks: String? = null,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //入参
    var orderTime: Date? = null,
    var orderState: Short = 0,
    //商品
    var produceList: List<Produce>? = null
) : Serializable
