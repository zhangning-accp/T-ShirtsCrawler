package com.sjw.tool;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/25.
 */
public class OkhttpUtils {
    private static final OkHttpClient MOKHTTPCLIENT = new OkHttpClient();
    private static Integer WriteTime = 50;
    private static Integer ReadTime = 200;
    private static Integer ConnectTime = 50;

    static {
        MOKHTTPCLIENT.setConnectTimeout(WriteTime, TimeUnit.SECONDS);
        MOKHTTPCLIENT.setReadTimeout(ReadTime, TimeUnit.SECONDS);
        MOKHTTPCLIENT.setWriteTimeout(ConnectTime, TimeUnit.SECONDS);
    }


    public static Response execute(Request request, Proxy proxy) throws IOException {
        return MOKHTTPCLIENT.setProxy(proxy).newCall(request).execute();
    }

    public static Response execute(Request request) throws IOException {
        return MOKHTTPCLIENT.newCall(request).execute();
    }


    public static void enqueue(Request request, Callback responseCallback) {
        MOKHTTPCLIENT.newCall(request).enqueue(responseCallback);
    }

    static int serversLoadTimes;

    public static void enqueue(Request request) {
        serversLoadTimes = 0;
        MOKHTTPCLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                if (e.getCause().equals(SocketTimeoutException.class) && serversLoadTimes < 3) {
                    serversLoadTimes++;
                    MOKHTTPCLIENT.newCall(request).enqueue(this);
                } else {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println("This reponse is Successful");
                } else {
                    System.out.println("This is okhttpUtils,and it has a Exeception");
                }
            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private static final String CHARSET_NAME = "UTF-8";


    public static String formatParams(List<BasicNameValuePair> params) {
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }

    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
        return url + "?" + formatParams(params);
    }

    public static String attachHttpGetParam(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }
}
