package com.treasure.parttimejob.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.helper.OnFinishListener;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.view.ProgressView;
import com.treasure.parttimejob.utils.LogUtil;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.ToastUtils;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {
  @BindView(R.id.progress_view)
  ProgressView progressView;

  @Override
  protected void loadContentLayout() {
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_welcome);
  }

  @Override
  protected void initView() {
    progressView.startCountDown(300, new OnFinishListener() {
      @Override
      public void onFinish() {
        checkState();
      }
    });
  }

  @Override
  protected void setListener() {

  }

  //检查登录状态
  private void checkState() {
    boolean status = SharedPreferencesUtil.getInstance().getBoolean(Contacts.LOGIN_STATUS, false);
    if (status) {
      checkIdentity();
    } else {
      LoginActivity.start(WelcomeActivity.this);
      WelcomeActivity.this.finish();
    }
  }

  //检查身份（雇员和商家）
  private void checkIdentity() {
    String identity = SharedPreferencesUtil.getInstance().getString(Contacts.USER_IDENTITY);
    if (TextUtils.isEmpty(identity)) {
      LogUtil.e("身份获取为空");
    } else {
      MainActivity.start(WelcomeActivity.this);
      WelcomeActivity.this.finish();
    }
  }
}
