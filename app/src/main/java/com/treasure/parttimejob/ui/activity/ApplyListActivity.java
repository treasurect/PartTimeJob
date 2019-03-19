package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.ui.adapter.ApplyAdapter;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.model.PartTimeBean;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的申请  已录取 已完成 列表
 */
public class ApplyListActivity extends BaseActivity {

  private int type;
  private ArrayList<PartTimeBean> partTimeBeans;
  private ApplyAdapter applyAdapter;

  //type 0 已申请   1 已录取   2 已完成   3 收藏
  public static void start(Context context, int type) {
    Intent intent = new Intent(context, ApplyListActivity.class);
    intent.putExtra("type", type);
    context.startActivity(intent);
  }

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.recycler)
  RecyclerView recyclerView;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_apply_list);
  }

  @Override
  protected void initView() {
    Intent intent = getIntent();
    if (intent != null) {
      type = intent.getIntExtra("type", 0);
    }
    switch (type) {
      case 0:
        if (SharedPreferencesUtil.getInstance().getString(Contacts.USER_IDENTITY).equals("personal")) {
          title.setText("我的申请");
        } else {
          title.setText("待处理");
        }
        break;
      case 1:
        title.setText("已录取");
        break;
      case 2:
        title.setText("已完成");
        break;
      case 3:
        title.setText("我的收藏");
        break;
    }
    initRecycler();
  }

  private void initRecycler() {
    partTimeBeans = new ArrayList<>();
    List<PartTimeBean> partTimeBeanList;
    if (type == 3) {
      partTimeBeanList = PJApp.userDaoManager.queryCollectPart();
    } else {
      partTimeBeanList = PJApp.userDaoManager.queryApplyPart(type + 1);
    }
    partTimeBeans.addAll(partTimeBeanList);
    applyAdapter = new ApplyAdapter(this, partTimeBeans, type);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(applyAdapter);
  }

  @Override
  protected void setListener() {

  }

  @OnClick({R.id.back})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
    }
  }
}
