package com.gson.chao.t_gson.net;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:15:57
 */
public class HttpResult<T> {

    public boolean error;
    //@SerializedName(value = "results", alternate = {"result"})
    public T results;
}
