package com.cunpiao.bll;

import android.app.Activity;

import com.cunpiao.bean.OrderDto;
import com.cunpiao.bean.PayResponse;
import com.cunpiao.network.MySubscriber;
import com.cunpiao.network.RxUtil;
import com.cunpiao.network.callback.SuccCallback;
import com.cunpiao.util.Logger;
import com.cunpiao.util.SignatureUtil;


/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-04-02 14:41
 * @DESCRIPTION:
 */

public class PayBll extends BaseBll {
    public void order(Activity activity, OrderDto dto, SuccCallback<PayResponse> succ) throws Exception {
        String merchId = "8769664623";
        String secret = "a809e261853bb117e862bde4bea81028";
        dto.setMerchId(merchId);
        String sign = SignatureUtil.generateSignature(dto,secret);
        dto.setSign(sign);
        Logger.e(TAG,"==============签名sign：" + sign);
        Logger.e(TAG,"==============请求参数：" + dto);
        getApi().order(dto).compose(RxUtil.tTransformer(activity)).subscribe(new MySubscriber<PayResponse>(){
            @Override
            public void onSuccess(PayResponse resp) {
                succ.call(resp);
            }
        });
    }
}
