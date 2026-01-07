package io.ituknown.utils.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.ituknown.utils.datetime.DateFormatUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Jackson 自定义日期时间反序列化扩展
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/13 16:38
 */
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(dateTime.format(DateFormatUtils.DATE_TIME_FORMATTER));
    }
}