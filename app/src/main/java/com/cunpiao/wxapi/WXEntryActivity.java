package com.cunpiao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cunpiao.MainActivity;
import com.cunpiao.R;
import com.cunpiao.bll.PayUtil;
import com.cunpiao.util.Constants;
import com.cunpiao.util.Logger;
import com.cunpiao.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
		PayUtil.getmWxApi().handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		ToastUtil.showToast("=============================》》》》intent = ");
		setIntent(intent);
		PayUtil.getmWxApi().handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		ToastUtil.showToast("=============================》》》》openid = " + req.openId);
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			goToGetMsg();		
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
			ToastUtil.showToast(getResources().getString(R.string.launch_from_wx));
			break;
		default:
			break;
		}
	}

	@Override
	public void onResp(BaseResp resp) {
		ToastUtil.showToast("=============================》》》》openid = " + resp.openId);
		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			ToastUtil.showToast("code = " + ((SendAuth.Resp) resp).code);
		}
		
		int result = 0;
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = R.string.errcode_success;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}
		ToastUtil.showToast(getString(result));
	}
	
	private void goToGetMsg() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtras(getIntent());
		startActivity(intent);
		finish();
	}
	
	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
		WXMediaMessage wxMsg = showReq.message;
		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
		
		StringBuffer msg = new StringBuffer();
		msg.append("description: ");
		msg.append(wxMsg.description);
		msg.append("\n");
		msg.append("extInfo: ");
		msg.append(obj.extInfo);
		msg.append("\n");
		msg.append("filePath: ");
		msg.append(obj.filePath);
		Logger.e(msg.toString());
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
		startActivity(intent);
		finish();
	}
}