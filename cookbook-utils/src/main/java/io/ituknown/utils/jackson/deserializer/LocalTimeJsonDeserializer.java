package io.ituknown.utils.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.ituknown.utils.datetime.DateFormatUtils;

import java.io.IOException;
import java.time.LocalTime;

/**
 * Jackson 自定义时间序列化扩展
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/13 16:38
 */
public class LocalTimeJsonDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return LocalTime.parse(parser.getText(), DateFormatUtils.ISO_LOCAL_TIME_FORMATTER);
    }
}