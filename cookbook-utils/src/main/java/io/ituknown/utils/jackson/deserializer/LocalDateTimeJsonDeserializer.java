package io.ituknown.utils.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.ituknown.utils.datetime.DateFormatUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Jackson 自定义日期时间序列化扩展
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/13 16:38
 */
public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return LocalDateTime.parse(parser.getText(), DateFormatUtils.DATE_TIME_FORMATTER);
    }
}