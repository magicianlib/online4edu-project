package com.online4edu.dependencies.utils.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Different from {@link DownloadResponseHandler}, this class is used to the
 * corresponding message flow into a text string. Such as the clear know
 * response message for a JSON string or HTML, XML data.
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @since 2021/12/24 20:42
 */
class PlainTextResponseHandler implements ResponseHandler<String> {

    private final Log log = LogFactory.getLog(getClass());

    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } else {
            log.error("HTTP请求失败, 状态码: " + statusCode);
            throw new HttpException("HTTP请求失败, 状态码: " + statusCode);
        }
    }
}
