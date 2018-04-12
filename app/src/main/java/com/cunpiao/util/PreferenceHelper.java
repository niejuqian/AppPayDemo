package com.cunpiao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

/**
 * SharePreference工具类
 *
 * @author wangdeyun
 */
public class PreferenceHelper {

    private static SharedPreferences sp;
    private final static String SharePreferncesName = "SP_CUSTOMER_APP";
    public final static String SECOND_CACHE_NAME = "second_cache";
    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
        sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
    }

    /**
     * @param key
     * @param value
     * @return 是否保存成功
     */
    public static void setValue(String key, Object value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value).apply();
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value).apply();
        }
        else if (value instanceof Set) {
            new IllegalArgumentException("Value can not be Set object!");
        }
    }
    public static boolean setValue(String key, long value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        Editor edit = sp.edit();
        return edit.putLong(key, value).commit();
    }
    public static boolean setValue(String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        Editor edit = sp.edit();
        return edit.putInt(key, value).commit();
    }


    public static boolean setValue(String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        Editor edit = sp.edit();
        return edit.putBoolean(key, value).commit();
    }


    public static boolean getBoolean(String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, false);
    }

    public static boolean getBoolean(String key,boolean dftValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, dftValue);
    }

    public static String getString(String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        return sp.getString(key, "");
    }

    public static String getString(String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static Float getFloat(String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        return sp.getFloat(key, 0f);
    }

    public static int getInt(String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, 0);
    }

    public static long getLong(String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        return sp.getLong(key, 0);

    }

    public static void remove(String... key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        for (int i = 0; i < key.length; i++) {
            sp.edit().remove(key[i]).commit();
        }
    }

    public static void remove(String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).apply();
    }
}
