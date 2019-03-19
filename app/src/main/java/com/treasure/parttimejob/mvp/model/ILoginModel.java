package com.treasure.parttimejob.mvp.model;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/27
 * description:
 * ============================================================
 */
public interface ILoginModel {
  void login(String name,String pwd,OnLoginListener listener);
  void register(String name,String pwd,String intro,String identity,OnLoginListener listener);
}
