package com.gson.chao.t_gson.net;

import android.util.Log;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

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