package com.manager.common.queue

/**
 * 队列 任务 元素
 */
data class Task (
    var sessionId: String?  = null,
    var methodName: Any/*KFunction1<@ParameterName(name = "param") T, Any>*/,
    var param: Any? =null
)

