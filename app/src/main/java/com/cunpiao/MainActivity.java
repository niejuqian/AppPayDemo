package com.cunpiao;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import com.cunpiao.bean.AliTradeResponse;
import com.cunpiao.bean.OrderDto;
import com.cunpiao.bean.PayResponse;
import com.cunpiao.bean.WxTradeResponse;
import com.cunpiao.bll.PayBll;
import com.cunpiao.bll.PayUtil;
import com.cunpiao.util.JsonUtils;
import com.cunpiao.util.Logger;
import com.cunpiao.util.StringUtils;
import com.cunpiao.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseAppCompatActivity {
    @BindView(R.id.trade_amount_et)
    EditText tradeAmountEt;
    @BindView(R.id.zfb_rbtn)
    RadioButton zfbRbtn;
    @BindView(R.id.wx_rbtn)
    RadioButton wxRbtn;

    PayBll payBll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_main);
        payBll = new PayBll();
    }

    @OnClick(R.id.setting_btn)
    void setting(){
        startActivity(SettingActivity.class);
    }


    private String getPayWay(){
        String payWay = null;
        if (zfbRbtn.isChecked()) {
            payWay = "ALIPAY";
        } else if (wxRbtn.isChecked()) {
            payWay = "WECHAT";
        }
        return payWay;
    }

    private int getTradeAmount(){
        String tradeAmountStr = tradeAmountEt.getText().toString();
        return Integer.valueOf(StringUtils.isBlank(tradeAmountStr.trim()) ? "0" : tradeAmountStr.trim());
    }

    @OnClick(R.id.start_pay_btn)
    public void startPay(){
        int tradeAmount = getTradeAmount();
        String payWay = getPayWay();
        String subPayWay = "APP";
        if (tradeAmount <= 0) {
            ToastUtil.showToast("请输入支付金额");
            return;
        }

        OrderDto req = new OrderDto();
        req.setPayWay(payWay);
        req.setSubPayWay(subPayWay);
        req.setTotalFee(tradeAmount);
        req.setMchTradeNo(StringUtils.uuid());
        req.setSubject("鲸鱼智投-支付");
        req.setBody("鲸鱼智投优选产品");
        req.setLimitCreditPay(1);
        req.setNotifyUrl("http:www.baidu.com");
        req.setNonceStr(StringUtils.uuid());
        req.setDeviceInfo("WEB");
        req.setSpbillCreateIp("192.168.1.10");
        try {
            payBll.order(this,req,obj -> {
                ToastUtil.showToast("下单成功，开始支付");
                if (payWay.equals("ALIPAY")) {
                    aliPay(obj);
                } else if (payWay.equals("WECHAT")) {
                    //微信支付
                    wxPay(obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("================>>>>下单失败");
        }
    }

    private void wxPay(PayResponse response){
        WxTradeResponse tradeResponse = JsonUtils.fromJson(response.getOrderStr(),WxTradeResponse.class);
        PayUtil.wxPay(tradeResponse);
    }

    private void aliPay(PayResponse response) {
        AliTradeResponse tradeResponse = JsonUtils.fromJson(response.getOrderStr(),AliTradeResponse.class);
        PayUtil.aliPay(this,tradeResponse.getResultBody());
    }
}
