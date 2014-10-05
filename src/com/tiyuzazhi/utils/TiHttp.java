package com.tiyuzazhi.utils;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author chris.xue
 *         http client
 */
public class TiHttp {
    public static final String HOST = "http://192.168.10.104:8000/tiyuzazhi/api";

    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public static TiHttp getInstance() {
        return MkHttpGen.instance;
    }

    private static class MkHttpGen {
        private static TiHttp instance;

        static {
            instance = new TiHttp();
        }
    }

    private HttpClient client;

    private TiHttp() {
        HttpParams params = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(params, 10);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        // Create and initialize scheme registry
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        // Create an HttpClient with the ThreadSafeClientConnManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        client = new DefaultHttpClient(cm, params);
        client.getParams().setParameter("http.socket.timeout", 20000);
        client.getParams().setParameter("http.connection.timeout", 20000);
        client.getParams().setParameter("http.connection-manager.timeout", 4000L);
        client.getParams().setParameter("http.protocol.head-body-timeout", 4000L);

    }

    /**
     * 发送HTTP请求，无回调
     *
     * @param request
     * @return
     */
    public Future<HttpResponse> send(final HttpUriRequest request) {
        return send(request, null);
    }

    /**
     * 发送HTTP请求
     *
     * @param request
     * @param callback
     */
    public Future<HttpResponse> send(final HttpUriRequest request, final HttpCallback callback) {

        return service.submit(new Callable<HttpResponse>() {
            @Override
            public HttpResponse call() {
                try {
                    HttpResponse response = client.execute(request);
                    if (callback != null) {
                        callback.onComplete(response, request);
                    }
                    return response;
                } catch (IOException e) {
                    if (callback != null) {
                        callback.onError(e);
                    } else {
                        Log.d("DEBUG", "HTTP ERR", e);
                    }
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
