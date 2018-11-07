package com.manager.shop.controller

import com.manager.shop.model.Produce
import com.manager.shop.service.ProduceService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags=["商品信息"])
@RestController
open class ProduceController{
    @Autowired
    private val produceService:ProduceService? = null

    @ApiOperation("获取列表信息")
    @ApiImplicitParam(name = "map", value = "多条件", dataType = "HashMap<String, Any>")
    @GetMapping("/produceList")
    private fun getAllProduces(@RequestParam map : HashMap<String, Any>) : List<Produce> {
        return  produceService!!.getList(map)
    }

    @ApiOperation("获取单条信息")
    @ApiImplicitParam(name = "produceId", value = "商品id", dataType = "Long", paramType = "path")
    @GetMapping("/produce/{produceId}.html")
    private fun getOneById(@PathVariable("produceId") produceId : Long=0) : Produce {
        return produceService!!.get(produceId)
    }

}
