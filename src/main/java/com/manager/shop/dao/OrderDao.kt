package com.manager.shop.dao

import com.manager.shop.model.Order

interface OrderDao {

    fun get(orderId: Long): Order

    fun update(order: Order): Int

    fun deleteProduces(orderId: Long): Int

}
