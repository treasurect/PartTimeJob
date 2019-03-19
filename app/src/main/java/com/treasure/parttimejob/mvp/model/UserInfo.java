package com.treasure.parttimejob.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/27
 * description:
 * ============================================================
 */
@Entity
public class UserInfo {
  @Id
  private Long id;
  private String name;
  private String pwd;
  private String info;
  private String identity;

  public UserInfo() {
  }

  public UserInfo(String name, String pwd, String info) {
    this.name = name;
    this.pwd = pwd;
    this.info = info;
  }

  public UserInfo(String name, String pwd, String info, String identity) {
    this.name = name;
    this.pwd = pwd;
    this.info = info;
    this.identity = identity;
  }

  @Generated(hash = 1114975134)
  public UserInfo(Long id, String name, String pwd, String info,
                  String identity) {
    this.id = id;
    this.name = name;
    this.pwd = pwd;
    this.info = info;
    this.identity = identity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
