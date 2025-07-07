package com.online4edu.dependencies.utils.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.online4edu.dependencies.utils.datetime.DateFormatUtil;
import com.online4edu.dependencies.utils.exception.DeserializationException;
import com.online4edu.dependencies.utils.exception.SerializationException;
import com.online4edu.dependencies.utils.jackson.serializer.BigDecimalAsStringJsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Jackson XML 序列化/反序列化工具类
 *
 * @author magicianlib@gmail.com
 * @see com.fasterxml.jackson.dataformat.xml.annotation
 */
public class JacksonXmlUtil {
    /**
     * 用于生成带 XML 声明的 XML
     */
    private static final XmlMapper XML_WITH_DECLARATION = createMapper(true);
    /**
     * 用于生成不带 XML 声明的 XML
     */
    private static final XmlMapper XML_WITHOUT_DECLARATION = createMapper(false);

    /**
     * 创建并配置 XmlMapper 实例
     *
     * <p>
     * 如果 {@code includeDeclaration} 为 true，在转XML时会在顶部增加XML声明：
     * <pre>
     * <?xml version="1.0" encoding="UTF-8"?>
     * </pre>
     * </p>
     *
     * @param includeDeclaration 是否包含 XML 声明
     * @return 配置好的 XmlMapper 实例
     */
    private static XmlMapper createMapper(boolean includeDeclaration) {
        XmlMapper mapper = new XmlMapper();

        // 格式化输出
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // XML 声明
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, includeDeclaration);

        // 忽略未知字段
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 序列化所有字段
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

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

    public static XmlMapper createXmlMapper() {
        return createXmlMapper(false);
    }

    /**
     * 获取XML实例，自己指定是否需要XML声明：
     * <p><pre>
     * <?xml version="1.0" encoding="UTF-8"?>
     * </pre></p>
     */
    public static XmlMapper createXmlMapper(boolean includeDeclaration) {
        return includeDeclaration ? XML_WITH_DECLARATION : XML_WITHOUT_DECLARATION;
    }

    // ====================== XmlMapper ======================

    // region toXml

    /**
     * 对象转 XML 字符串
     *
     * @param obj obj
     * @return xml 字符串
     * @throws SerializationException if transfer failed
     */
    public static String toXml(Object obj) {
        return toXml(obj, false);
    }

    public static String toXml(Object obj, boolean includeDeclaration) {
        try {
            return createXmlMapper(includeDeclaration).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializationException(obj.getClass(), e);
        }
    }

    public static byte[] toXmlBytes(Object obj) {
        return toXmlBytes(obj, false);
    }

    public static byte[] toXmlBytes(Object obj, boolean includeDeclaration) {
        try {
            return createXmlMapper(includeDeclaration).writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new SerializationException(obj.getClass(), e);
        }
    }

    public static void toXml(Writer w, Object obj) {
        toXml(w, obj, false);
    }

