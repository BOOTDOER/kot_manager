package com.manager.common.tools

import javax.servlet.http.HttpServletRequest

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletResponse

open class HttpContextUtils {
    companion object {
        /**
         * 获取请求信息
         */
        fun getRequest(): HttpServletRequest{
            return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        }

        /**
         * 获取响应信息
         */
        fun getResponse(): HttpServletResponse{
            return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response
        }
    }
}