package com.online4edu.dependencies.utils.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.online4edu.dependencies.utils.datetime.DateFormatUtil;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Jackson 自定义日期反序列化扩展
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/13 16:38
 */
public class LocalDateJsonSerializer extends JsonSerializer<LocalDate> {
    
    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(date.format(DateFormatUtil.ISO_LOCAL_DATE));
    }
}