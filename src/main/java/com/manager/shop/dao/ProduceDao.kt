package com.manager.shop.dao

import com.manager.shop.model.Produce

interface ProduceDao {

    fun getList(map : HashMap<String, Any>?): List<Produce>

    fun getListByOrderId(orderId: Long): List<Produce>

    fun get(produceId: Long): Produce

}
