package com.cunpiao.bean;

/**
 * 支付宝支付下预订单响应
 *
 * @author 6213
 * @date 2018/4/2
 */
public class AliTradeResponse extends BaseBean {

    private String resultBody;

    public String getResultBody() {
        return resultBody;
    }

    public void setResultBody(String resultBody) {
        this.resultBody = resultBody;
    }

}
