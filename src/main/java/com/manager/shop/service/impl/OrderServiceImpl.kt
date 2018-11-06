package com.manager.shop.service.impl

import com.manager.shop.dao.OrderDao
import com.manager.shop.model.Order
import com.manager.shop.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = arrayOf("Order"))
open class OrderServiceImpl : OrderService {
    @Autowired
    private val orderDao: OrderDao? = null

    @Cacheable(key="#orderId")
    override fun get(orderId: Long): Order {
        return orderDao!!.get(orderId)
    }

    override fun update(order: Order): Int {
        return orderDao!!.update(order)
    }

    override fun deleteDetail(orderId: Long): Int {
        return orderDao!!.deleteProduces(orderId)
    }

}