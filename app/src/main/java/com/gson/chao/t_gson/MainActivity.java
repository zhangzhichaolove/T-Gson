package com.gson.chao.t_gson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gson.chao.t_gson.net.HttpResultSubscriber;
import com.gson.chao.t_gson.net.ObservableProvider;
import com.gson.chao.t_gson.net.ResultBean;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    String json1 = " {\"code\":\"0\",\"message\":\"success\",\"data\":{\"name\":\"chao\",\"age\":25,\"msg\":\"hello\"}}";
    String json2 = "{\"code\":\"0\",\"message\":\"success\",\"data\":[{\"name\":\"chao\",\"age\":25,\"msg\":\"hello\"},{\"name\":\"chaochao\",\"age\":20,\"msg\":\"java\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //{"code":"0","message":"success","data":{"name":"chao","age":25,"msg":"hello"}}
        Result<Bean> objectResult = fromJsonObject(json1, Bean.class);
        Log.e("TAG", objectResult.data.getName());
        Result<List<Bean>> listResult = fromJsonArray(json2, Bean.class);
        for (int i = 0; i < listResult.data.size(); i++) {
            Log.e("TAG", listResult.data.get(i).getName());
        }
        ObservableProvider.getDefault().<List<ResultBean>>loadResult("http://gank.io/api/data/Android/10/1", ResultBean.class).subscribe(new HttpResultSubscriber<List<ResultBean>>() {
            @Override
            public void onSuccess(List<ResultBean> resultBeen) {
                for (int i = 0; i < resultBeen.size(); i++) {
                    Log.e("TAG", resultBeen.get(i).toString());
                }
            }

            @Override
            public void _onError(Throwable e) {
                Log.e("TAG", "获取失败");
            }
        });
        ObservableProvider.getDefault().loadString("http://gank.io/api/data/Android/10/1").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("TAG", s);
            }
        });
    }


    /**
     * 转换Array
     * java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.gson.chao.t_gson.Bean
     *
     * @param <T>
     * @param reader
     * @return
     */
    public static <T> Result<List<T>> fromJsonArray(String reader) {
        Type type = new TypeToken<Result<List<T>>>() {
        }.getType();
        Log.e("TAG", type.toString());//Result<java.util.List<T>>
        return new Gson().fromJson(reader, type);
    }

    /**
     * 转换Object
     * java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.gson.chao.t_gson.Bean
     *
     * @param <T>
     * @param reader
     * @return
     */
    public static <T> Result<T> fromJsonObject(String reader) {
        Type type = new TypeToken<Result<T>>() {
        }.getType();
        Log.e("TAG", type.toString());//Result<T>
        return new Gson().fromJson(reader, type);
    }

    public static <T> Result<T> fromJsonObject(String reader, Class<Bean> clazz) {
        Type type = new ParameterizedTypeImpl(Result.class, new Class[]{clazz});
        return new Gson().fromJson(reader, type);
    }

    public static <T> Result<List<T>> fromJsonArray(String reader, Class<Bean> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(Result.class, new Type[]{listType});
        return new Gson().fromJson(reader, type);
    }

}
