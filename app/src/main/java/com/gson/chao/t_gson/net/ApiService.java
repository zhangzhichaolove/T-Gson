package com.gson.chao.t_gson.net;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 服务器接口定义类
 */
public interface ApiService {

    String BASE_URL = "http://www.gank.io/api/";//这个不重要，可以随便写，但是必须有

    @GET("data/Android/10/{page}")
    Observable<HttpResult<List<ResultBean>>> getAndroidData(@Path("page") int page);

    @GET
    Observable<ResponseBody> loadString(@Url String url);

    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);
}
