package com.manager.common.websocket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.server.standard.ServerEndpointExporter
import javax.servlet.http.HttpSession

import javax.websocket.HandshakeResponse
import javax.websocket.server.HandshakeRequest
import javax.websocket.server.ServerEndpointConfig
import javax.websocket.server.ServerEndpointConfig.Configurator

@Configuration
open class WebSocketConfig: Configurator() {

    /**
     * 将 sessionId 传递给 下一级 使用者
     */
    override fun modifyHandshake(sec: ServerEndpointConfig, request: HandshakeRequest, response: HandshakeResponse) {
        val httpSession: HttpSession = request.httpSession as HttpSession
        sec.userProperties["sessionId"] = httpSession.id
    }

    /**
     * 引入shiro框架下的session，获取session信息,不知道为什么这里出了点问题
     */
    /*override fun modifyHandshake (sec: ServerEndpointConfig, request: HandshakeRequest, response: HandshakeResponse) {
        //val shiroSession: Session = ShiroUtils.subjct.session
        val userInfo = ShiroUtils.user

        sec.userProperties["userInfo"] = userInfo
    }*/

    /**
     * 注入ServerEndpointExporter
     */
    @Bean
    open fun serverEndpointExporter(): ServerEndpointExporter {
        return ServerEndpointExporter()
    }
}