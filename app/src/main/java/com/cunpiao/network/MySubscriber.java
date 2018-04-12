package com.cunpiao.network;


import com.cunpiao.network.callback.Callback;
import com.cunpiao.network.callback.EndCallback;
import com.cunpiao.network.callback.ErrCallback;
import com.cunpiao.network.callback.StartCallback;
import com.cunpiao.network.callback.SuccCallback;
import com.cunpiao.network.exception.BllException;
import com.cunpiao.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:58
 * @DESCRIPTION:
 */

public class MySubscriber<T> extends Subscriber<T> {
    private SuccCallback<T> succCallback;//成功回调
    private ErrCallback<String,String> errCallback;//失败回调
    private StartCallback startCallback;//开始调用前回调
    private EndCallback endCallback;//完成回调，不管成功、失败都会回调
    private Callback<T> callback;
    public MySubscriber(){
    }
    public MySubscriber(Callback<T> callback){
        this.callback = callback;
    }
    public MySubscriber(SuccCallback<T> succCallback){
        this.succCallback = succCallback;
    }
    public MySubscriber(EndCallback endCallback){
        this.endCallback = endCallback;
    }
    public MySubscriber(SuccCallback<T> succCallback, ErrCallback<String,String> errCallback){
        this.succCallback = succCallback;
        this.errCallback = errCallback;
    }
    @Override
    public void onStart() {
        if (null != startCallback) startCallback.call();
    }
    @Override
    public void onCompleted() {
        if (null != endCallback) endCallback.call();
    }
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
    /**
     * 统一异常处理分发
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof BllException) {
            BllException exception = (BllException) e;
            //特定的code，需要重新登录
            onError(exception.getCode(),exception.getMsg());
        }else if (e instanceof SocketTimeoutException) {
            onError(RespCodeEnum.NETWORK_ERROR.getCode(),RespCodeEnum.NETWORK_ERROR.getMessage());
        } else if (e instanceof ConnectException) {
            onError(RespCodeEnum.CONNECT_ERROR.getCode(),RespCodeEnum.CONNECT_ERROR.getMessage());
        }else {
            onError(RespCodeEnum.SYS_ERROR.getCode(),RespCodeEnum.SYS_ERROR.getMessage());
        }
    }
    public void onSuccess(T t){
        if (null != succCallback) {
            succCallback.call(t);
        }
        if (null != callback) {
            callback.call(t);
        }
    }

    public void onError(String code,String msg){
        if (RespCodeEnum.isToast(code)) {
            ToastUtil.showToast(msg);
        }
        if (RespCodeEnum.reLogin(code)) {
            // 跳转到登录页面，进行重新登录操作
        }
        if (null != errCallback) {
            errCallback.call(code,msg);
        }
    }


    public void setSuccCallback(SuccCallback<T> succCallback) {
        this.succCallback = succCallback;
    }

    public void setErrCallback(ErrCallback<String,String> errCallback) {
        this.errCallback = errCallback;
    }

    public void setStartCallback(StartCallback startCallback) {
        this.startCallback = startCallback;
    }

    public void setEndCallback(EndCallback endCallback) {
        this.endCallback = endCallback;
    }
}
