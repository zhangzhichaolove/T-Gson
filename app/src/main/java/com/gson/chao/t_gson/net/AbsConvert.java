package com.gson.chao.t_gson.net;

/**
 * 抽象转换父类。
 * @param <T>
 */
public abstract class AbsConvert<T> {

    abstract T parseData(String result);
}
