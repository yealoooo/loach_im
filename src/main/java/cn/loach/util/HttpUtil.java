package cn.loach.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class HttpUtil {
    public static void post(String url, String bodyData) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(bodyData, mediaType))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();

        final Call call = okHttpClient.newCall(request);
        new Thread(() -> {
            try {
                Response response = call.execute();
                log.info("run: " + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
