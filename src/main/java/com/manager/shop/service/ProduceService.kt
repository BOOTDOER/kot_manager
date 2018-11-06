package com.manager.shop.service

import com.manager.shop.model.Produce

interface ProduceService {

    /**
     * @description 查询多个
     * @param 多条件
     */
    fun getList(map : HashMap<String, Any>): List<Produce>

    /**
     * @description 查询单个商品
     * @param 商品id
     */
    fun get(produceId: Long): Produce


}
