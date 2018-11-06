package com.manager.core.model

import java.io.Serializable


/**
 * @description 角色
 * @author lin
 * @version 1.0
 */
data class Role (
    var roleId: Int? = null,
    var roleName: String? = null
): Serializable