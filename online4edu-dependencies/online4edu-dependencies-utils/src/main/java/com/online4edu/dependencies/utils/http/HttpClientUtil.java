package com.online4edu.dependencies.utils.http;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HeaderElement;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 客户端工具
 *
 * @author magicianlib@gmail.com
 * @since 2021/12/24 20:43
 */
public class HttpClientUtil {

    private HttpClientUtil() {
    }

    private static final AtomicBoolean init = new AtomicBoolean(false);

    private static RequestConfig defaultRequestConfig;

    private static CloseableHttpClient httpClient;

    /**
     * 从连接池获取连接的超时时间
     */
    public static final int CONNECTION_REQUEST_TIMEOUT = 3000;

    /**
     * 客户端和服务器建立连接超时时间 {@link org.apache.http.conn.ConnectTimeoutException}
     */
    public static final int CONNECT_TIMEOUT = 3000;

    /**
     * 成功建立连接后, 客户端从服务器读取数据的超时时间 {@link java.net.SocketTimeoutException}
     */
    public static final int SOCKET_TIMEOUT = 10000;

    /**
     * 默认每路由最大并发数
     */
    public static final int DEFAULT_MAX_PER_ROUTE = 20;

    /**
     * 全局最大并发数
     */
    public static final int MAX_TOTAL = 500;

    private static synchronized void init(int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        if (init.get()) {
            return;
        }

        // 连接池
        PoolingHttpClientConnectionManager conMgr = new PoolingHttpClientConnectionManager();
        conMgr.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        conMgr.setMaxTotal(MAX_TOTAL);

        // 默认连接配置
        defaultRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        // 活跃策略
        ConnectionKeepAliveStrategy strategy = (response, ctx) -> {
            BasicHeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement e = it.nextElement();
                String n = e.getName();
                String v = e.getValue();
                if (v != null && n.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(v) * 1000;
                }
            }
            // 如果没有约定, 则默认定义时长为 60s
            return 60 * 1000;
        };

        // 客户端实例
        httpClient = HttpClientBuilder.create()
                .setConnectionManager(conMgr)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setKeepAliveStrategy(strategy)
                .build();

        // 监控线程, 对异常和空闲线程进行关闭
        IdleConnectionMonitorThread idleMonitorThread = new IdleConnectionMonitorThread(conMgr);
        idleMonitorThread.setDaemon(true);
        idleMonitorThread.start();

        // Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(idleMonitorThread::shutdown));

