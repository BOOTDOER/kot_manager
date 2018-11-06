package com.manager.test

import kotlin.reflect.KFunction1

//线程的工作方法
fun main(args: Array<String>) {

    val work = Work()
    val obj = Run(work::doSomething, 1)     //intoQueue

    obj.run()

}
//service
class Work {
    fun doSomething(item: Int) {
        print("do$item")
    }

    fun update(item: HashMap<String, Any>) {
        print("do$item")
    }
}

//队列处理方式
class Run<T>(private val method: KFunction1<@ParameterName(name = "item") T, Unit>,
          private val item: T) {
    fun run() {
        method(item)
    }
}
