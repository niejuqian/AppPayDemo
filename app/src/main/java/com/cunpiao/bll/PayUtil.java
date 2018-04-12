package com.cunpiao.bll;

import android.app.Activity;
import android.content.Context;

import com.alipay.sdk.app.PayTask;
import com.cunpiao.bean.AliTradeResponse;
import com.cunpiao.bean.WxTradeResponse;
import com.cunpiao.util.Constants;
import com.cunpiao.util.JsonUtils;
import com.cunpiao.util.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-04-02 16:18
 * @DESCRIPTION:
 */

public class PayUtil {
    private static IWXAPI mWxApi;
    public static void init(Context context){
        mWxApi = WXAPIFactory.createWXAPI(context, null);
        mWxApi.registerApp(Constants.APP_ID);
    }

    public static IWXAPI getmWxApi(){
        return mWxApi;
    }

    public static void wxPay(WxTradeResponse response){
        Logger.i("======>>>请求参数：" + JsonUtils.toJsonStr(response));
        PayReq req = new PayReq();
        req.appId = response.getAppId();
        req.partnerId = response.getPartnerid();
        req.prepayId = response.getPrepayid();
        req.nonceStr = response.getNoncestr();
        req.timeStamp = response.getTimestamp();
        req.packageValue = response.getPackageStr();
        req.sign = response.getSign();
        mWxApi.sendReq(req);
    }

    public static void aliPay(Activity activity, AliTradeResponse response){
        String orderInfo = response.getResultBody();
        Logger.i("======>>>请求参数：" + orderInfo);
        Runnable runnable = () -> {
            try {
                PayTask task = new PayTask(activity);
                Map<String,String> result = task.payV2(orderInfo,true);
                Logger.e("===========>>>支付宝返回：" + JsonUtils.toJsonStr(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
