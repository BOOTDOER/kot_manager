package com.manager.shop.controller

import com.manager.shop.model.Order
import com.manager.shop.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
open class OrderController {
    @Autowired
    private val orderService: OrderService? = null

    @GetMapping("/order")
    private fun getOrder(@RequestParam("orderId") orderId: Long) : Order {
        return orderService!!.get(orderId)
    }

    @PostMapping("/updateOrder")
    private fun updateOrder(@RequestParam order: Order) : Int {
        return orderService!!.update(order)
    }

}