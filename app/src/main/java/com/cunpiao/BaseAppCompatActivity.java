package com.cunpiao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cunpiao.network.SubscriberManager;
import com.cunpiao.util.AppManager;
import com.cunpiao.util.Logger;

import butterknife.ButterKnife;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 15:31
 * @DESCRIPTION:
 */

public class BaseAppCompatActivity extends AppCompatActivity {
    protected static String TAG;
    private InputMethodManager inputMethodManager;
    private boolean isDealKeyboardHide = true;
    protected void onCreate(@Nullable Bundle savedInstanceState, int layoutId) {
        //禁止横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TAG = this.getClass().getSimpleName();
        initView();
        setListener();
        initData();
    }

    /**
     * 获取状态栏高度
     * @return
     */
    protected int getStatusBarHeight() {
        int statusBarHeight = 75;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    protected int getWindowHeight(){
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    protected void initData(){

    }

    protected void initView(){

    }

    protected void setListener(){}

    protected void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this,cls));
    }

    protected void startActivityForResult(Class<? extends Activity> cls,int code) {
        startActivityForResult(new Intent(this,cls),code);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideInput(v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (!isDealKeyboardHide) return false;
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    protected void setIsDealKeyboardHide(boolean isDealKeyboardHide) {
        this.isDealKeyboardHide = isDealKeyboardHide;
    }

    /**
     * 显示软键盘
     */
    public void showInput(View v){
        inputMethodManager = getInputMethodManager();
        if (null != inputMethodManager && null != v) {
            inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideInput(View v){
        inputMethodManager = getInputMethodManager();
        if (null != inputMethodManager && null != v) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    public InputMethodManager getInputMethodManager() {
        if (null == inputMethodManager) {
            inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return inputMethodManager;
    }


    @Override
    public Resources getResources() { // 屏蔽设置字体大小效果
        Resources res = super.getResources();
        Configuration conf = new Configuration();
        conf.setToDefaults();
        res.updateConfiguration(conf, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e(TAG,"==============onDestory");
        SubscriberManager.getSingleton().onDestory(this);
        AppManager.getAppManager().finishActivity(this);
    }
}
