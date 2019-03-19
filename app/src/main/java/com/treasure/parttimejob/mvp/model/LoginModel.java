package com.treasure.parttimejob.mvp.model;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.db.utils.UserDaoManager;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.ToastUtils;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/27
 * description: model 的业务实现类
 * ============================================================
 */
public class LoginModel implements ILoginModel{

  @Override
  public void login(String name, String pwd, OnLoginListener listener) {
    boolean personExist = UserDaoManager.checkPersonExist(name);
    if (personExist){
      String userPwd = UserDaoManager.getUserPwd(name);
      if (pwd.equals(userPwd)){
        UserInfo userInfo = UserDaoManager.getUser(name);
        putSharedPreferences(userInfo);
        listener.success();
      }else {
        listener.failed("用户密码错误");
      }
    }else {
      listener.failed("用户不存在");
    }
  }

  @Override
  public void register(String name, String pwd, String intro,String identity, OnLoginListener listener) {
    boolean personExist = UserDaoManager.checkPersonExist(name);
    if (personExist) {
      listener.failed("用户已存在");
    } else {
      UserInfo userInfo = new UserInfo(System.currentTimeMillis(),name, pwd, intro, identity);
      PJApp.userDaoManager.insertUser(userInfo);
      listener.success();
    }
  }

  private void putSharedPreferences(UserInfo infoBean) {
    SharedPreferencesUtil preferencesUtil = SharedPreferencesUtil.getInstance();
    preferencesUtil.putBoolean(Contacts.LOGIN_STATUS,true);
    preferencesUtil.putString(Contacts.USER_NAME,infoBean.getName());
    preferencesUtil.putString(Contacts.USER_PWD,infoBean.getPwd());
    preferencesUtil.putString(Contacts.USER_SIGN,infoBean.getInfo());
    preferencesUtil.putString(Contacts.USER_IDENTITY,infoBean.getIdentity());
  }
}