        init.set(true);
    }

    /**
     * Get Request
     */
    public static String requestGet(String url) {
        return requestGet(url, null);
    }

    public static String requestGet(String url, Map<String, Object> params) {
        return requestGet(url, params, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestGet(String url, Map<String, Object> params, CustomRequestConfig customRequestConfig) {

        HttpGet httpGet = HttpWrapper.createGet(url, params);
        return doRequest(httpGet, customRequestConfig);
    }

    /**
     * Post for application/x-www-form-urlencoded
     */
    public static String requestPostForm(String url, Map<String, Object> params) {
        return requestPostForm(url, params, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostForm(String url, Map<String, Object> params, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostForm(url, params);
        return doRequest(httpPost, customRequestConfig);
    }

    /**
     * Post for application/json
     */
    public static String requestPostJson(String url, String json) {
        return requestPostJson(url, json, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostJson(String url, String json, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostJson(url, json);
        return doRequest(httpPost, customRequestConfig);
    }

    public static String requestPostJson(String url, Object obj) {
        return requestPostJson(url, obj, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostJson(String url, Object obj, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostJson(url, obj);
        return doRequest(httpPost, customRequestConfig);
    }

    /**
     * Post for application/xml
     */
    public static String requestPostXml(String url, String xml) {
        return requestPostXml(url, xml, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostXml(String url, String xml, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostXml(url, xml);
        return doRequest(httpPost, customRequestConfig);
    }

    public static String requestPostXml(String url, Object obj) {
        return requestPostXml(url, obj, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostXml(String url, Object obj, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostXml(url, obj);
        return doRequest(httpPost, customRequestConfig);
    }


    /**
     * File upload
     */
    public static String requestPostFile(String url, File file) {
        return requestPostFile(url, file, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, File file, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, file);
        return doRequest(httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, File file, ContentType contentType) {
        return requestPostFile(url, file, contentType, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, File file, ContentType contentType, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, file, contentType);
        return doRequest(httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String filename, String fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, File fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, InputStream fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, byte[] fileContent) {
        return requestPostFile(url, filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, String fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, File fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, InputStream fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String filename, byte[] fileContent, CustomRequestConfig customRequestConfig) {
        return requestPostFile(url, "file", filename, fileContent, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String name, String filename, String fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String name, String filename, File fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String name, String filename, InputStream fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(httpPost, customRequestConfig);
    }

    public static String requestPostFile(String url, String name, String filename, byte[] fileContent, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, filename, fileContent);
        return doRequest(httpPost, customRequestConfig);
    }

    /**
     * Batch File Uploading
     * <p>
     * The map object stores multiple files to be uploaded. Key is the
     * name of the file, value is the file data corresponding to the file
     * name, which can be {@link InputStream} and {@link File} and {@link String} and byte[]
     */
    public static String requestPostFile(String url, Map<String, Object> content) {
        return requestPostFile(url, "file", content);
    }

    public static String requestPostFile(String url, String name, Map<String, Object> content) {
        return requestPostFile(url, name, content, HttpWrapper.createCustomRequestConfig(0));
    }

    public static String requestPostFile(String url, String name, Map<String, Object> content, CustomRequestConfig customRequestConfig) {
        HttpPost httpPost = HttpWrapper.createPostFile(url, name, content);
        return doRequest(httpPost, customRequestConfig);
    }

    /**
     * file download
     *
     * @param url          Network file protocol address
     * @param outputStream Write out
     */
    public static void requestDownloadFile(String url, OutputStream outputStream) {
        requestDownloadFile(url, outputStream, HttpWrapper.createCustomRequestConfig(0));
    }

    public static void requestDownloadFile(String url, OutputStream outputStream, CustomRequestConfig customRequestConfig) {
        requestDownloadFile(Collections.singletonList(url), outputStream, HttpWrapper.createCustomRequestConfig(0));
    }

    /**
     * Incorporating multiple files into one file.
     * <p>
     * Does not guarantee that merge order, if you want to merge in
     * sequence the use which has the function of sorting collection
     * storage download link.
     *
     * @param urlList      Network file protocol address List
     * @param outputStream Write out
     */
    public static void requestDownloadFile(Collection<String> urlList, OutputStream outputStream) {
        requestDownloadFile(urlList, outputStream, HttpWrapper.createCustomRequestConfig(0));
    }

    public static void requestDownloadFile(Collection<String> urlList, OutputStream outputStream, CustomRequestConfig customRequestConfig) {
        if (CollectionUtils.isEmpty(urlList)) {
            throw new HttpException("the file url is empty");
        }

        for (String url : urlList) {
            doDownload(url, customRequestConfig, outputStream);
        }
    }

    public static String doRequest(HttpRequestBase request, CustomRequestConfig customRequestConfig) {
        try {
            if (!init.get()) {
                init(CONNECTION_REQUEST_TIMEOUT, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
            }
            // Merge Custom Request Config
            HttpWrapper.mergeCustomRequestConfig(request, customRequestConfig, defaultRequestConfig);
            return httpClient.execute(request, new PlainTextResponseHandler());
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
            request.releaseConnection();
        }
    }

    public static void doDownload(String url, CustomRequestConfig customRequestConfig, OutputStream outputStream) {

        HttpGet httpGet = new HttpGet(url);
        try {
            if (!init.get()) {
                init(CONNECTION_REQUEST_TIMEOUT, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
            }
            // Merge Custom Request Config
            HttpWrapper.mergeCustomRequestConfig(httpGet, customRequestConfig, defaultRequestConfig);
            httpClient.execute(httpGet, new DownloadResponseHandler(outputStream));
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
            httpGet.releaseConnection();
        }
    }

    public static void createDownloadPath(String downloadPath) {
        try {
            File file = new File(downloadPath);
            if (file.exists()) {
                return;
            }
            if (file.isDirectory()) {
                FileUtils.forceMkdir(file);
            } else {
                FileUtils.forceMkdir(file.getParentFile());
            }
        } catch (Exception e) {
            throw new HttpException(String.format("cannot create dir %s", downloadPath), e);
        }
    }
}
