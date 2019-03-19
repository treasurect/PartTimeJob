package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.mvp.presenter.LoginPresenter;
import com.treasure.parttimejob.mvp.view.IRegisterView;
import com.treasure.parttimejob.ui.adapter.ApplyAdapter;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.utils.LogUtil;
import com.treasure.parttimejob.utils.ToastUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegisterView {
  public static void start(Context context) {
    Intent intent = new Intent(context, RegisterActivity.class);
    context.startActivity(intent);
  }

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.phonenum)
  EditText phonenum;
  @BindView(R.id.passwd)
  EditText passwd;
  @BindView(R.id.introduce)
  EditText intro;
  @BindView(R.id.spinner)
  Spinner spinner;
  @BindView(R.id.root)
  RelativeLayout root;

  private LoginPresenter presenter;
  private String identity = "personal";

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_register);

  }

  @Override
  protected void initView() {
    title.setText("注册");
    presenter = new LoginPresenter(this);
    initSpinnerData();
  }

  private void initSpinnerData() {
    ArrayList<String> list = new ArrayList<>();
    list.add("个人");
    list.add("商家");
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(arrayAdapter);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
          identity = "personal";
        }else if (position == 1){
          identity = "boss";
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  @Override
  protected void setListener() {
  }

  @OnClick({R.id.back, R.id.next, R.id.root})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.next:
        String phone = phonenum.getText().toString().trim();
        String psw = passwd.getText().toString().trim();
        if (TextUtils.isEmpty(psw)) {
          ToastUtils.showSingleToast("密码不能为空");
          return;
        } else if (psw.length() < 6) {
          ToastUtils.showSingleToast("密码长度必须大于等于6");
          return;
        } else {
          //注册时先提交验证码  检验成功后进行注册
          register();
        }
        break;
      case R.id.root:
//        Utils.hideSoftInput(this, root);
        break;
    }
  }

  //注册
  private void register() {
    presenter.register();
  }

  @Override
  public String getUserName() {
    return phonenum.getText().toString().trim();
  }

  @Override
  public String getUserPwd() {
    return passwd.getText().toString().trim();
  }

  @Override
  public String getUserIntro() {
    String introStr = intro.getText().toString().trim();
    if (TextUtils.isEmpty(introStr))
      introStr = "这个人没有说什么";
    return introStr;
  }

  @Override
  public String getUserIdentity() {
    return identity;
  }

  @Override
  public void showSuccess() {
    ToastUtils.showSingleToast("注册成功");
    finish();
  }

  @Override
  public void showFailMsg(String msg) {
    ToastUtils.showSingleToast(msg);
  }
}
