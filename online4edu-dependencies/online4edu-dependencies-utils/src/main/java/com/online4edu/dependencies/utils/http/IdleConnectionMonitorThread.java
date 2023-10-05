package com.online4edu.dependencies.utils.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * 监控线程池 {@link HttpClientConnectionManager} 过期及空闲的连接
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @since 2021/12/24 20:44
 */
class IdleConnectionMonitorThread extends Thread {

    private volatile boolean shutdown;
    private final HttpClientConnectionManager connMgr;

    private final Log log = LogFactory.getLog(getClass());

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(3000);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    connMgr.closeIdleConnections(30L, TimeUnit.SECONDS);
                }
            }
            log.info("IdleConnectionMonitorThread Shutdown!!!");
            connMgr.closeIdleConnections(0L, TimeUnit.MILLISECONDS);
            connMgr.shutdown();
        } catch (InterruptedException ex) {
            // terminal
        }
    }

    public void shutdown() {
        log.info("IdleConnectionMonitorThread: Send a shutdown signal...");
        shutdown = true;

        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            // ignore...
        }

        synchronized (this) {
            notifyAll();
        }
    }

}
