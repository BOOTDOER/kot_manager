package com.manager.common.queue

/**
 * 队列元素
 */
data class MQueueItem (
    var sessionId: String?  = null,
    var methodName: Any/*KFunction1<@ParameterName(name = "param") T, Any>*/,
    var param: Any? =null
)

