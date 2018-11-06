package com.manager.common.log

@Target(AnnotationTarget.FUNCTION)
annotation class Log (
    val value:String
)