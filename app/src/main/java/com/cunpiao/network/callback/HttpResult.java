package com.cunpiao.network.callback;


import com.cunpiao.network.RespCodeEnum;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *              网络请求想要body
 */

public class HttpResult<T> {
    private String code;//返回码
    private String msg;//返回信息
    private T data;//返回数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return RespCodeEnum.SUCCESS.getCode().equals(getCode());
    }
}
