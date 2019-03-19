package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.helper.GenderChangeListener;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.helper.FinishEvent;
import com.treasure.parttimejob.ui.view.OutLogDialog;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.ToastUtils;
import com.treasure.parttimejob.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements GenderChangeListener {
  public static void start(Context context) {
    Intent intent = new Intent(context, SettingsActivity.class);
    context.startActivity(intent);
  }

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.versionname)
  TextView versionn;

  private String persnalDir;
  private String versionName;
  private OutLogDialog outLogDialog;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_settings);
    try {
      versionName = Utils.getVersionName(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void initView() {
    title.setText("设置");
    versionn.setText(versionName);
  }

  @Override
  protected void setListener() {
  }

  @OnClick({R.id.back, R.id.out_login, R.id.about})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.out_login:
        if (outLogDialog == null) {
          outLogDialog = new OutLogDialog(this);
          outLogDialog.setListener(this);
        }
        outLogDialog.show();
        break;
      case R.id.about:
//                Intent intent1 = new Intent(this, PersonalAboutHuLuActivity.class);
//                startActivity(intent1);
        ToastUtils.showToast("兼职");
        break;
    }
  }

  @Override
  public void genderClick(int id) {
    switch (id) {
      case R.id.cancle:
        outLogDialog.dismiss();
        break;
      case R.id.outlogin:
        outLogin();
        outLogDialog.dismiss();
        break;
    }
  }

  private void outLogin() {
    SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();
    boolean resume = instance.getBoolean(Contacts.USER_RESUME, false);
    instance.removeAll();
    instance.putBoolean(Contacts.INIT_DATA,true);
    instance.putBoolean(Contacts.USER_RESUME,resume);
    ToastUtils.showToast("退出成功");
    LoginActivity.start(this);
    finish();
    //清除activity
    EventBus.getDefault().post(new FinishEvent());
  }

}
