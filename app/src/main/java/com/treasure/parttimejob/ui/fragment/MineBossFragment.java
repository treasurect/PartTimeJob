package com.treasure.parttimejob.ui.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.ui.activity.ApplyListActivity;
import com.treasure.parttimejob.ui.activity.MyResumeActivity;
import com.treasure.parttimejob.ui.activity.PartIssueActivity;
import com.treasure.parttimejob.ui.activity.SettingsActivity;
import com.treasure.parttimejob.ui.base.BaseFragment;
import com.treasure.parttimejob.utils.GlideUtils;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MineBossFragment extends BaseFragment {
  @BindView(R.id.head_img)
  ImageView headImg;
  @BindView(R.id.name)
  TextView nameTxt;
  @BindView(R.id.sign)
  TextView signTxt;
  @BindView(R.id.perfect_resume)
  TextView perfectResume;

  @Override
  protected void initView(Bundle savedInstanceState) {
    setContentView(R.layout.fragment_mine_boss);
    SharedPreferencesUtil preferencesUtil = SharedPreferencesUtil.getInstance();
    GlideUtils.loadRoundImg(getContext(), preferencesUtil.getString(Contacts.USER_IMAGE),headImg,R.mipmap.ic_launcher_logo,6);
    nameTxt.setText(preferencesUtil.getString(Contacts.USER_NAME));
    String string = preferencesUtil.getString(Contacts.USER_SIGN);
    signTxt.setText(string);
  }

  @Override
  protected void setListener() {

  }

  @Override
  protected void onUserVisible() {

  }

  @OnClick({R.id.perfect_resume, R.id.apply_layout, R.id.admit_layout, R.id.complete_layout, R.id.my_collect, R.id.settings})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.perfect_resume:
        MyResumeActivity.start(getContext());
        break;
      case R.id.apply_layout:
        ApplyListActivity.start(getContext(),0);
        break;
      case R.id.admit_layout:
        ApplyListActivity.start(getContext(),1);
        break;
      case R.id.complete_layout:
        ApplyListActivity.start(getContext(),2);
        break;
      case R.id.my_collect:
        PartIssueActivity.start(getContext());
        break;
      case R.id.settings:
        SettingsActivity.start(getContext());
        break;
    }
  }

}
