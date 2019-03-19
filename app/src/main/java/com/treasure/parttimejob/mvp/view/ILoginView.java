package com.treasure.parttimejob.mvp.view;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/27
 * description:view的接口 负责mvp View部分
 * ============================================================
 */
public interface ILoginView {
  String getUserName();
  String getUserPwd();
  void showLoginSuccess();
  void showLoginFail(String msg);
}
