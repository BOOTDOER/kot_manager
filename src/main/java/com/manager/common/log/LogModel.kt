package com.manager.common.log

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class LogModel (
        var id: Long? = 0,
        var userId: Long? = 0,
        var operation: String? = null,
        var method: String? = null,
        var params: String? = null,
        var duration: Long = 0,
        var ip: String? = null,
        var status: Int? = 0,
        var userAgent: String? = null,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var time: Date? = null
)