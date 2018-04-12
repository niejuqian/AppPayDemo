package com.cunpiao.bean;


import com.cunpiao.network.annotation.HeaderParam;
import com.cunpiao.network.param.BaseCommonParam;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-09-08 18:13
 * @DESCRIPTION:
 */

public class CommonHeaderParam extends BaseCommonParam {
    @HeaderParam
    private String version;
    @HeaderParam
    private String platform = "android";

    @HeaderParam
    private String token;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
