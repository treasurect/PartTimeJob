package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.mvp.model.UserInfo;
import com.treasure.parttimejob.mvp.presenter.LoginPresenter;
import com.treasure.parttimejob.mvp.view.ILoginView;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {
  public static void start(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }

  @BindView(R.id.phonenum)
  EditText phonenum;
  @BindView(R.id.passwd)
  EditText passwd;
  @BindView(R.id.login_btn)
  TextView loginBtn;
  @BindView(R.id.phonehitn)
  TextView phonehitn;
  @BindView(R.id.pswhint)
  TextView pswhint;

  private boolean see = false;
  private String accout;
  private String psw;

  private LoginPresenter presenter;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_login);
  }

  @Override
  protected void initView() {
    presenter = new LoginPresenter(this);
  }

  @Override
  protected void setListener() {
    loginBtn.setClickable(false);
    phonenum.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        String string = s.toString();
        if (string.length() > 0 && phonehitn.getVisibility() == View.GONE) {
          phonehitn.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        String string = s.toString();
        if (string.length() == 0 && phonehitn.getVisibility() == View.VISIBLE) {
          phonehitn.setVisibility(View.GONE);
        }
        String psw = passwd.getText().toString().trim();
//                if (s.length()>0&& psw.length()>0){
//                    btnHasInput();
//                }else {
//                    btnNoInput();
//                }
      }
    });
    passwd.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        String string = s.toString();
        if (string.length() > 0 && pswhint.getVisibility() == View.GONE) {
          pswhint.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        String string = s.toString();
        if (string.length() == 0 && pswhint.getVisibility() == View.VISIBLE) {
          pswhint.setVisibility(View.GONE);
        }
        String num = phonenum.getText().toString();
        if (s.length() > 0 && num.length() > 0) {
//                    loginBtn.setBackgroundResource(R.drawable.loginbtn);
//                    loginBtn.setAlpha((float)1);
          loginBtn.setClickable(true);
          loginBtn.setSelected(true);
        } else {
//                    loginBtn.setBackgroundResource(R.drawable.loginbtn_noinput);
//                    loginBtn.setAlpha((float)0.2);
          loginBtn.setClickable(false);
          loginBtn.setSelected(false);
        }
      }
    });
  }

  @OnClick({R.id.seepasswd, R.id.fogetpass, R.id.register, R.id.root, R.id.login_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.seepasswd:
        if (see) {
          passwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          see = false;
        } else {
          passwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
          see = true;
        }
        break;
      case R.id.fogetpass:
//        ResetPassActivity.start(this);
        break;
      case R.id.register:
        RegisterActivity.start(this);
        break;

      case R.id.root:
//        Utils.hideSoftInput(this, passwd);
        break;

      case R.id.login_btn:
//        if (!Utils.isFastClick()) {
        boolean clickable = loginBtn.isClickable();
        if (clickable) {
          accout = phonenum.getText().toString();
          psw = passwd.getText().toString();
          if (TextUtils.isEmpty(accout)) {
            ToastUtils.showSingleToast("手机号不能为空");
            return;
          } else if (TextUtils.isEmpty(psw)) {
            ToastUtils.showSingleToast("密码不能为空");
            return;
          } else if (psw.length() < 6) {
            ToastUtils.showSingleToast("密码必须大于等于6位");
            return;
          } else if (accout.length() < 4) {
            ToastUtils.showSingleToast("请输入正确账号");
            return;
          } else {
            login();
          }
        }
//        }

        break;
    }
  }

  //登录
  private void login() {
    presenter.login();
  }

  @Override
  public String getUserName() {
    return accout;
  }

  @Override
  public String getUserPwd() {
    return psw;
  }

  @Override
  public void showLoginSuccess() {
    ToastUtils.showSingleToast("登录成功");
    MainActivity.start(LoginActivity.this);
    finish();
  }

  @Override
  public void showLoginFail(String msg) {
    ToastUtils.showSingleToast(msg);
  }
}

