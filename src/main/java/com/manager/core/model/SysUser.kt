package com.manager.core.model

import java.io.Serializable

/**
 * @description 用户
 * @author lin
 * @version 1.0
 */
class SysUser (
    var userId: Long? = null,
    var userName: String? = null,
    var userPwd: String? = null,
    var roleList: List<Role>? = null,
    var userImg: String? = null,
    var userPhone: Long? = null,
    var state: Short = 0
) : Serializable
