package com.cunpiao.bean;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-03-02 11:55
 * @DESCRIPTION:
 *              sp缓存key值，方便清理缓存时统一处理
 */

public enum  PreferenceKeyEnum {
    LOGIN_KEY("login","是否登录"),
    ENVIRONMENTT("environment","服务器环境")
    ;

    private String key;
    private String desc;

    PreferenceKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
