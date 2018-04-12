package com.cunpiao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.cunpiao.bean.OrderDto;
import com.cunpiao.bean.WxTradeResponse;
import com.cunpiao.bll.PayBll;
import com.cunpiao.bll.PayUtil;
import com.cunpiao.util.StringUtils;
import com.cunpiao.util.ToastUtil;

public class MainActivity extends AppCompatActivity {
    EditText tradeAmountEt;
    RadioButton zfbRbtn;
    RadioButton wxRbtn;

    PayBll payBll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        payBll = new PayBll();
        tradeAmountEt = (EditText) findViewById(R.id.trade_amount_et);
        zfbRbtn = (RadioButton) findViewById(R.id.zfb_rbtn);
        wxRbtn = (RadioButton) findViewById(R.id.wx_rbtn);
        findViewById(R.id.start_pay_btn).setOnClickListener(view -> {
            startPay();
        });
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
    void startPay(){
        int tradeAmount = getTradeAmount();
        String payWay = getPayWay();
        String subPayWay = "APP";
        if (tradeAmount <= 0) {
            ToastUtil.showToast("请输入支付金额");
            return;
        }

        OrderDto req = new OrderDto();
        req.setMerchId("5624799463");
        req.setPayWay(payWay);
        req.setSubPayWay(subPayWay);
        req.setTotalFee(tradeAmount);
        req.setMchTradeNo(StringUtils.uuid());
        req.setSubject("鲸鱼智投-支付");
        req.setBody("鲸鱼智投优选产品");
        req.setKey("dddd");
        req.setSign("dddd");
        req.setLimitCreditPay(1);
        req.setNotifyUrl("http:www.baidu.com");
        req.setNonceStr(StringUtils.uuid());
        req.setDeviceInfo("WEB");
        if (payWay.equals("ALIPAY")) {
            payBll.aliOrder(this,req,obj->{
                ToastUtil.showToast("下单成功，开始支付");
                PayUtil.aliPay(MainActivity.this,obj);
            });
        } else if (payWay.equals("WECHAT")) {
            payBll.order(this,req,obj -> {
                ToastUtil.showToast("下单成功，开始支付");
                //微信支付
                wxPay(obj);
            });
        }
    }

    private void wxPay(WxTradeResponse response){
        PayUtil.wxPay(response);
    }
}
