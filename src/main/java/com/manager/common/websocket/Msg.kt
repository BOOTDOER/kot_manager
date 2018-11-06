package com.manager.common.websocket

import java.util.*

/**
 * 消息体
 */
data class Msg (
    // 推送人id
    var fromUid: String? = null,
    // 定点推送人id
    var toUid: String? = null,
    // 消息体
    var text: String? = null,
    // 推送时间
    var date: Date? = null,
    // 消息状态
    var state: Int? = 0 //0：未读，1：已读，-1：发送失败（ 网络原因 or 断线 ）
)