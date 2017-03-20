package com.gson.chao.t_gson.net;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by CJ on 2016/10/27 0027.
 */
public class ApiExecutor {

    private ApiService apiService;

    /**
     * 私有构造方法
     */
    private ApiExecutor() {
    }


    /**
     * 使用静态类创建单例
     */
    private static class SingletonHolder {
        private static final ApiExecutor INSTANCE = new ApiExecutor();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ApiExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取ApiService
     *
     * @return
     */
    public ApiService getApiService() {
        if (apiService == null) {
            apiService = ServiceFactory.getInstance()
                    .createService(ApiService.class);
        }
        return apiService;
    }

    /**
     * 使用RxJava异步执行网络请求
     *
     * @param observable
     * @param subscriber
     */
    public <T extends Mapper<R>, R> void toSubscribe(Observable<T> observable, Subscriber<R> subscriber) {
        observable.compose(RxUtils.<T>defaultSchedulers())//调用默认调度器
                .retryWhen(new RetryWhenNetworkException())//重试
                .map(new Func1<T, R>() {
                    @Override
                    public R call(T t) {
                        return t.transform();
                    }
                })
                .subscribe(subscriber);
    }
}