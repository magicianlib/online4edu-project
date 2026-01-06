package io.ituknown.mybatis.generator.plugin;

/**
 * 异常类
 *
 * @author magicianlib@gmail.com
 * @since 2021/03/12 10:21
 */
public class MapperException extends RuntimeException {

    public MapperException() {
        super();
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

}