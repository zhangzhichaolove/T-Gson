package com.gson.chao.t_gson.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.gson.chao.t_gson.Bean;
import com.gson.chao.t_gson.ParameterizedTypeImpl;
import com.gson.chao.t_gson.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:11:04
 */
public class ResultFunc<T> implements Func1<String, HttpResult<T>> {
    Class beanClass;

    public ResultFunc(Class beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public HttpResult<T> call(String result) {
        //JsonConvert<HttpResult<T>> convert = new JsonConvert<HttpResult<T>>(beanClass) {};
        HttpResult<T> t = null;
        try {
            t = (HttpResult<T>) fromJsonObject(result, beanClass);
        } catch (JsonSyntaxException e) {//解析异常，说明是array数组
            t = (HttpResult<T>) fromJsonArray(result, beanClass);
        }
        return t;
    }

    public static <T> HttpResult<T> fromJsonObject(String reader, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(HttpResult.class, new Class[]{clazz});
        return new Gson().fromJson(reader, type);
    }

    public static <T> HttpResult<List<T>> fromJsonArray(String reader, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(HttpResult.class, new Type[]{listType});
        return new Gson().fromJson(reader, type);
    }


}