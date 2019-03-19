package com.treasure.parttimejob.ui.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/3/7
 * description:
 * ============================================================
 */
@Entity
public class PartTimeBean {
  @Id
  private Long id;   //id
  private String part_name;//name
  private String part_duration;//长期 短期
  private String part_content;//内容
  private String part_address;//地址
  private double latitude;//纬度
  private double longitude;//经度
  private String part_money;//薪资
  private String part_sex;//男女不限
  private String part_clearing;//日结 月结
  private String part_type;//技能 实习 一般
  private Long skill_id;//技能ID
  private String part_state;//在招
  private String part_image;//职位图片
  private String business_image;//商家图片
  private String business_name;//商家名字
  private String business_phone;//商家电话
  private boolean part_collect;//用户是否收藏
  private int part_apply;//用户是否申请  未0  申请1   录取 2   完成3
  private float rating;//评分

  public PartTimeBean() {
  }

  @Generated(hash = 56279483)
  public PartTimeBean(Long id, String part_name, String part_duration,
          String part_content, String part_address, double latitude,
          double longitude, String part_money, String part_sex,
          String part_clearing, String part_type, Long skill_id,
          String part_state, String part_image, String business_image,
          String business_name, String business_phone, boolean part_collect,
          int part_apply, float rating) {
      this.id = id;
      this.part_name = part_name;
      this.part_duration = part_duration;
      this.part_content = part_content;
      this.part_address = part_address;
      this.latitude = latitude;
      this.longitude = longitude;
      this.part_money = part_money;
      this.part_sex = part_sex;
      this.part_clearing = part_clearing;
      this.part_type = part_type;
      this.skill_id = skill_id;
      this.part_state = part_state;
      this.part_image = part_image;
      this.business_image = business_image;
      this.business_name = business_name;
      this.business_phone = business_phone;
      this.part_collect = part_collect;
      this.part_apply = part_apply;
      this.rating = rating;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPart_name() {
    return part_name;
  }

  public void setPart_name(String part_name) {
    this.part_name = part_name;
  }

  public String getPart_duration() {
    return part_duration;
  }

  public void setPart_duration(String part_duration) {
    this.part_duration = part_duration;
  }

  public String getPart_content() {
    return part_content;
  }

  public void setPart_content(String part_content) {
    this.part_content = part_content;
  }

  public String getPart_address() {
    return part_address;
  }

  public void setPart_address(String part_address) {
    this.part_address = part_address;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getPart_money() {
    return part_money;
  }

  public void setPart_money(String part_money) {
    this.part_money = part_money;
  }

  public String getPart_sex() {
    return part_sex;
  }

  public void setPart_sex(String part_sex) {
    this.part_sex = part_sex;
  }

  public String getPart_clearing() {
    return part_clearing;
  }

  public void setPart_clearing(String part_clearing) {
    this.part_clearing = part_clearing;
  }

  public String getPart_type() {
    return part_type;
  }

  public void setPart_type(String part_type) {
    this.part_type = part_type;
  }

  public Long getSkill_id() {
    return skill_id;
  }

  public void setSkill_id(Long skill_id) {
    this.skill_id = skill_id;
  }

  public String getPart_state() {
    return part_state;
  }

  public void setPart_state(String part_state) {
    this.part_state = part_state;
  }

  public String getPart_image() {
    return part_image;
  }

  public void setPart_image(String part_image) {
    this.part_image = part_image;
  }

  public String getBusiness_image() {
    return business_image;
  }

  public void setBusiness_image(String business_image) {
    this.business_image = business_image;
  }

  public String getBusiness_name() {
    return business_name;
  }

  public void setBusiness_name(String business_name) {
    this.business_name = business_name;
  }

  public String getBusiness_phone() {
    return business_phone;
  }

  public void setBusiness_phone(String business_phone) {
    this.business_phone = business_phone;
  }

  public boolean isPart_collect() {
    return part_collect;
  }

  public void setPart_collect(boolean part_collect) {
    this.part_collect = part_collect;
  }

  public int getPart_apply() {
    return part_apply;
  }

  public void setPart_apply(int part_apply) {
    this.part_apply = part_apply;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public boolean getPart_collect() {
      return this.part_collect;
  }
}
