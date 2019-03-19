package com.treasure.parttimejob.ui.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/3/7
 * description:
 * ============================================================
 */
@Entity
public class MsgBean {
  @Id
  private Long id;
  private String user_name;
  private String image;
  private String name;
  private String content;

  @Generated(hash = 210018581)
  public MsgBean(Long id, String user_name, String image, String name,
          String content) {
      this.id = id;
      this.user_name = user_name;
      this.image = image;
      this.name = name;
      this.content = content;
  }

  @Generated(hash = 237905234)
  public MsgBean() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
