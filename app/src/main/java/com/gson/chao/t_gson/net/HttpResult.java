package com.gson.chao.t_gson.net;

/**
 * 服务器返回数据顶层JavaBean类
 * @param <T>
 */
public class HttpResult<T> {

    public boolean error;
    //@SerializedName(value = "results", alternate = {"result"})
    public T results;
}
