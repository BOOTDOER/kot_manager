package com.manager.common.log

import com.manager.common.shiro.util.ShiroUtils
import com.manager.common.tools.HttpContextUtils
import com.manager.core.model.SysUser
import org.apache.shiro.SecurityUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*
import javax.servlet.http.HttpSession

/**
 * ******************************记录操作日志******************************
 *                (其 实 我 只 是 想 练 练 lambda 表 达 式 hahaha)
 *  !注意!
 *      1.class : open / public
 *      2.method : !private and !final
 *
 *  推荐方案
 *      class  : open / public
 *      method : protected
 * @author lin
 * @version 1.0
 */
@Aspect
@Component
open class LogAspect {

    @Pointcut("@annotation(com.manager.common.log.Log)")
    private fun annotation() {}

    /**
     * 获取注解内容，生成日志存储
     */
    @Around("annotation()")
    protected open fun saveLog(point: ProceedingJoinPoint): Any? {
        var obj: Any? = null
        try{
            //制作日志--表达式
            val log: (duration: Long) -> LogModel = {

                val request = HttpContextUtils.getRequest()
                val response = HttpContextUtils.getResponse()

                val ip =
                        if (request.remoteAddr != "0:0:0:0:0:0:0:1")
                            request.remoteAddr
                        else "127.0.0.1"

                //生成模型
                LogModel(userId = ShiroUtils.user?.userId ?: -1,//用户id
                        operation = getSignature(point).getAnnotation(Log::class.java).value,//用户操作
                        method = getSignature(point).name,//对应方法
                        params = point.args.joinToString(","),//方法参数
                        duration = it,//共计时间
                        ip = ip, //用户ip
                        status = response.status,
                        userAgent = request.getHeader("user-agent"),
                        time = Date())//请求时间
            }

            //1.开始时间
            val beginTime: Long = System.currentTimeMillis()


            //2.执行方法
                obj = point.proceed()


            //3.执行时长(毫秒)
            val time: Long = System.currentTimeMillis() - beginTime

            //4.生成并处理日志
            println("info:"+log(time).toString())

        }catch(e: Exception) {
            println("出现异常")
        }

        return obj
    }

    /**
     * 获取方法签名
     */
    private val getSignature: (point: ProceedingJoinPoint) -> Method = {
        (it.signature!! as MethodSignature).method
    }

}