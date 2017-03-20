package com.gson.chao.t_gson.net;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * RxJava-map转换类
 */
public class StringFunc implements Func1<ResponseBody, String> {
    @Override
    public String call(ResponseBody responseBody) {
        String result = null;
        try {
            result = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
