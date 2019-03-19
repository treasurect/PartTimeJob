package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.helper.GenderChangeListener;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.view.GenderChangeDialog;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.Utils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class MyResumeActivity extends BaseActivity implements GenderChangeListener {
  public static void start(Context context) {
    Intent intent = new Intent(context, MyResumeActivity.class);
    context.startActivity(intent);
  }

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.name)
  EditText nameEt;
  @BindView(R.id.sex)
  TextView sexTx;
  @BindView(R.id.birthday)
  TextView birthTxt;
  @BindView(R.id.school)
  TextView schoolTxt;
  @BindView(R.id.school_name)
  EditText schoolName;
  @BindView(R.id.work_edit)
  EditText workEdit;
  @BindView(R.id.mine_edit)
  EditText mineEdit;

  private GenderChangeDialog dialog;
  private TimePickerView pvTime;
  private int pvType = 0;//区分年龄和入学日期
  private SharedPreferencesUtil preferencesUtil;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_my_resume);
  }

  @Override
  protected void initView() {
    title.setText("我的简历");
    preferencesUtil = SharedPreferencesUtil.getInstance();
    initTimePicker();
    initData();
  }

  private void initData() {
    String nick = preferencesUtil.getString(Contacts.USER_NICK);
    if (nick != null) nameEt.setText(nick);
    String sex = preferencesUtil.getString(Contacts.USER_SEX);
    if (sex != null) sexTx.setText(sex);
    String birth = preferencesUtil.getString(Contacts.USER_BIRTH);
    if (birth != null) birthTxt.setText(birth);
    String school_year = preferencesUtil.getString(Contacts.USER_SCHOOL_YEAR);
    if (school_year != null) schoolTxt.setText(school_year);
    String school_name = preferencesUtil.getString(Contacts.USER_SCHOOL);
    if (school_name != null) schoolName.setText(school_name);
    String work = preferencesUtil.getString(Contacts.USER_WORK);
    if (work != null) workEdit.setText(work);
    String intro = preferencesUtil.getString(Contacts.USER_INTRO);
    if (intro != null) mineEdit.setText(intro);
  }

  @Override
  protected void setListener() {

  }

  @OnClick({R.id.back, R.id.sex_layout, R.id.birthday_layout, R.id.school_layout})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        onBackPressed();
        break;
      case R.id.sex_layout:
        if (dialog == null) {
          dialog = new GenderChangeDialog(this);
          dialog.setListener(this);

        }
        dialog.show();
        break;
      case R.id.birthday_layout:
        pvTime.show(view);
        pvType = 0;
        break;
      case R.id.school_layout:
        pvTime.show(view);
        pvType = 1;
        break;
    }
  }

  @Override
  public void onBackPressed() {
    showLoading("正在保存");
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        dissLoading();
        finish();
        saveData();
      }
    },1000);
  }

  /**
   * 保存数据
   */
  private void saveData() {
    preferencesUtil.putString(Contacts.USER_NICK, nameEt.getText().toString().trim());
    preferencesUtil.putString(Contacts.USER_SEX, sexTx.getText().toString().trim());
    preferencesUtil.putString(Contacts.USER_BIRTH, birthTxt.getText().toString().trim());
    preferencesUtil.putString(Contacts.USER_SCHOOL_YEAR, schoolTxt.getText().toString().trim());
    preferencesUtil.putString(Contacts.USER_SCHOOL, schoolName.getText().toString().trim());
    preferencesUtil.putString(Contacts.USER_WORK, workEdit.getText().toString().trim());
    preferencesUtil.putString(Contacts.USER_INTRO, mineEdit.getText().toString().trim());
    preferencesUtil.putBoolean(Contacts.USER_RESUME,true);
  }

  @Override
  public void genderClick(int id) {
    switch (id) {
      case R.id.man:
        SharedPreferencesUtil.getInstance().putString(Contacts.USER_SEX, "1");
        changeSex(1);
        if (dialog != null) {
          dialog.dismiss();
        }
        break;
      case R.id.women:
        SharedPreferencesUtil.getInstance().putString(Contacts.USER_SEX, "2");
        changeSex(2);
        if (dialog != null) {
          dialog.dismiss();
        }
        break;
      case R.id.unkonow:
        SharedPreferencesUtil.getInstance().putString(Contacts.USER_SEX, "3");
        changeSex(3);
        if (dialog != null) {
          dialog.dismiss();
        }
        break;
      case R.id.cancle:
        if (dialog != null) {
          dialog.dismiss();
        }
        break;
    }
  }

  private void changeSex(int changesex) {
    if (changesex == 1) {
      sexTx.setTextColor(getResources().getColor(R.color.black_2d));
      sexTx.setText("男");
    } else if (changesex == 2) {
      sexTx.setTextColor(getResources().getColor(R.color.black_2d));
      sexTx.setText("女");
    } else if (changesex == 3) {
      sexTx.setTextColor(getResources().getColor(R.color.black_2d));
      sexTx.setText("保密");
    } else {
      sexTx.setTextColor(getResources().getColor(R.color.black_2d));
      sexTx.setText("未设置");
    }
  }

  //初始化时间选择器
  private void initTimePicker() {
    Calendar selectedDate = Calendar.getInstance();
    Calendar startDate = Calendar.getInstance();
    startDate.set(1970, 0, 1);
    Calendar endDate = Calendar.getInstance();
    endDate.set(2019, 3, 28);
    pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {

      @Override
      public void onTimeSelect(Date date, View v) {
        String birthday = Utils.format2YMD(date.getTime());
        if (pvType == 0) {
          birthTxt.setText(birthday);
        } else {
          schoolTxt.setText(birthday);
        }
      }
    }) //年月日时分秒 的显示与否，不设置则默认全部显示
        .setType(new boolean[]{true, true, true, false, false, false})
        .setLabel("", "", "", "", "", "")
        .isCenterLabel(false)
        .setDividerColor(Color.DKGRAY)
        .setContentSize(21)
        .setDate(selectedDate)
        .setRangDate(startDate, endDate)
        .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
        .setDecorView(null)
        .build();
  }
}
