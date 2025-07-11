package com.online4edu.dependencies.utils.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.online4edu.dependencies.utils.datetime.DateFormatUtil;
import com.online4edu.dependencies.utils.exception.DeserializationException;
import com.online4edu.dependencies.utils.exception.SerializationException;
import com.online4edu.dependencies.utils.jackson.serializer.BigDecimalAsStringJsonSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Jackson JSON 序列化/反序列化工具类
 *
 * @author magicianlib@gmail.com
 * @see com.fasterxml.jackson.annotation
 */
public final class JacksonUtil {

    private static final ObjectMapper MAPPER_WITH_FORMAT = createObjectMapper(true);
    private static final ObjectMapper MAPPER_WITHOUT_FORMAT = createObjectMapper(false);

    /**
     * 获取 ObjectMapper 实例
     */
    public static ObjectMapper createMapper() {
        return createMapper(false);
    }

    public static ObjectMapper createMapper(boolean format) {
        return format ? MAPPER_WITH_FORMAT : MAPPER_WITHOUT_FORMAT;
    }

    /**
     * 创建 ObjectMapper 对象
     */
    public static ObjectMapper createObjectMapper(boolean format) {
        ObjectMapper mapper = new ObjectMapper();

        // 格式化输出
        // mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, format);

        // 忽略未知字段
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 序列化所有字段
        // mapper.setSerializationInclusion(Include.ALWAYS);

        // 设置时区
        mapper.setTimeZone(TimeZone.getDefault());

        // 忽略 transient 字段
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

        // java.util.Date 日期格式 处理
        mapper.setDateFormat(new SimpleDateFormat(DateFormatUtil.DATE_TIME_PATTERN));

        // java.time.* 日期格式处理
        JacksonConfig.configureObjectMapper4Jsr310(mapper);

        // Null 值处理
        JacksonConfig.configureNullObject(mapper);

        // BigDecimal 自定义序列化
        JacksonConfig.registerModule(mapper, BigDecimal.class, new BigDecimalAsStringJsonSerializer());

        return mapper;
    }


    // ====================== ObjectMapper ======================

    public static String toJson(Object obj) {
        return toJson(obj, false);
    }

    /**
     * Object to json string.
     *
     * @param obj obj
     * @return json string
     * @throws SerializationException if transfer failed
     */
    public static String toJson(Object obj, boolean format) {
        try {
            return createMapper(format).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializationException(obj.getClass(), e);
        }
    }

    public static byte[] toJsonBytes(Object obj) {
        return toJsonBytes(obj, false);
    }

