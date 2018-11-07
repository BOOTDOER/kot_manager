package com.manager

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
open class ProjectAPI {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    open fun controllerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(ApiInfoBuilder()
                .title("标题： kot 电商系统_接口文档")
                .description("描述：这个人很懒，什么都没留下")
                .contact(Contact("Mr Lin", null, null))
                .version("版本号:1.0")
                .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.manager"))
                .paths(PathSelectors.any())
                .build()
    }
}
