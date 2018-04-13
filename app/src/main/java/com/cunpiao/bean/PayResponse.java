package com.cunpiao.bean;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-04-13 17:39
 * @DESCRIPTION:
 */

public class PayResponse extends BaseBean{
    private String merchId;
    private String nonceStr;
    private String sign;
    private String orderStr;

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
