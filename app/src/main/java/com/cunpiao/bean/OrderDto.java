package com.cunpiao.bean;


/**
 * 支付流水
 *
 * @author 6213
 * @date 2018/3/28
 */
public class OrderDto extends BaseBean {
    private String merchId;                 //商户号
    private String payWay;                  //支付方式
    private String subPayWay;               //子支付方式
    private int totalFee;                   //总金额（单位分）
    private String mchTradeNo;              // 商户订单号
    private String subject;// 商品描述(如：腾讯充值中心-QQ会员充值)
    private String body; // 商品描述(如：腾讯充值中心-QQ会员充值)
    private String detail; // 商品详情
    private String attach; // 附加数据
    private String sign;
    private String spbillCreateIp;          // 终端IP
    private Integer limitCreditPay;          //限定用户使用时能否使用信用卡，值为1，禁用信用卡，值为0不限制
    private String notifyUrl;                   //此回调地址，为客户端上传的回调地址，平台收到第三方回调后，我方再调用客户的回调接口
    private String nonceStr;
    private String deviceInfo;

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getSubPayWay() {
        return subPayWay;
    }

    public void setSubPayWay(String subPayWay) {
        this.subPayWay = subPayWay;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getMchTradeNo() {
        return mchTradeNo;
    }

    public void setMchTradeNo(String mchTradeNo) {
        this.mchTradeNo = mchTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public Integer getLimitCreditPay() {
        return limitCreditPay;
    }

    public void setLimitCreditPay(Integer limitCreditPay) {
        this.limitCreditPay = limitCreditPay;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public boolean isLimitCreditPay() {
        return getLimitCreditPay() == 1;
    }


    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }


    @Override
    public String toString() {
        return "OrderDto{" +
                "merchId='" + merchId + '\'' +
                ", payWay='" + payWay + '\'' +
                ", subPayWay='" + subPayWay + '\'' +
                ", totalFee=" + totalFee +
                ", mchTradeNo='" + mchTradeNo + '\'' +
                ", body='" + body + '\'' +
                ", detail='" + detail + '\'' +
                ", attach='" + attach + '\'' +
                ", sign='" + sign + '\'' +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", limitCreditPay=" + limitCreditPay +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                '}';
    }
}
