package io.ituknown.utils.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * File download response handler
 * <p>
 * This class is used only for file download, Because the class will be in the
 * internal processing IO flow, not the data output to the outside. So if you
 * want the I/O output to the specified location can use {@link OutputStream}
 *
 * @author magicianlib@gmail.com
 * @since 2021/12/24 20:42
 */
class DownloadResponseHandler implements ResponseHandler<Void> {

    private final OutputStream out;

    public DownloadResponseHandler(OutputStream out) {
        this.out = out;
    }

    @Override
    public Void handleResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
            response.getEntity().writeTo(out);
            return null;
        } else {
            throw new HttpException("HTTP请求失败, 状态码：" + statusCode);
        }
    }
}