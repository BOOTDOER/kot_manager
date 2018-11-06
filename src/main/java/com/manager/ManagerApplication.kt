package com.manager

import com.manager.common.queue.MQueueService
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@MapperScan("com.manager.*.dao")
//开启缓存
@EnableScheduling
@EnableCaching
open class ManagerApplication {


}
fun main(args: Array<String>) {

    //开启消息队列
    MQueueService.instance.start()

    runApplication<ManagerApplication>(*args)

    print("============================= 项目启动成功 =============================\n" +
            "  _       __     __                                  __  ___        __    _\n" +
            " | |     / /__  / /________  ____ ___  ___          /  |/  /____   / /   (_)___\n" +
            " | | /| / / _ \\/ / ___/ __ \\/ __ `__ \\/ _ \\        / /|_/ / ___/  / /   / / __ \\     \n" +
            " | |/ |/ /  __/ / /__/ /_/ / / / / / /  __/  _    / /  / / /     / /___/ / / / /  _\n" +
            " |__/|__/\\___/_/\\___/\\____/_/ /_/ /_/\\___/  ( )  /_/  /_/_/     /_____/_/_/ /_/  (_)\n"
    )


}
