package com.manager.common.shiro.util

import com.manager.core.model.SysUser
import org.apache.shiro.SecurityUtils
import org.apache.shiro.session.mgt.eis.SessionDAO
import org.apache.shiro.subject.Subject

import org.springframework.beans.factory.annotation.Autowired

object ShiroUtils {
    @Autowired
    private val sessionDAO: SessionDAO? = null

    val subjct: Subject
        get() = SecurityUtils.getSubject()

    val user: SysUser?
        get() = subjct.principal as? SysUser

    val userId: Long?
        get() = user?.userId

    /*val principles: List<Principal>?
        get() {
            val principals: List<Principal>? = null
            val sessions = sessionDAO!!.activeSessions
            return principals
        }*/

    fun logout() {
        subjct.logout()
    }
}
