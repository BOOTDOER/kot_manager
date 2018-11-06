package com.manager.common.queue

import com.manager.common.shiro.util.ShiroUtils
import com.manager.common.tools.HttpContextUtils
import com.manager.common.websocket.Msg
import com.manager.common.websocket.WebSocketServer
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

/**
 * ************************消息队列************************
 *                          单例
 *                    监听服务 开启与关闭
 *
 * 几个比较重要的方法和类：
 *
 *  1.fun start() : 开启 【处理队列】 线程 监听
 *
 *  2.fun put(T) ： 放入队列
 *
 *  3.class QueueHandler : 队列元素处理方式
 *
 *  4.fun resultHandler() : 队列元素处理结果 的 处理方法   可通过WebSocket返回客户端 or 记录日志 or ...
 *
 *  @author lin
 *  @version 1.0
 */
class MQueueService<T>: LinkedBlockingQueue<MQueueItem>() {
    private var flag = false

    companion object {
        private const val maxSize = 20
        private val es = Executors.newFixedThreadPool(maxSize)//线程池
        val instance = MQueueService<Any>()//单例
    }

    /**
     * 开启队列监听
     */
    fun start() {
        if (!this.flag) {
            this.flag = true
        } else {
            throw IllegalArgumentException("队列已启动.")
        }

        Thread(Runnable {
            //单连接 : 是
            @Synchronized while (flag) {
                try {
                    //take() : 使用阻塞模式获取队列消息 , 并将获取的消息交由线程池处理
                    //execute() :
                    val obj = take()
                    val result = es.submit(
                            QueueHandler<T>(obj.methodName as (T) -> Unit,
                            obj.param as T?)).get()
                    //根据result选择发送不同数据到客户端
                    resultHandler(obj.sessionId, result)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()

    }

    /**
     * 停止队列监听
     */
    fun stop() {
        this.flag = false
    }

    /**
     * 线程处理结果处理方法 , 此处用 WebSocket 处理
     */
    private fun resultHandler(sessionId: String? , result: T): Any? {
        //发送给该客户端
        return WebSocketServer.pushToOne(sessionId!!, Msg(
                toUid = sessionId,
                text = result.toString()
        ))
    }

    /**
     * 队列处理方法
     *      Callable 代替 Runnable
     *      前可以者返回值，用来判断任务执行结果
     */
    class QueueHandler<T>(private val method: (T) -> Unit,
                          private val item: T? =null) : Callable<T> {

        /**
         * let表达式执行方法
         *      方法执行正常 -> ok
         *      方法执行出错 -> error
         */
        override fun call(): T {
            return try{
                item?.let { method(it) }
                "ok" as T
            }catch (e: Exception) {
                "error" as T
            }
        }

    }

}