    /**
     * Object to json string byte array.
     *
     * @param obj obj
     * @return json string byte array
     * @throws SerializationException if transfer failed
     */
    public static byte[] toJsonBytes(Object obj, boolean format) {
        try {
            String json = createMapper(format).writeValueAsString(obj);
            if (StringUtils.isNotBlank(json)) {
                return json.getBytes(StandardCharsets.UTF_8);
            }
            return new byte[0];
        } catch (JsonProcessingException e) {
            throw new SerializationException(obj.getClass(), e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json  json string
     * @param clazz class of object
     * @param <T>   General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(byte[] json, Class<T> clazz) {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), clazz);
        } catch (Exception e) {

            throw new DeserializationException(clazz, e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json  json string
     * @param clazz {@link Type} of object
     * @param <T>   General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(byte[] json, Type clazz) {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), clazz);
        } catch (Exception e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param clazz       class of object
     * @param <T>         General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Class<T> clazz) {
        try {
            return MAPPER_WITHOUT_FORMAT.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string byte array
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), typeReference);
        } catch (Exception e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json  json string
     * @param clazz class of object
     * @param <T>   General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        try {
            return MAPPER_WITHOUT_FORMAT.readValue(json, clazz);
        } catch (IOException e) {
            throw new DeserializationException(clazz, e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param type {@link Type} of object
     * @param <T>  General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String json, Type type) {
        try {
            return MAPPER_WITHOUT_FORMAT.readValue(json, MAPPER_WITHOUT_FORMAT.constructType(type));
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER_WITHOUT_FORMAT.readValue(json, typeReference);
        } catch (IOException e) {
            throw new DeserializationException(typeReference.getClass(), e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param type        {@link Type} of object
     * @param <T>         General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Type type) {
        try {
            return MAPPER_WITHOUT_FORMAT.readValue(inputStream, MAPPER_WITHOUT_FORMAT.constructType(type));
        } catch (IOException e) {
            throw new DeserializationException(type, e);
        }
    }

    /**
     * Json string deserialize to Jackson {@link JsonNode}.
     *
     * @param json json string
     * @return {@link JsonNode}
     * @throws DeserializationException if deserialize failed
     */
    public static ObjectNode toObj(String json) {
        return toObj(json, ObjectNode.class);
    }

    public static <T> T toObj(String json, Class<T> parametrized, Class<?>... parameterClasses) {
        try {
            JavaType javaType = MAPPER_WITHOUT_FORMAT.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return MAPPER_WITHOUT_FORMAT.readValue(json, javaType);
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * 将 JSON 字符串转换为指定集合类型
     */
    public static <C extends Collection<E>, E> C toCollection(String json, Class<C> collection, Class<E> element) {
        try {
            CollectionType type = MAPPER_WITHOUT_FORMAT.getTypeFactory().constructCollectionType(collection, element);
            return MAPPER_WITHOUT_FORMAT.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Parse json to Collection<E> failed:" + json, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> ArrayList<E> toCollection(String json, Class<E> element) {
        return toCollection(json, ArrayList.class, element);
    }

    /**
     * 将 JSON 字符串转为 Map
     * <pre>
     * HashMap<String, String> data = toMap(jsonMap, HashMap.class, String.class, String.class);
     * </pre>
     *
     * @throws RuntimeException 如果解析失败
     */
    public static <K, V, H extends Map<K, V>> H toMap(String json, Class<H> map, Class<K> key, Class<V> value) {
        try {
            MapType type = MAPPER_WITHOUT_FORMAT.getTypeFactory().constructMapType(map, key, value);
            return MAPPER_WITHOUT_FORMAT.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Parse json to Map<K, V> failed:" + json, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> toMap(String json, Class<K> key, Class<V> value) {
        return toMap(json, HashMap.class, key, value);
    }

    /**
     * 将 JSON 字符串转换为指定集合Map类型
     */
    public static <K, V, H extends Map<K, V>, C extends Collection<H>> C toCollectionMap(String json, Class<C> collection, Class<H> map, Class<K> key, Class<V> value) {
        try {
            MapType mapType = MAPPER_WITHOUT_FORMAT.getTypeFactory().constructMapType(map, key, value);
            CollectionType collectionType = MAPPER_WITHOUT_FORMAT.getTypeFactory().constructCollectionType(collection, mapType);
            return MAPPER_WITHOUT_FORMAT.readValue(json, collectionType);
        } catch (Exception e) {
            throw new RuntimeException("Parse json to Collection<Map<K, V>>> failed:" + json, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> ArrayList<HashMap<K, V>> toCollectionMap(String json, Class<K> key, Class<V> value) {
        return toCollectionMap(json, ArrayList.class, HashMap.class, key, value);
    }

    /**
     * Register sub type for child class.
     *
     * @param clazz child class
     * @param type  type name of child class
     */
    public static void registerSubtype(Class<?> clazz, String type) {
        registerSubtype(clazz, type, false);
    }

    public static void registerSubtype(Class<?> clazz, String type, boolean format) {
        createMapper(format).registerSubtypes(new NamedType(clazz, type));
    }

    /**
     * Create a new empty Jackson {@link ObjectNode}.
     *
     * @return {@link ObjectNode}
     */
    public static ObjectNode createEmptyJsonNode() {
        return createEmptyJsonNode(false);
    }

    public static ObjectNode createEmptyJsonNode(boolean format) {
        return new ObjectNode(createMapper(format).getNodeFactory());
    }


    /**
     * Create a new empty Jackson {@link ArrayNode}.
     *
     * @return {@link ArrayNode}
     */
    public static ArrayNode createEmptyArrayNode() {
        return createEmptyArrayNode(false);
    }

    public static ArrayNode createEmptyArrayNode(boolean format) {
        return new ArrayNode(createMapper(format).getNodeFactory());
    }

    /**
     * Parse object to Jackson {@link JsonNode}.
     *
     * @param obj object
     * @return {@link JsonNode}
     */
    public static JsonNode transferToJsonNode(Object obj) {
        return transferToJsonNode(obj, false);
    }

    public static JsonNode transferToJsonNode(Object obj, boolean format) {
        return createMapper(format).valueToTree(obj);
    }

    /**
     * construct java type -> Jackson Java Type.
     *
     * @param type java type
     * @return JavaType {@link JavaType}
     */
    public static JavaType constructJavaType(Type type) {
        return constructJavaType(type, false);
    }

    public static JavaType constructJavaType(Type type, boolean format) {
        return createMapper(format).constructType(type);
    }
}
