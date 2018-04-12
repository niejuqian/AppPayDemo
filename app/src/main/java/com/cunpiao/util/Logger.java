package com.cunpiao.util;

import android.util.Log;


/**
 * 日志工具类
 * @author nie_jq
 * @time 2016/11/17 17:42
 * @copyright 世纪银河
 */
public class Logger {

    public static final boolean DEBUG = true;//BuildConfig.LOG_DEBUG;
    private static final String DEFAULT_TAG = "CPCash";

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }


    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);
    }

    public static void i(String msg) {
        if (DEBUG)
            Log.i(DEFAULT_TAG, msg);
    }


    public static void e(String msg) {
        if (DEBUG)
            Log.e(DEFAULT_TAG, msg);
    }

    public static void v(String msg) {
        if (DEBUG)
            Log.v(DEFAULT_TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w(DEFAULT_TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String msg) {
        if (DEBUG)
            Log.d(DEFAULT_TAG, msg);
    }

    /**
     * 输出系统错误
     *
     * @param msg
     */
    public static void ee(String msg) {
        Log.v(DEFAULT_TAG, msg);
    }

    public static void wtf(String msg) {
        Log.i("wtf", msg);
    }
}
