package com.cunpiao;

import android.app.Application;

import com.cunpiao.bean.CommonHeaderParam;
import com.cunpiao.bean.EnvironmentConfigEnum;
import com.cunpiao.bean.PreferenceKeyEnum;
import com.cunpiao.bll.PayUtil;
import com.cunpiao.bll.ServiceApi;
import com.cunpiao.network.RetrofitControl;
import com.cunpiao.util.AppUtil;
import com.cunpiao.util.Logger;
import com.cunpiao.util.PreferenceHelper;
import com.cunpiao.util.StringUtils;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-04-11 11:59
 * @DESCRIPTION:
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initUtil();
        initRetrofit();
        PayUtil.init(this);
    }

    private void initRetrofit(){
        EnvironmentConfigEnum environmentConfigEnum = EnvironmentConfigEnum.ONLINE;
        String key = "LOCAL";
        if (!EnvironmentConfigEnum.ONLINE.getName().equals(key)){
            String cacheKey = PreferenceHelper.getString(PreferenceKeyEnum.ENVIRONMENTT.getKey());
            if (StringUtils.isEmpty(cacheKey)) {
                PreferenceHelper.setValue(PreferenceKeyEnum.ENVIRONMENTT.getKey(),key);
            } else {
                key = cacheKey;
            }
            environmentConfigEnum = EnvironmentConfigEnum.getEnum(key);
        }
        Logger.e("================environment=" + key);
        CommonHeaderParam commonParam = new CommonHeaderParam();
        RetrofitControl.Builder builder = new RetrofitControl.Builder()
                .setApiClass(ServiceApi.class)
                .setHostAddress(environmentConfigEnum.getHost())
                .setCommonParam(commonParam);
        RetrofitControl.getSingleton().initRegrofit(builder);
    }

    /**
     * 初始化自定义util
     */
    private void initUtil(){
        AppUtil.init(this);
        PreferenceHelper.init(this);
    }
}
