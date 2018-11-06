package com.manager.core.dao


import com.manager.core.model.Role

interface RoleDao {
    fun getList(userId: Long?): List<Role>
}
