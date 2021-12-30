package com.example.demo.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter

/**
 * Jackson 配置类
 *
 * @author xinggang
 * @create 2021-12-06
 */
@Configuration
class JacksonConfig {

    /** 日期时间格式  */
    private val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    /** 日期格式  */
    private val DATE_FORMAT = "yyyy-MM-dd"

    /** 时间格式  */
    private val TIME_FORMAT = "HH:mm:ss"

    /**
     * 定制 ObjectMapper。
     * 1、支持 Java8 时间类型（格式设置见 jackson2ObjectMapperBuilderCustomizer()）
     * 2、属性命名策略：下划线转小驼峰
     */
    @Bean
    fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {

        //实例化
        val objectMapper = builder.createXmlMapper(false).build<ObjectMapper>()
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)

        //支持 Java8 时间类型
        objectMapper.registerModule(JavaTimeModule())

        //region ========== 自定义属性命名策略：小驼峰 ==========

        //自定义属性KEY命名策略，下划线转小驼峰（适用于Map等）
        val simpleModule = SimpleModule()

        objectMapper.registerModule(simpleModule)

        //endregion

        return objectMapper
    }

    /**
     * 定时 ObjectMapperBuiler，为 LocalDateTime 等日期、时间类型设置 Json 序列化、反序列化格式
     */
    @Bean
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer {
            it.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
            it.serializers(LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
            it.serializers(LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
            it.deserializers(LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
            it.deserializers(LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
            it.deserializers(LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
            it.serializerByType(Long::class.java, ToStringSerializer.instance)
            it.serializerByType(AnnotationTarget::class.java, ToStringSerializer.instance);
        }
    }
}