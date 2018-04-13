package com.cunpiao.bll;

import android.app.Activity;

import com.cunpiao.bean.OrderDto;
import com.cunpiao.bean.PayResponse;
import com.cunpiao.network.MySubscriber;
import com.cunpiao.network.RxUtil;
import com.cunpiao.network.callback.SuccCallback;


/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-04-02 14:41
 * @DESCRIPTION:
 */

public class PayBll extends BaseBll {
    public void order(Activity activity, OrderDto dto, SuccCallback<PayResponse> succ){
        String merchId = "5624799463";
        String secret = "9f5a7918bfc87ea5e9697297e94b874e";
        getApi().order(dto).compose(RxUtil.tTransformer(activity)).subscribe(new MySubscriber<PayResponse>(){
            @Override
            public void onSuccess(PayResponse resp) {
                succ.call(resp);
            }
        });
    }
}
