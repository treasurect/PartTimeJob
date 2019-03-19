package com.treasure.parttimejob.mvp.view;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/27
 * description:
 * ============================================================
 */
public interface IRegisterView {
  String getUserName();
  String getUserPwd();
  String getUserIntro();
  String getUserIdentity();
  void showSuccess();
  void showFailMsg(String msg);
}
