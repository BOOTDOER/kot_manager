package com.manager.common.shiro.config

import com.manager.core.model.SysUser
import com.manager.core.service.UserService
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy

class UserRealm : AuthorizingRealm() {
    //用于用户查询
    @Autowired
    @Lazy
    private val userService: UserService? = null

    /**
     * 访问控制，即用户是否具有该操作的权限
     * 当访问到页面时，只有配置了相应的权限或者shiro标签的链接才会执行此方法，否则不会执行
     * @param principals
     * @return
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo? {
        /*
        Long uid = ShiroUtils.getUserId();
        Set<String> perms = permissionService.findPermissionSetByUid(uid);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        //设置登录次数、时间
//        userService.updateUserLogin(user);
        return info;
        */
        return null
    }

    /**
     * 用户身份识别，即用户“登录”功能，返回三种异常，调用层进行处理即可
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo {
        val userName = token.principal as String
        val userPwd = String(token.credentials as CharArray)

        val user = SysUser(userName = userName, userPwd = userPwd)
        // 查询用户信息
        val sysUser = userService!!.get(user) ?: throw UnknownAccountException("账号不存在")

        // 账号不存在
        // 密码错误
        if (userPwd != sysUser.userPwd) {
            throw IncorrectCredentialsException("账号或密码不正确")
        }
        // 账号锁定
        if (sysUser.state.toInt() == 0) {
            throw LockedAccountException("账号已被锁定")
        }
        return SimpleAuthenticationInfo(sysUser, userPwd, name)
    }

}
