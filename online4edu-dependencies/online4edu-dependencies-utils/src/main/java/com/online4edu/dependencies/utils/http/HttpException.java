package com.online4edu.dependencies.utils.http;

/**
 * Custom Http Client Exception
 *
 * @author magicianlib@gmail.com
 * @since 2021/12/24 20:43
 */
public class HttpException extends RuntimeException {

    public HttpException() {
        super();
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
