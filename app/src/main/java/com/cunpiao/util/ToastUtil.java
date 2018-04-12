package com.cunpiao.util;

import android.widget.Toast;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-09-06 17:58
 * @DESCRIPTION:
 *          消息提示帮助类
 */

public class ToastUtil {
    static Toast mToast = null;
    public static void showToast(String text) {
        showMessage(text, Toast.LENGTH_SHORT);
    }

    public static void showToast(String text, int time) {
        showMessage(text, time);
    }

    private static void showMessage(final String msg,
                                    final int len) {
        if (null == msg || msg.trim().length() == 0) return;
        if (mToast != null) {
            mToast.setText(msg);
            mToast.setDuration(len);
        } else {
            mToast = Toast.makeText(AppUtil.getCtx(), msg, len);
        }
        mToast.show();
    }
}
