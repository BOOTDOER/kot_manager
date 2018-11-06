package com.manager.core.dao

import com.manager.core.model.SysUser

interface UserDao {
    fun getList(map: HashMap<String, Any>?): List<SysUser>

    fun get(user: SysUser): SysUser
}
