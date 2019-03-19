package com.treasure.parttimejob.helper;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/3/18
 * description:
 * ============================================================
 */
public class DataChangeEvent {
  private String type;

  public DataChangeEvent(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
