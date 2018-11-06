package com.manager.core.model

import java.io.Serializable

/**
 * @description 菜单
 * @author lin
 * @version 1.0
 */
data class Menu (
    var menuId: Int? = null,
    var menuName: String? = null,
    var menuIcon: String? = null,
    var menuUrl: String? = null,
    var menuPermission: String? = null
) : Serializable
