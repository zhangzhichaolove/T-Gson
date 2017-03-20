package com.gson.chao.t_gson.net;

/**
 * Created by Chao on 2017/3/20.
 */

public class ResultOTO implements Mapper<ResultBean> {
    @Override
    public ResultBean transform() {
        return new ResultBean();
    }
}
