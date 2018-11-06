package com.manager.shop.thread

import com.manager.shop.service.ProduceService
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

@Component
class BuyThread : Runnable {
    @Autowired
    private val produceService : ProduceService? = null

    //线程默认状态 ： false
    var excute = false

    companion object {
        private var buyQueue: MutableList<Long>? = null
    }

    constructor()

    constructor(buyQueue : MutableList<Long>) {
        BuyThread.buyQueue = buyQueue
    }

    override fun run() {

        //修改线程的默认执行标志为执行状态
        excute = true

        //buyQueue
        while (buyQueue != null && buyQueue!!.size > 0) {
            //取出队头
            val produceId = buyQueue!!.first()
            //处理请求
            dealWithQueue(produceId)
        }

    }

    /**
     *  处理队列订单
     *      加锁
     */
    @Synchronized
    private fun dealWithQueue(produceId: Long) {
        try {
            //为了尽量确保数据的一致性,处理之前先从redis中获取当前抢购商品的剩余数量
            val produceCount : Int = produceService!!.get(produceId)!!.produceCount
            if (produceCount < 1) {//如果没有剩余商品,就直接返回
                return
            }
            //如果有剩余商品,先将队列长度减一,再开始下订单
            buyQueue!!.remove(produceId)
            //将数据库中将剩余数量减一,这一步处理可以在队列处理完成之后一次性更新剩余数量
            //this.produceService.buyProduce()

            //处理请求,下订单

        } catch (e: Exception) {
            Exception("购买失败")
        }
    }

}