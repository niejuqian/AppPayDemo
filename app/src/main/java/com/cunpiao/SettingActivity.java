package com.cunpiao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cunpiao.bean.CommonHeaderParam;
import com.cunpiao.bll.ServiceApi;
import com.cunpiao.network.RetrofitControl;
import com.cunpiao.util.Logger;
import com.cunpiao.util.PreferenceHelper;
import com.cunpiao.util.StringUtils;
import com.cunpiao.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-04-17 10:55
 * @DESCRIPTION:
 */

public class SettingActivity extends BaseAppCompatActivity {
    @BindView(R.id.address_et)
    EditText addressEt;
    @BindView(R.id.http_rbtn)
    RadioButton httpRbtn;
    @BindView(R.id.https_rbtn)
    RadioButton httpsRbtn;
    @BindView(R.id.current_address_tv)
    TextView currentAddressTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        String requestAddress = PreferenceHelper.getString("request_address");
        currentAddressTv.setText("当前连接地址：" + (StringUtils.isBlank(requestAddress) ? "未知" : requestAddress));
    }

    private String getAddress(){
        return addressEt.getText().toString();
    }

    private String getHttp(){
        if (httpsRbtn.isChecked()) {
            return "https://";
        } else {
            return "http://";
        }
    }

    @OnClick(R.id.save_btn)
    void save(){
        String ip = getAddress();
        if (StringUtils.isBlank(ip)) {
            ToastUtil.showToast("请输入地址");
            return;
        }
        String address = getHttp() + ip;
        if (!address.substring(address.length()-1).equals("/")) {
            address = address + "/";
        }
        Logger.e(TAG,"============address=" + address);

        CommonHeaderParam commonParam = new CommonHeaderParam();
        RetrofitControl.Builder builder = new RetrofitControl.Builder()
                .setApiClass(ServiceApi.class)
                .setHostAddress(address)
                .setCommonParam(commonParam);
        RetrofitControl.getSingleton().initRegrofit(builder);
        ToastUtil.showToast("保存成功");
        PreferenceHelper.setValue("request_address",address);
        finish();
    }
}
