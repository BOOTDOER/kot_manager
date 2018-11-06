package com.manager.core.service

import com.manager.core.model.SysUser

interface UserService {

    fun getList(map: HashMap<String, Any>?): List<SysUser>

    fun get(user: SysUser): SysUser

}
