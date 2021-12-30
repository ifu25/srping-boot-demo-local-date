package com.example.demo

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * 时间参数接收 Demo
 *
 * @author xinggang
 * @create 2021-12-29
 */
@RestController
class HomeController {

    @RequestMapping("/")
    fun index(@RequestParam date: LocalDate, @RequestParam time: LocalTime, @RequestParam datetime: LocalDateTime): Any {

        //说明：实现日期接收有3种方案，参考 README.md

        println(date)
        println(time)
        println(datetime)

        return datetime
    }
}