package com.treasure.parttimejob.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplySuccessActivity extends BaseActivity {

  private String phone;
  private String name;

  public static void start(Context context, String phone, String name) {
    Intent intent = new Intent(context, ApplySuccessActivity.class);
    intent.putExtra("phone", phone);
    intent.putExtra("name", name);
    context.startActivity(intent);
  }

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.business_name)
  TextView nameTxt;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_apply_success);
  }

  @Override
  protected void initView() {
    title.setText("报名成功");
    Intent intent = getIntent();
    if (intent != null) {
      phone = intent.getStringExtra("phone");
      name = intent.getStringExtra("name");
    }
    nameTxt.setText("联系人：" + name);
  }

  @Override
  protected void setListener() {

  }

  @OnClick({R.id.back, R.id.business_phone})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.business_phone:
        Utils.callPhone(this,phone);
        break;
    }
  }
}
