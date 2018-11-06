package com.manager.common.shiro.config

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect
import org.apache.shiro.cache.ehcache.EhCacheManager
import org.apache.shiro.codec.Base64
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.CookieRememberMeManager
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.servlet.SimpleCookie
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.crazycake.shiro.RedisCacheManager

import org.crazycake.shiro.RedisManager

import org.crazycake.shiro.RedisSessionDAO

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.web.filter.DelegatingFilterProxy

import java.util.HashMap

@Configuration
open class ShiroConfig {
    @Value("\${hello}")
    private val host = "localhost"
    @Value("\${hello}")
    private val port = 7777
    @Value("\${spring.redis.timeout}")
    private val timeout = 1000
    //过期时间
    @Value("\${spring.redis.expire}")
    private val expire = 1800

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     * @param securityManager
     * @return
     */
    @Bean
    open fun shiroFilterFactoryBean(securityManager: SecurityManager): ShiroFilterFactoryBean {
        val shiroFilterFactoryBean = ShiroFilterFactoryBean()
        shiroFilterFactoryBean.securityManager = securityManager

        shiroFilterFactoryBean.loginUrl = "/login.html" //登录页面的跳转 -- 访问非过滤资源时跳转的页面
        /**
         * 放行资源
         * 注： LinkedHashMap莫名报错
         * user： rememberMe || 认证通过可以访问
         * anon： 匿名访问
         * authc: 认证通过才可以访问
         */
        val map = HashMap<String, String>()
        map["/**"] = "user"
        map["/login.html"] = "anon"
        map["/js/**"] = "anon"
        map["/logout"] = "logout"

        //shiroFilter


        shiroFilterFactoryBean.filterChainDefinitionMap = map
        return shiroFilterFactoryBean
    }

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean
    open fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }

    /**
     * 验证方式
     * @return
     */
    @Bean
    open fun myShiroRealm(): UserRealm {
        return UserRealm()
    }

    /**
     * EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，
     * 然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    open fun ehCacheManager(): EhCacheManager {
        return EhCacheManager()
    }


    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean
    open fun shiroDialect(): ShiroDialect {
        return ShiroDialect()
    }


    /**
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    /*@Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }*/


    /**
     * 权限管理，组合了登陆，登出，权限，session的处理，是个比较重要的类。
     * @return
     */
    @Bean
    open fun securityManager(): SecurityManager {
        val securityManager = DefaultWebSecurityManager()
        //设置 Realm
        securityManager.setRealm(myShiroRealm())
        //设置 缓存实现 使用redis
        securityManager.cacheManager = cacheManager()
        //设置 session管理 使用redis
        securityManager.sessionManager = sessionManager()
        //设置 rememberMe
        securityManager.rememberMeManager = rememberMeManager()
        return securityManager
    }

    /**
     * 加入注解的使用
     * @param securityManager
     * @return
     */
    @Bean
    open fun authorizationAttributeSourceAdvisor(securityManager: SecurityManager): AuthorizationAttributeSourceAdvisor {
        val authorizationAttributeSourceAdvisor = AuthorizationAttributeSourceAdvisor()
        authorizationAttributeSourceAdvisor.securityManager = securityManager
        return authorizationAttributeSourceAdvisor
    }

    /**
     * cookie对象;
     * @return
     */
    fun rememberMeCookie(): SimpleCookie {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        val simpleCookie = SimpleCookie("rememberMe")

        //记住我cookie生效时间30天 ,单位秒;
        simpleCookie.maxAge = 30 * 24 * 60 * 60
        return simpleCookie
    }

    /**
     * cookie管理对象 : "rememberMe"功能
     * @return
     */
    fun rememberMeManager(): CookieRememberMeManager {
        val cookieRememberMeManager = CookieRememberMeManager()
        cookieRememberMeManager.cookie = rememberMeCookie()
        //rememberMe cookie加密的密钥  默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.cipherKey = Base64.decode("3AvVhmFLUs0KTA3Kprsdag==")
        return cookieRememberMeManager
    }


    /**
     * 配置shiro redisManager
     *
     *
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    open fun redisManager(): RedisManager {
        val redisManager = RedisManager()
        redisManager.host = host
        redisManager.port = port
        redisManager.expire = expire// 配置缓存过期时间
        redisManager.timeout = timeout
        //redisManager.setPassword(password);
        return redisManager
    }


    /**
     * cacheManager 缓存 redis实现
     *
     *
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    fun cacheManager(): RedisCacheManager {
        val redisCacheManager = RedisCacheManager()
        redisCacheManager.redisManager = redisManager()
        return redisCacheManager

    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     *
     *
     * 使用的是shiro-redis开源插件
     */
    @Bean
    open fun redisSessionDAO(): RedisSessionDAO {
        val redisSessionDAO = RedisSessionDAO()
        redisSessionDAO.redisManager = redisManager()
        return redisSessionDAO
    }

    /**
     * shiro session的管理
     */
    @Bean
    open fun sessionManager(): DefaultWebSessionManager {
        val sessionManager = DefaultWebSessionManager()
        sessionManager.sessionDAO = redisSessionDAO()
        return sessionManager
    }

}
