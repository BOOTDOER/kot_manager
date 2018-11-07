package com.manager.shop.controller

import com.manager.shop.model.Produce
import com.manager.shop.service.ProduceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
open class ProduceController{
    @Autowired
    private val produceService:ProduceService? = null

    @GetMapping("/produceList")
    private fun getAllProduces(@RequestParam map : HashMap<String, Any>) : List<Produce> {
        return  produceService!!.getList(map)
    }

    @GetMapping("/produce/{produceId}.html")
    private fun getOneById(@PathVariable("produceId") produceId : Long) : Produce {
        return produceService!!.get(produceId)
    }

}
