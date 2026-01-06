package io.ituknown.utils.http;

import lombok.Getter;
import org.apache.http.HttpHost;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Http Client Request Config
 *
 * @author magicianlib@gmail.com
 * @see org.apache.http.client.config.RequestConfig.Builder
 * @since 2021/12/24 20:42
 */
@Getter
public class CustomRequestConfig {

    /**
     * 开启重定向
     */
    private boolean enabledRedirect;

    /**
     * 获取连接超时时间
     */
    private int connectionRequestTimeout;

    /**
     * 建立连接超时时间
     */
    private int connectTimeout;

    /**
     * 获取响应超时时间
     */
    private int socketTimeout;

    /**
     * only for httpAsyncClient BaseIOReactor 验证超时的时间间隔
     */
    private long selectTimeout;

    /**
     * 请求代理
     */
    private HttpHost proxy;

    /**
     * 请求头
     */
    private final Map<String, Object> headers;

    public CustomRequestConfig() {
        this(0);
    }

    public CustomRequestConfig(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        this.enabledRedirect = true;
        this.headers = new HashMap<>(8);
    }

    public void setEnabledRedirect(boolean enabledRedirect) {
        this.enabledRedirect = enabledRedirect;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setSelectTimeout(long selectTimeout) {
        this.selectTimeout = selectTimeout;
    }

    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers.putAll(headers);
    }

    public void addHead(final String name, final Object value) {
        this.headers.put(name, value);
    }

}