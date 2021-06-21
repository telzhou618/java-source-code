package com.dubbo.framework.server.http;

import com.alibaba.fastjson.JSON;
import com.dubbo.framework.Invocation;
import okhttp3.*;

import java.io.IOException;

/**
 * @author zhou1
 * @since 2021/6/11
 */
public class HttpClient {

    private final static MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public  <T> T send(String url, Invocation invocation, Class<T> getReturnType) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(invocation));
            Request request = new Request.Builder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            return JSON.parseObject(responseBody.bytes(), getReturnType);
        } catch (IOException e) {
            throw new RuntimeException("服务调用异常", e);
        }
    }
}
