package com.manager.shop.service

import com.manager.shop.model.Order

interface OrderService {

    /**
     * 根据id获取订单
     */
    fun get(orderId: Long): Order

    /**
     * 更新订单
     */
    fun update(order: Order): Int

    /**
     * 删除订单中的商品详情
     */
    fun deleteDetail(orderId: Long): Int

}