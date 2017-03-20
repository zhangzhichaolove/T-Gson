package com.gson.chao.t_gson.net;

/**
 * 书写一个接口，用来定义所有DTO需要实现的方法，完成实体转换
 * <p>
 * Created by CJ on 2016/12/14 0014.
 */
public interface Mapper<T> {
    T transform();
}