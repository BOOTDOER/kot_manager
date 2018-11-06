package com.manager.core.service.impl

import com.manager.core.dao.UserDao
import com.manager.core.model.SysUser
import com.manager.core.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["SysUser"])
open class UserServiceImpl : UserService {
    @Autowired
    private var userMapper: UserDao? = null

    @Cacheable
    override fun getList(map: HashMap<String, Any>?): List<SysUser> {
        println("如果第二次没有走到这里说明缓存被添加了")
        return userMapper!!.getList(map)
    }


    override fun get(user: SysUser): SysUser {
        return userMapper!!.get(user)
    }

}
