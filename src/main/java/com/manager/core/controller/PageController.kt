package com.manager.core.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
open class PageController {

    @GetMapping("/login.html")
    open fun index(): String {
        return "login"
    }

    /**
     * 请求资源统一返回主页
     */

    /**
     * 主页
     * @return
     */
    @GetMapping("/index.html", "/", "")
    open fun index(model: ModelMap): String {
        return "index"
    }

}
