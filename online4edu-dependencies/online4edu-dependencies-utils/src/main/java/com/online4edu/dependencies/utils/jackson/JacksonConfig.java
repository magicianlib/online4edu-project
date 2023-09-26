package com.online4edu.dependencies.utils.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.online4edu.dependencies.utils.datetime.DateFormatUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Jackson Config
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/12/24 20:40
 */
public final class JacksonConfig {

    /**
     * 配置 Java8 日期处理格式
     *
     * @param objectMapper 实例
     */
    public static void configureObjectMapper4Jsr310(ObjectMapper objectMapper) {

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // LocalTime 序列化和反序列化配置
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateFormatUtil.FORMAT_TIME));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateFormatUtil.FORMAT_TIME));

        // LocalDate 序列化和反序列化配置
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateFormatUtil.FORMAT_DATE));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateFormatUtil.FORMAT_DATE));

        // LocalDateTime 序列化和反序列化配置
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateFormatUtil.FORMAT_DATETIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateFormatUtil.FORMAT_DATETIME));

        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 序列化对 Null 值处理
     *
     * @param objectMapper 实例
     */
    public static void configureNullObject(ObjectMapper objectMapper) {

        SerializerFactory serializerFactory = objectMapper.getSerializerFactory()
                .withSerializerModifier(new JacksonBeanNullValueSerializerModifier());

        objectMapper.setSerializerFactory(serializerFactory);
    }

    /**
     * 添加自定义序列化实现
     *
     * @param objectMapper 实例
     */
    public static <T> void registerModule(ObjectMapper objectMapper, Class<? extends T> type, JsonSerializer<T> serializer) {

        SimpleModule module = new SimpleModule();
        module.addSerializer(type, serializer);

        objectMapper.registerModule(module);
    }

    /**
     * 添加自定义反序列化实现
     *
     * @param objectMapper 实例
     */
    public static <T> void registerModule(ObjectMapper objectMapper, Class<T> type, JsonDeserializer<? extends T> deserializer) {

        SimpleModule module = new SimpleModule();
        module.addDeserializer(type, deserializer);

        objectMapper.registerModule(module);
    }
}
