package com.gson.chao.t_gson.net;

import android.util.Log;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.exceptions.OnErrorNotImplementedException;

/**
 * 错误过滤，数据返回-观察者
 *
 * @param <T>
 */
public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.e("TAG", e.getMessage());
        e.printStackTrace();
        //在这里做全局的错误处理
        if (e instanceof HttpException) {
            // ToastUtils.getInstance().showToast(e.getMessage());
            Log.e("HttpResultSubscriber", "网络异常");
        }
        if (e instanceof OnErrorNotImplementedException) {
            Log.e("HttpResultSubscriber", "错误没有实现异常");
        }
        if (e instanceof ClassCastException) {
            Log.e("HttpResultSubscriber", "类转换异常");
        }
        _onError(e);
    }

    @Override
    public void onNext(HttpResult<T> t) {
        if (!t.error)
            onSuccess(t.results);
        else
            _onError(new Throwable("error=" + t.error));
    }

    public abstract void onSuccess(T t);

    public abstract void _onError(Throwable e);
}