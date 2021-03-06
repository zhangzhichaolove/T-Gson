package com.gson.chao.t_gson.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava线程调度器
 */
public class RxUtils {

    public static <T> Observable.Transformer<T, T> defaultSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribeOn(Schedulers.io());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> all_io() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(Schedulers.io());
            }
        };
    }
}