    public static void toXml(Writer w, Object obj, boolean includeDeclaration) {
        try {
            createXmlMapper(includeDeclaration).writeValue(w, obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void toXml(OutputStream out, Object obj) {
        toXml(out, obj, false);
    }

    public static void toXml(OutputStream out, Object obj, boolean includeDeclaration) {
        try {
            createXmlMapper(includeDeclaration).writeValue(out, obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // endregion toXml
    // region toObj

    public static <T> T toObj(byte[] xml, Class<T> clazz) {
        return toObj(xml, clazz, false);
    }

    public static <T> T toObj(byte[] xml, Class<T> clazz, boolean includeDeclaration) {
        return toObj(new String(xml, StandardCharsets.UTF_8), clazz, includeDeclaration);
    }

    public static <T> T toObj(byte[] xml, Type clazz) {
        return toObj(new String(xml, StandardCharsets.UTF_8), clazz, false);
    }

    public static <T> T toObj(byte[] xml, TypeReference<T> typeReference) {
        return toObj(new String(xml, StandardCharsets.UTF_8), typeReference, false);
    }

    /**
     * XML InputStream 反序列化为对象
     *
     * @param inputStream        xml输入流
     * @param clazz              类型
     * @param includeDeclaration 是否包含 xml 声明
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Class<T> clazz, boolean includeDeclaration) {
        try {
            return createXmlMapper(includeDeclaration).readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }


    /**
     * XML 字符串反序列化为对象
     *
     * @param xml                xml字符串
     * @param type               类型
     * @param includeDeclaration 是否包含 xml 声明
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String xml, Type type, boolean includeDeclaration) {
        try {
            XmlMapper XML = createXmlMapper(includeDeclaration);
            return XML.readValue(xml, XML.constructType(type));
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * XML 字符串反序列化为对象
     *
     * @param xml                xml字符串
     * @param clazz              类型
     * @param includeDeclaration 是否包含 xml 声明
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String xml, Class<T> clazz, boolean includeDeclaration) {
        try {
            return createXmlMapper(includeDeclaration).readValue(xml, clazz);
        } catch (IOException e) {
            throw new DeserializationException(clazz, e);
        }
    }


    /**
     * XML 字符串反序列化为对象
     *
     * @param xml                xml字符串
     * @param typeReference      类型
     * @param includeDeclaration 是否包含 xml 声明
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String xml, TypeReference<T> typeReference, boolean includeDeclaration) {
        try {
            return createXmlMapper(includeDeclaration).readValue(xml, typeReference);
        } catch (IOException e) {
            throw new DeserializationException(typeReference.getClass(), e);
        }
    }

    /**
     * XML InputStream 反序列化为对象
     *
     * @param inputStream        输入流
     * @param type               类型
     * @param includeDeclaration 是否包含 xml 声明
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Type type, boolean includeDeclaration) {
        try {
            XmlMapper XML = createXmlMapper(includeDeclaration);
            return XML.readValue(inputStream, XML.constructType(type));
        } catch (IOException e) {
            throw new DeserializationException(type, e);
        }
    }

    public static <T> T toObj(String xml, Class<T> parametrized, Class<?>... parameterClasses) {
        return toObj(xml, false, parametrized, parameterClasses);
    }

    /**
     * XML 字符串反序列化为泛型类对象
     * <p><pre>
     *     String xml = "...";
     *     List<User> userList = toObj(xml, false, List.class, User.class);
     *     List<User<Role> userList = toObj(xml, false, List.class, User.class, Role.class);
     * </pre></p>
     *
     * @param xml                XML字符串
     * @param general            泛型类型
     * @param parameterClasses   泛型参数
     * @param includeDeclaration 是否包含 xml 声明
     * @return T
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String xml, boolean includeDeclaration, Class<T> general, Class<?>... parameterClasses) {
        try {
            XmlMapper XML = createXmlMapper(includeDeclaration);
            JavaType javaType = XML.getTypeFactory().constructParametricType(general, parameterClasses);
            return XML.readValue(xml, javaType);
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /*@SuppressWarnings("unchecked")
    public static <E> ArrayList<E> toCollection(String xml, Class<E> element) {
        return toCollection(xml, false, ArrayList.class, element);
    }

    @SuppressWarnings("unchecked")
    public static <E> ArrayList<E> toCollection(String xml, boolean includeDeclaration, Class<E> element) {
        return toCollection(xml, includeDeclaration, ArrayList.class, element);
    }

    public static <C extends Collection<E>, E> C toCollection(String xml, Class<C> collection, Class<E> element) {
        return toCollection(xml, false, collection, element);
    }*/

    /**
     * 将 XML 字符串转换为指定集合类型
     */
    /*public static <C extends Collection<E>, E> C toCollection(String xml, boolean includeDeclaration, Class<C> collection, Class<E> element) {
        try {
            XmlMapper XML = createXmlMapper(includeDeclaration);
            CollectionType type = XML.getTypeFactory().constructCollectionType(collection, element);
            return XML.readValue(xml, type);
        } catch (Exception e) {
            throw new RuntimeException("Parse xml to Collection<E> failed:" + xml, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> toMap(String xml, Class<K> key, Class<V> value) {
        return toMap(xml, HashMap.class, key, value);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> toMap(String xml, boolean includeDeclaration, Class<K> key, Class<V> value) {
        return toMap(xml, includeDeclaration, HashMap.class, key, value);
    }


    public static <K, V, H extends Map<K, V>> H toMap(String xml, Class<H> map, Class<K> key, Class<V> value) {
        return toMap(xml, false, map, key, value);
    }*/

    /**
     * 将 XML 字符串反序列化为 Map
     * <p><pre>
     * HashMap<String, String> data = toMap(xmlMap, HashMap.class, String.class, String.class);
     * </pre></p>
     *
     * @throws RuntimeException 如果解析失败
     */
    /*public static <K, V, H extends Map<K, V>> H toMap(String xml, boolean includeDeclaration, Class<H> map, Class<K> key, Class<V> value) {
        try {
            XmlMapper XML = createXmlMapper(includeDeclaration);
            MapType type = XML.getTypeFactory().constructMapType(map, key, value);
            return XML.readValue(xml, type);
        } catch (Exception e) {
            throw new RuntimeException("Parse xml to Map<K, V> failed:" + xml, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> ArrayList<HashMap<K, V>> toCollectionMap(String xml, Class<K> key, Class<V> value) {
        return toCollectionMap(xml, ArrayList.class, HashMap.class, key, value);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> ArrayList<HashMap<K, V>> toCollectionMap(String xml, boolean includeDeclaration, Class<K> key, Class<V> value) {
        return toCollectionMap(xml, includeDeclaration, ArrayList.class, HashMap.class, key, value);
    }

    public static <K, V, H extends Map<K, V>, C extends Collection<H>> C toCollectionMap(String xml, Class<C> collection, Class<H> map, Class<K> key, Class<V> value) {
        return toCollectionMap(xml, false, collection, map, key, value);
    }*/

    /**
     * 将 XML 字符串转换为指定集合Map类型
     */
    /*public static <K, V, H extends Map<K, V>, C extends Collection<H>> C toCollectionMap(String xml, boolean includeDeclaration, Class<C> collection, Class<H> map, Class<K> key, Class<V> value) {
        try {
            XmlMapper XML = createXmlMapper(includeDeclaration);
            MapType mapType = XML.getTypeFactory().constructMapType(map, key, value);
            CollectionType collectionType = XML.getTypeFactory().constructCollectionType(collection, mapType);
            return XML.readValue(xml, collectionType);
        } catch (Exception e) {
            throw new RuntimeException("Parse xml to Collection<Map<K, V>>> failed:" + xml, e);
        }
    }*/

    // endregion toObj
}
