package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.helper.DataChangeEvent;
import com.treasure.parttimejob.helper.GenderChangeListener;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.model.MsgBean;
import com.treasure.parttimejob.ui.model.PartTimeBean;
import com.treasure.parttimejob.ui.view.AlertDialog;
import com.treasure.parttimejob.utils.GlideUtils;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PartDetailActivity extends BaseActivity implements GenderChangeListener {

  private PartTimeBean partTimeBean;
  private AlertDialog alertDialog;

  public static void start(Context context, long part_id) {
    Intent intent = new Intent(context, PartDetailActivity.class);
    intent.putExtra("part_id", part_id);
    context.startActivity(intent);
  }

  @BindView(R.id.background)
  ImageView backGround;
  @BindView(R.id.collect_img)
  ImageView collectImg;
  @BindView(R.id.part_name)
  TextView partName;
  @BindView(R.id.part_money)
  TextView partMoney;
  @BindView(R.id.part_tag)
  TextView partTag;
  @BindView(R.id.business_image)
  ImageView busImg;
  @BindView(R.id.business_name)
  TextView busName;
  @BindView(R.id.part_content)
  TextView partContent;
  @BindView(R.id.part_address)
  TextView partAddr;
  @BindView(R.id.part_map)
  ImageView partMap;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_part_detail);
  }

  @Override
  protected void initView() {
    Intent intent = getIntent();
    if (intent != null) {
      long partId = intent.getLongExtra("part_id", 0);
      partTimeBean = PJApp.userDaoManager.queryIdPart(partId);
    }
    GlideUtils.loadImg(this, partTimeBean.getPart_image(), backGround, R.mipmap.pic_welcome);
    GlideUtils.loadRoundImg(this, partTimeBean.getBusiness_image(), busImg, R.mipmap.pic_welcome, 20);
    partName.setText(partTimeBean.getPart_name());
    partMoney.setText(partTimeBean.getPart_money());
    partTag.setText(partTimeBean.getPart_clearing() + "/" + partTimeBean.getPart_duration() + "/" + partTimeBean.getPart_sex());
    busName.setText(partTimeBean.getBusiness_name());
    partContent.setText(partTimeBean.getPart_content());
    partAddr.setText(partTimeBean.getPart_address());
    collectImg.setSelected(partTimeBean.isPart_collect());
  }

  @Override
  protected void setListener() {

  }

  @OnClick({R.id.back, R.id.part_address,R.id.part_map, R.id.collect_layout, R.id.apply})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.part_map:
      case R.id.part_address:
        MapActivity.start(this,partTimeBean.getLatitude(),partTimeBean.getLongitude());
        break;
      case R.id.collect_layout:
        if (partTimeBean.isPart_collect()) {
          collectImg.setSelected(false);
          partTimeBean.setPart_collect(false);
        } else {
          collectImg.setSelected(true);
          partTimeBean.setPart_collect(true);
        }
        PJApp.userDaoManager.updatePart(partTimeBean);
        break;
      case R.id.apply:
        //是否有简历
        SharedPreferencesUtil preferencesUtil = SharedPreferencesUtil.getInstance();
        if (preferencesUtil.getString(Contacts.USER_IDENTITY).equals("boss")){
          ToastUtils.showSingleToast("您是商家，不能进行报名");
          return;
        }
        boolean isResume = preferencesUtil.getBoolean(Contacts.USER_RESUME, false);
        if (isResume) {
          MsgBean msgBean = new MsgBean(System.currentTimeMillis(), preferencesUtil.getString(Contacts.USER_NAME),
              preferencesUtil.getString(Contacts.USER_IMAGE), partTimeBean.getPart_name(),
              "感谢您报名该职位，请耐心等待商家的录取信息");
          PJApp.userDaoManager.insertMsg(msgBean);
          partTimeBean.setPart_apply(1);
          PJApp.userDaoManager.updatePart(partTimeBean);
          EventBus.getDefault().post(new DataChangeEvent("msg"));
          ApplySuccessActivity.start(this, partTimeBean.getBusiness_phone(), partTimeBean.getBusiness_name());
        } else {
          if (alertDialog == null) {
            alertDialog = new AlertDialog(this);
            alertDialog.setContent("您还未创建简历，是否先创建简历", "创建简历");
            alertDialog.setListener(this);
          }
          alertDialog.show();
        }
        break;
    }
  }

  @Override
  public void genderClick(int id) {
    switch (id) {
      case R.id.done:
        alertDialog.dismiss();
        MyResumeActivity.start(this);
        break;
    }
  }
}
