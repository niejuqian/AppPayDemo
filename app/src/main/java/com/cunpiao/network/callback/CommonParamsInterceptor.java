package com.cunpiao.network.callback;

import android.util.Log;

import com.cunpiao.util.JsonUtils;
import com.cunpiao.util.Logger;
import com.cunpiao.util.StringUtils;
import com.cunpiao.network.annotation.FormBodyParam;
import com.cunpiao.network.annotation.HeaderParam;
import com.cunpiao.network.annotation.QueryParam;
import com.cunpiao.network.param.BaseCommonParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *              Okhttp 拦截器
 *              可以将一些公共的参数放在请求头中
 */

public class CommonParamsInterceptor implements Interceptor {
    private static String TAG = CommonParamsInterceptor.class.getSimpleName();
    private BaseCommonParam commonParam;
    @Override
    public Response intercept(Chain chain) throws IOException {
        long start = System.currentTimeMillis();
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        try {
            //封装公共请求参数到header中
            buildHeaderParams(requestBuilder);
            //封装公共请求参数到url后面
            /*HttpUrl.Builder httpBuilder = request.url().newBuilder();
            String method = request.method();
            if ("POST".equalsIgnoreCase(method)) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                //将原有请求参数读取出来
                HashMap<String, String> rootMap = buildRootParam(request);
                //添加公共参数
                buildFormParams(formBodyBuilder,rootMap);
                //重新构建request.builder
                String postBodyString = bodyToString(formBodyBuilder.build());
                requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
            } else {
                //GET
                buildQueryParams(httpBuilder);
                requestBuilder.url(httpBuilder.build());
            }*/
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG,"=============================封装公共请求异常："+ e.getMessage());
        }
        request = requestBuilder.build();
        Logger.e("OkHttp===", "request:" + request.toString());
        Logger.e("OkHttp===", "requestBody:" + bodyToString(request.body()));
        Logger.e("OkHttp===", "header:" + headerToString(request));
        Logger.e(TAG,"======================公共参数封装耗时：" + (System.currentTimeMillis() - start));
        return chain.proceed(request);
    }

    private HashMap<String, String> buildRootParam(Request request) throws IOException {
        RequestBody requestBody = request.body();
        HashMap<String, String> rootMap = new HashMap<>();
        if (requestBody instanceof FormBody) {
            FormBody formBody = (FormBody) requestBody;
            for (int i = 0; i < formBody.size(); i++) {
                rootMap.put(formBody.encodedName(i), formBody.encodedValue(i));
            }
        } else {
            //buffer流
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            String oldParamsJson = buffer.readUtf8();
            rootMap = JsonUtils.fromJson(oldParamsJson,HashMap.class);
        }
        return rootMap;
    }


    private void buildFormParams(FormBody.Builder builder,HashMap<String,String> rootMap) throws IllegalAccessException {
        if (null != commonParam ) {
            Field[] fields = commonParam.getClass().getDeclaredFields();
            if (null != fields && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    Object fieldValue = field.get(commonParam);
                    FormBodyParam param = field.getAnnotation(FormBodyParam.class);
                    if (null != fieldValue && param != null) {
                        builder.add(fieldName,fieldValue.toString());
                    }
                }
            }
        }
        if (null != rootMap && rootMap.size() > 0) {
            for (String key : rootMap.keySet()) {
                String value = rootMap.get(key);
                if (StringUtils.isEmpty(value)) continue;
                try {
                    builder.add(key,URLDecoder.decode(value,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    builder.add(key,value);
                }
            }
        }
    }

    /**
     * 封装公共请求query参数
     * @param builder
     * @throws IllegalAccessException
     */
    private void buildQueryParams(HttpUrl.Builder builder) throws IllegalAccessException {
        if (null != commonParam) {
            Field[] fields = commonParam.getClass().getDeclaredFields();
            if (null != fields && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    Object fieldValue = field.get(commonParam);
                    QueryParam param = field.getAnnotation(QueryParam.class);
                    if (null != fieldValue && param != null) {
                        builder.addQueryParameter(fieldName, fieldValue.toString());
                    }
                }
            }
        }
    }

    /**
     * 封装公共请求header参数
     * @param builder
     * @throws IllegalAccessException
     */
    private void buildHeaderParams(Request.Builder builder) throws IllegalAccessException {
        if (null != commonParam) {
            Field[] fields = commonParam.getClass().getDeclaredFields();
            if (null != fields && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    Object fieldValue = field.get(commonParam);
                    HeaderParam param = field.getAnnotation(HeaderParam.class);
                    if (null != fieldValue && !"".equalsIgnoreCase(fieldValue.toString()) && param != null) {
                        builder.addHeader(fieldName, fieldValue.toString());
                    }
                }
            }
        }
    }

    /**
     * 更新公共参数
     * @param commonParam 公共参数
     */
    public void updateParams(BaseCommonParam commonParam) {
        this.commonParam = commonParam;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "";
        }
    }

    private static String headerToString(Request request){
        Headers requestHeaders = request.headers();
        StringBuffer sb = new StringBuffer();
        int requestHeadersLength = requestHeaders.size();
        if(requestHeadersLength == 0) return "";
        for (int i = 0; i < requestHeadersLength; i++){
            String headerName = requestHeaders.name(i);
            String headerValue = requestHeaders.get(headerName);
            sb.append(headerName + "=" + headerValue + "&");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
