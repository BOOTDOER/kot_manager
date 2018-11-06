package com.manager.shop.controller

import com.manager.shop.service.ProduceService
import com.manager.shop.thread.BuyThread
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.support.collections.RedisList
import org.springframework.web.bind.annotation.*

/**
 * 请求队列
 *   抢购
 */
@RequestMapping("/grab")
@RestController
open class BuyController {
    @Autowired
    private val produceService : ProduceService? = null

    /**
     * 抢购方案1
     *
     *    定义线程安全的请求队列 requestQueue? = null
     *      1.if(商品数量<=0)
     *          return “商品已售空”
     *      2.if(requestQueue == null)
     *          根据商品数量初始化requestQueue长度
     *      3.if(requestQueue长度>0)
     *          将请求放入队列
     *        else
     *          return "抢购失败"
     *      4.开启“购买”线程
     *      5.响应客户端
     *
     * 订单抢购（多商品抢购） 方案2
     *
     *
     */
    companion object {
        private var buyQueue : MutableList<Long>? = null
    }

    @PostMapping("/produce")
    private fun buyProduce(@RequestParam(value = "produceId",defaultValue = "null") produceId : Long) : String {

        val produceCount : Int = produceService!!.get(produceId)!!.produceCount

        /**
         * 1.确保商品有存货
         */
        if(produceCount<1) return "商品已售空"

        /**
         * 2.根据商品数量初始化requestQueue长度
         */
        if(buyQueue == null) buyQueue = ArrayList(produceCount)

        /**
         * 3.将请求放入队列
         */
        if(buyQueue!!.size>0) {
            buyQueue!!.add(produceId)
        }else {
            return "抢购失败"
        }
        /**
         * 4.开启“抢购”线程
         */
        Thread(BuyThread(buyQueue!!)).start()
        /**
         * 5.响应客户端
         */
        return "抢购中，请稍等。。。。"
    }


}