package com.cunpiao.bll;


import com.cunpiao.network.RetrofitControl;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:46
 * @DESCRIPTION:
 */

public class BaseBll {
    protected static String TAG;
    public BaseBll(){
        TAG = this.getClass().getSimpleName();
    }
    /*public <T> Observable<T> observable(Observable<HttpResult<T>> observable) {
        return observable
                .flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final HttpResult<T> httpResult) {
                        if (!httpResult.isSuccess()) {
                            return Observable.error(new BllException(httpResult.getRetCode(),httpResult.getRetMsg()));
                        } else {
                            return Observable.just(httpResult.getData());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public <T> Observable.Transformer<HttpResult<T>,T> tTransformer(){
        return httpResultObservable -> observable(httpResultObservable);
    }

    public <T> Observable<T> observable1(Observable<HttpResult<T>> observable) {
        return observable
                .flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final HttpResult<T> httpResult) {
                        if (!httpResult.isSuccess()) {
                            return Observable.error(new BllException(httpResult.getRetCode(),httpResult.getRetMsg()));
                        } else {
                            return Observable.just(httpResult.getData());
                        }
                    }
                });
    }
    public <T> Observable.Transformer<HttpResult<T>,T> tTransformer1(){
        return httpResultObservable -> observable1(httpResultObservable);
    }

    public <T> Observable valid(HttpResult<T> result,Observable<T> next) {
        if (null == result) {
            return Observable.error(new BllException(RespCodeEnum.SYS_ERROR));
        }
        if (!result.isSuccess() || null == result.getData()) {
            return Observable.error(new BllException(result.getRetCode(),result.getRetMsg()));
        }else {
            return next;
        }
    }

    public <T> Observable errorObservable(HttpResult<T> result) {
        if (null == result) {
            return Observable.error(new BllException(RespCodeEnum.SYS_ERROR));
        } else {
            return Observable.error(new BllException(result.getRetCode(),result.getRetMsg()));
        }
    }*/

    protected ServiceApi getApi(){
        return RetrofitControl.getSingleton().getServiceApi(ServiceApi.class);
    }
}
