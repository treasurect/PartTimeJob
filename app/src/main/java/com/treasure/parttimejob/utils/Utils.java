package com.treasure.parttimejob.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/3/15
 * description:
 * ============================================================
 */
public class Utils {
  public static void callPhone(Context context, String phoneNum) {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    Uri data = Uri.parse("tel:" + phoneNum);
    intent.setData(data);
    context.startActivity(intent);
  }

  public static void callPhone2(Context context, String phoneNum) {
    Intent intent = new Intent(Intent.ACTION_CALL);
    Uri data = Uri.parse("tel:" + phoneNum);
    intent.setData(data);
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    context.startActivity(intent);
  }
  //版本名
  public static String getVersionName(Context context) throws Exception {
    // 获取packagemanager的实例
    PackageManager packageManager = context.getPackageManager();
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
    String version = packInfo.versionName;
    return version;
  }
  public static String format2YMD(long when) {

    SimpleDateFormat mouth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    return mouth.format(when);
  }
}
