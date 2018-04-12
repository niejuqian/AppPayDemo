package com.cunpiao.network;

import com.cunpiao.network.callback.CommonParamsInterceptor;
import com.cunpiao.network.param.BaseCommonParam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *              retrofit 控制器
 */

public class RetrofitControl {
    //添加okhttp 网络请求，公共参数拦截器
    private OkHttpClient okHttpClient;
    private CommonParamsInterceptor commonParamsInterceptor;
    private Gson gson;
    private static final int TIME_OUT_TIME = 10;//超时时间
    private Retrofit retrofit;
    private Builder builder;
    private static Map<String, Object> map = new HashMap<>();

    private RetrofitControl(){
        commonParamsInterceptor = new CommonParamsInterceptor();
        //日志输出拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//添加日志打印拦截器
                .addInterceptor(commonParamsInterceptor)//公共参数添加拦截器
                .connectTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_TIME,TimeUnit.SECONDS);
        okHttpClient = httpBuilder.build();
        gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
    public static RetrofitControl getSingleton(){
        return SingletonHolder.singleton;
    }
    private static class SingletonHolder{
        private final static RetrofitControl singleton = new RetrofitControl();
    }
    public void initRegrofit(Builder builder){
        if (null == builder) {
            throw new NullPointerException("必要参数为空，请传入builder信息");
        }
        this.builder = builder;
        retrofit = new Retrofit.Builder()
                .baseUrl(builder.getHostAddress())//
                .client(okHttpClient)//底层使用的http请求框架
                .addConverterFactory(GsonConverterFactory.create(gson))//数据转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加rx支持
                .build();
        map.put(builder.getApiClass().getName(), retrofit.create(builder.getApiClass()));
        updateCommParams(builder.getCommonParam());
    }

    public <w> w getServiceApi(Class<w> wClass) {
        if(map.size() == 0) {
            RetrofitControl.getSingleton().initRegrofit(RetrofitControl.getSingleton().getBuilder());
        }
        if (map.containsKey(wClass.getName())) {
            return (w) map.get(wClass.getName());
        } else {
            throw new IllegalArgumentException("Api为空，可能没有进行初始化！");
        }
    }

    /**
     * 更新公共参数
     * @param params
     */
    public void updateCommParams(BaseCommonParam params){
        this.commonParamsInterceptor.updateParams(params);
    }

    public Builder getBuilder(){
        return this.builder;
    }

    public static class Builder {
        //请求接口地址，最后字符必须是/
        private String hostAddress;
        //接口定义
        private Class apiClass;
        //公共参数[heander,query]
        private BaseCommonParam commonParam;

        public String getHostAddress() {
            return hostAddress;
        }

        public Builder setHostAddress(String hostAddress) {
            this.hostAddress = hostAddress;
            return this;
        }

        public Class getApiClass() {
            return apiClass;
        }

        public Builder setApiClass(Class apiClass) {
            this.apiClass = apiClass;
            return this;
        }

        public BaseCommonParam getCommonParam() {
            return commonParam;
        }

        public Builder setCommonParam(BaseCommonParam commonParam) {
            this.commonParam = commonParam;
            return this;
        }
    }
}
