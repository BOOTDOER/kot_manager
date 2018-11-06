package com.manager.core.dao


import com.manager.core.model.Menu

interface MenuDao {
    fun getList(roleId: Int?): List<Menu>
}
