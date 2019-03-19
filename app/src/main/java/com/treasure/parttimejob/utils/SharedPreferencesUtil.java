package com.treasure.parttimejob.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ============================================================
 * Author：   treasure
 * time：2019/2/19
 * description:
 * ============================================================
 */
public class SharedPreferencesUtil {
  private static SharedPreferencesUtil prefsUtil;
  private Context context;
  private SharedPreferences prefs;
  private SharedPreferences.Editor editor;

  public synchronized static SharedPreferencesUtil getInstance() {
    return prefsUtil;
  }

  public static void init(Context context, String prefsName, int mode) {
    prefsUtil = new SharedPreferencesUtil();
    prefsUtil.context = context;
    prefsUtil.prefs = prefsUtil.context.getSharedPreferences(prefsName, mode);
    prefsUtil.editor = prefsUtil.prefs.edit();
  }

  public boolean getBoolean(String key, boolean defValue) {
    return prefs.getBoolean(key, defValue);
  }

  public SharedPreferencesUtil putBoolean(String key, Boolean value) {
    editor.putBoolean(key, value);
    editor.commit();
    return this;
  }

  public int getInt(String key, int defValue) {
    return prefs.getInt(key, defValue);
  }

  public SharedPreferencesUtil putInt(String key, int value) {
    editor.putInt(key, value);
    editor.commit();
    return this;
  }

  public String getString(String key, String defValue) {
    return prefs.getString(key, defValue);
  }

  public String getString(String key) {
    return prefs.getString(key, null);
  }

  public SharedPreferencesUtil putString(String key, String value) {
    editor.putString(key, value);
    editor.commit();
    return this;
  }
  public SharedPreferencesUtil removeAll() {
    editor.clear();
    editor.commit();
    return this;
  }
}
