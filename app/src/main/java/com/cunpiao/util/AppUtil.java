package com.cunpiao.util;

import android.content.Context;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-12 14:20
 * @DESCRIPTION:
 */

public class AppUtil {
    private static Context context;
    public static void init(Context ctx) {
        context = ctx;
    }
    public static Context getCtx(){
        return context;
    }
}
