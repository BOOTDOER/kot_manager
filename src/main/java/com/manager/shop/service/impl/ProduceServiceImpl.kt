package com.manager.shop.service.impl

import com.manager.shop.dao.ProduceDao
import com.manager.shop.model.Produce
import com.manager.shop.service.ProduceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = arrayOf("Produce"))
open class ProduceServiceImpl : ProduceService {
    @Autowired
    private val produceDao : ProduceDao ?= null

    @Cacheable(key="#map")
    override fun getList(map : HashMap<String, Any>): List<Produce> {
        println("缓存中暂无该数据")
        return produceDao!!.getList(map)
    }

    @Cacheable(key="#ProduceId")
    override fun get(produceId: Long): Produce {
        println("缓存中暂无该数据")
        return produceDao!!.get(produceId)
    }

}