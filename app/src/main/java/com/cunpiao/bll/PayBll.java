package com.cunpiao.bll;

import android.app.Activity;

import com.cunpiao.bean.AliTradeResponse;
import com.cunpiao.bean.OrderDto;
import com.cunpiao.bean.WxTradeResponse;
import com.cunpiao.network.MySubscriber;
import com.cunpiao.network.RxUtil;
import com.cunpiao.network.callback.SuccCallback;


/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-04-02 14:41
 * @DESCRIPTION:
 */

public class PayBll extends BaseBll {
    public void order(Activity activity, OrderDto dto, SuccCallback<WxTradeResponse> succ){
        getApi().order(dto).compose(RxUtil.tTransformer(activity)).subscribe(new MySubscriber<WxTradeResponse>(){
            @Override
            public void onSuccess(WxTradeResponse resp) {
                succ.call(resp);
            }
        });
    }


    public void aliOrder(Activity activity, OrderDto dto, SuccCallback<AliTradeResponse> succ) {
        getApi().aliOrder(dto).compose(RxUtil.tTransformer(activity)).subscribe(new MySubscriber<AliTradeResponse>() {
            @Override
            public void onSuccess(AliTradeResponse resp) {
                succ.call(resp);
            }
        });
    }
}
