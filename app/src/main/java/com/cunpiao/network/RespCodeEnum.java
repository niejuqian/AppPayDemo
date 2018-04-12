package com.cunpiao.network;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:49
 * @DESCRIPTION:
 */

public enum RespCodeEnum {
    SUCCESS("0","响应成功"),
    SYS_ERROR("-1","请求失败"),
    NETWORK_ERROR("408","网络异常"),
    CONNECT_ERROR("500","连接服务器失败"),
    TOKEN_INVALID("105002","令牌失效，请重新登录"),
    ;
    String code;
    String message;
    RespCodeEnum(String code, String message) {
        this.code = code;
        this.message  = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static boolean isToast(String code){
        // TODO: 2018/3/5 这里匹配code，过滤掉不用toast的错误
        return true;
    }

    /**
     * 是否需要重新登录
     * @param code
     * @return
     */
    public static boolean reLogin(String code) {
        RespCodeEnum codeEnum = null;
        for (RespCodeEnum respCodeEnum : RespCodeEnum.values()) {
            if (respCodeEnum.getCode().equals(code)) {
                codeEnum = respCodeEnum;
                break;
            }
        }
        if (codeEnum != null &&
                codeEnum == TOKEN_INVALID ){
            return true;
        }
        return false;
    }
}
