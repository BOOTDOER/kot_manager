package com.manager.core.controller

import com.manager.common.log.Log
import com.manager.common.queue.Task
import com.manager.common.queue.TQueueService
import com.manager.common.shiro.util.ShiroUtils
import com.manager.core.model.SysUser
import com.manager.core.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Api(tags=["用户操作"])
@RestController
open class UserController {
    @Autowired
    open val userService: UserService? = null

    companion object {
        private val queue = TQueueService.instance
    }

    @Log("同步测试")
    @ApiOperation("同步测试")
    @ApiImplicitParam(name = "map", value = "多条件", dataType = "HashMap<String, Any>")
    @GetMapping("/UserList")
    protected open fun getAll(@RequestParam map: HashMap<String, Any>) : List<SysUser> {
        return userService!!.getList(map)
    }

    @Log("异步测试")
    @ApiOperation("异步测试")
    @ApiImplicitParam(name = "message", value = "测试数据", dataType = "String")
    @PostMapping("/intoQueue")
    protected open fun putIntoQueue(@RequestParam("message") message: String): String {
        val param = HashMap<String, Any>()
        param.put("param", message)
        //将内容放入消息队列
        queue.put(Task(sessionId = ShiroUtils.subjct.session.id as String,
                        methodName = userService!!::getList,
                        param = param))

        return "ok"
    }

}

