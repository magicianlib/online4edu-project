package com.online4edu.dependencies.utils.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.online4edu.dependencies.utils.datetime.DateFormatUtil;

import java.io.IOException;
import java.time.LocalTime;

/**
 * Jackson 自定义时间反序列化扩展
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 16:38
 */
public class LocalTimeJsonSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime time, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeString(time.format(DateFormatUtil.ISO_LOCAL_TIME));
    }
}