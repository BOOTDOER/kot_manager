package com.manager.shop.controller

import com.manager.shop.model.Order
import com.manager.shop.service.OrderService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags=["订单信息"])
@RestController
open class OrderController {
    @Autowired
    private val orderService: OrderService? = null

    @ApiOperation("获取单条信息")
    @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "Long")
    @GetMapping("/order")
    private fun getOrder(@RequestParam("orderId") orderId: Long=0) : Order {
        return orderService!!.get(orderId)
    }

    @ApiOperation("更新单条信息")
    @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "Long")
    @PostMapping("/updateOrder")
    private fun updateOrder(@RequestParam order: Order) : Int {
        return orderService!!.update(order)
    }

}