package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * 日期类型转换器（支持控制器参数直接接收 LocalDate 时间类型）
 *
 * @author xinggang
 * @create 2021-12-29
 */
//此配置不支持 lamdba 语法，否则异常：Unable to determine source type <S> and target type <T> for your Converter
@Suppress("ObjectLiteralToLambda")
@Configuration
class DateConverterConfig {

    @Bean
    fun localDateConverter(): Converter<String, LocalDate> {
        return object : Converter<String, LocalDate> {
            override fun convert(source: String): LocalDate {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            }
        }
    }

    @Bean
    fun localDateTimeConverter(): Converter<String, LocalDateTime> {
        return object : Converter<String, LocalDateTime> {
            override fun convert(source: String): LocalDateTime {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            }
        }
    }

    @Bean
    fun localTimeConverter(): Converter<String, LocalTime> {
        return object : Converter<String, LocalTime> {
            override fun convert(source: String): LocalTime {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern("HH:mm:ss"))
            }
        }
    }
}