package com.manager.core.controller

import com.manager.common.log.Log
import com.manager.common.shiro.util.ShiroUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.LockedAccountException
import org.apache.shiro.authc.UsernamePasswordToken
import org.springframework.web.bind.annotation.*

import java.util.HashMap

/**
 * @Description 登录相关
 * @Author 林昊
 * @Date 2018/10/23 19:44
 * @Version v1.0
 */
@Api(tags= ["登录相关"])
@RestController
open class LoginController {
    /**
     * 登录
     * @param userName
     * @param userPwd
     * @return
     */
    @Log("登录")
    @ApiOperation("登录")
    @ApiImplicitParams(
        ApiImplicitParam(name = "userName", value = "用户名", dataType = "String"),
        ApiImplicitParam(name = "userPwd", value = "密码", dataType = "Long"),
        ApiImplicitParam(name = "rememberMe", value = "记住密码", dataType = "boolean")
    )
    @PostMapping("/login.html")
    protected open fun login(@RequestParam("userName") userName: String, @RequestParam("userPwd") userPwd: String, @RequestParam(value = "rememberMe", defaultValue = "false") rememberMe: Boolean): Map<String, Any> {
        val subject = SecurityUtils.getSubject()
        val usernamePasswordToken = UsernamePasswordToken(userName,userPwd)
        //ajax方式登录
        usernamePasswordToken.isRememberMe = rememberMe

        val map = HashMap<String, Any>()
        //进行验证，捕获异常，返回对应信息
        try {
            subject.login(usernamePasswordToken)
            map["msg"] = "ok"
        } catch (e: LockedAccountException) {
            usernamePasswordToken.clear()

            map["msg"] = "state:0"
        } catch (e: AuthenticationException) {
            usernamePasswordToken.clear()

            map["msg"] = "user:null"
        }

        return map
    }

    @Log("注销")
    @ApiOperation("注销")
    @GetMapping("/logout")
    protected open fun logout(): String {
        ShiroUtils.logout()
        return "ok"
    }

}
