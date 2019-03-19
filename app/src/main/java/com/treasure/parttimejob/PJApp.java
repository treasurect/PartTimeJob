package com.treasure.parttimejob;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.treasure.parttimejob.db.greendao.DaoMaster;
import com.treasure.parttimejob.db.greendao.DaoSession;
import com.treasure.parttimejob.db.utils.UserDaoManager;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;

/**
 * ============================================================
 * Author：   treasure
 * time：2019/2/19
 * description:
 * ============================================================
 */
public class PJApp extends Application {
  public static Context context;
  public static DaoSession daoSession;
  public static DaoMaster daoMaster;
  public static UserDaoManager userDaoManager;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    userDaoManager = UserDaoManager.getInstance(context);
    initPrefs();
    //百度地图
    SDKInitializer.initialize(this);
  }

  public static Context getContext() {
    return context;
  }

  private void initPrefs() {
    SharedPreferencesUtil.init(getApplicationContext(), "PartTime", Context.MODE_PRIVATE);
  }

  public static DaoMaster getDaoMaster(Context context){
    DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, "user.db", null);
    daoMaster = new DaoMaster(openHelper.getWritableDatabase());
    return daoMaster;
  }

  public static DaoSession getDaoSession(Context context){
    if (daoSession == null){
      if (daoMaster == null){
        daoMaster = getDaoMaster(context);
      }
      daoSession = daoMaster.newSession();
    }
    return daoSession;
  }
}
