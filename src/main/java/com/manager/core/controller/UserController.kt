package com.manager.core.controller

import com.manager.common.log.Log
import com.manager.common.queue.MQueueItem
import com.manager.common.queue.MQueueService
import com.manager.common.shiro.util.ShiroUtils
import com.manager.core.model.SysUser
import com.manager.core.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
open class UserController {
    @Autowired
    open val userService: UserService? = null

    companion object {
        private val queue = MQueueService.instance
    }

    @Log("同步测试")
    @RequestMapping("/getAll")
    protected open fun getAll(@RequestParam map: HashMap<String, Any>) : List<SysUser> {
        return userService!!.getList(map)
    }

    @Log("异步测试")
    @RequestMapping("/intoQueue")
    protected open fun putIntoQueue(@RequestParam("message", defaultValue = "null") message: String): String {
        val param = HashMap<String, Any>()
        param.put("param", message)
        //将内容放入消息队列
        queue.put(MQueueItem(sessionId = ShiroUtils.subjct.session.id as String,
                             methodName = userService!!::getList,
                             param = param))

        return "ok"
    }



}

