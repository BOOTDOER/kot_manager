package com.manager.common.websocket

import com.alibaba.fastjson.JSON
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.websocket.*
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(value="/chat", configurator = WebSocketConfig::class)
@Component
open class WebSocketServer {

    companion object {
        // 连接集合 sessionId => server 键值对 线程安全
        var map: ConcurrentMap<String, WebSocketServer> = ConcurrentHashMap()

        /**
         * 群发
         */
        val pushToAll = { msg: Msg ->
            map.values.forEach {
                it.session?.basicRemote?.sendText(JSON.toJSONString(msg))
            }
        }
        /**
         * 单发
         */
        val pushToOne = { sessionId: String, msg: Msg ->
            map[sessionId]?.session?.basicRemote?.sendText(JSON.toJSONString(msg))
        }

    }

    //与客户端的连接会话
    private var session: Session? = null
    //当前用户的sessionId
    private var sessionId: String? =null

    /**
     * 声明客户端连接进入的方法
     */
    @OnOpen
    open fun onOpen(session: Session, config: EndpointConfig) {

        // 得到httpSession
        this.sessionId = config.userProperties["sessionId"] as String?

        this.session = session

        // 将连接session对象存入map
        map[sessionId] = this

    }

    /**
     * 关闭连接
     */
    @OnClose
    open fun onClose() {
        // 从map中移除连接
        map.remove(sessionId)
    }

    /**
     * 消息监听处理方法 , 将消息转为 Msg 对象 发送给客户端
     */
    @OnMessage
    open fun onMessage(message: String) {

        // 将消息转Msg对象
        val msg: Msg = JSON.parseObject(message, Msg::class.java)

        // 根据Msg消息对象获取定点发送人的userId
        val client: WebSocketServer? = map[sessionId]

        // 定点发送
        if (msg.toUid != null) {
            // 消息发送
            client?.session?.basicRemote?.sendText(JSON.toJSONString(msg))
        }

    }

    /**
     * 异常处理
     */
    @OnError
    open fun onError(e: Throwable) {
        e.printStackTrace()
    }

}