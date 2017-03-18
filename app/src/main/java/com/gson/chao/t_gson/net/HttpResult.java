package com.gson.chao.t_gson.net;

public class HttpResult<T> {

    public boolean error;
    //@SerializedName(value = "results", alternate = {"result"})
    public T results;
}
