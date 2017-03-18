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

/**
 * Created by _SOLID
 * Date:2016/7/22
 * Time:17:50
 * HttpResult<List<ResultBean>>
 */
public class JsonConvert<T> extends AbsConvert<T> {

    private String mDataName = null;
    Class beanClass;

    public JsonConvert(Class resultBeanClass) {
        this.beanClass = beanClass;
    }


    @Override
    public T parseData(String result) {
        T t = null;
        try {
            t = (T) fromJsonObject(result, beanClass);
            T tHttpResult = (T) parseJson(result);
            Type trueType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Type type = new TypeToken<T>() {
            }.getType();
            Gson gson = new Gson();
            if (!TextUtils.isEmpty(mDataName)) {
                JSONObject jsonObject = new JSONObject(result);
                t = gson.fromJson(jsonObject.getString(mDataName), trueType);
            } else {
                t = gson.fromJson(result, trueType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {//解析异常，说明是array数组
            t = (T) fromJsonArray(result, beanClass);
        }
        return t;
    }

    /**
     * 数据解析
     *
     * @param jsonStr JSON字符串
     * @return UniApiResult<GoodsInfoModel> 数据对象
     */
    public T parseJson(String jsonStr) {
        Gson gson = new Gson();
        Type jsonType = new TypeToken<HttpResult<T>>() {
        }.getType();
        return gson.fromJson(jsonStr, jsonType);
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

    /**
     * 在数据中要获取的name（当数据类型为集合时）
     *
     * @return data_name
     */
    public void setDataName(String dataName) {
        mDataName = dataName;
    }
}
