package com.ymkj.im.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {
    final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String postForm(String url, String jsonObj) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody body = RequestBody.create(JSON,jsonObj);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public static String get( String url, Map<String, String> params) throws IOException {
        url = url + buildRequestUrl(params);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public static String buildRequestUrl(Map<String, String> params) {
        if(params==null){
            return "";
        }
        StringBuilder url = new StringBuilder("?");
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            url.append(key).append("=").append(params.get(key)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }
}
