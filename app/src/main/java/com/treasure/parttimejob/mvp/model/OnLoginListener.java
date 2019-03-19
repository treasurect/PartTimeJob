package com.treasure.parttimejob.mvp.model;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/27
 * description:
 * ============================================================
 */
public interface OnLoginListener {
  void success();
  void failed(String errorMsg);
}
