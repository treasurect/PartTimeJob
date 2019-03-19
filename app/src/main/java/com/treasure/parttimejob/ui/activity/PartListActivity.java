package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.ui.adapter.PartTimeAdapter;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.model.PartTimeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PartListActivity extends BaseActivity implements PartTimeAdapter.OnItemClickListener {

  private String partType;

  public static void start(Context context, String type) {
    Intent intent = new Intent(context, PartListActivity.class);
    intent.putExtra("part_type", type);
    context.startActivity(intent);
  }

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.recycler)
  RecyclerView recyclerView;
  private ArrayList<PartTimeBean> list;
  private PartTimeAdapter msgAdapter;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_part_list);
  }

  @Override
  protected void initView() {
    Intent intent = getIntent();
    if (intent != null) {
      partType = intent.getStringExtra("part_type");
    }
    title.setText(partType + "兼职");
    initRecyclerView();
  }

  @Override
  protected void setListener() {
    msgAdapter.setOnItemClickListener(this);
  }

  private void initRecyclerView() {
    List<PartTimeBean> partTimeBeans = null;
    switch (partType) {
      case "长期":
      case "短期":
        partTimeBeans = PJApp.userDaoManager.queryLongPart(partType);
        break;
      case "技能":
      case "实习":
        partTimeBeans = PJApp.userDaoManager.querySkillPart(partType);
        break;
    }
    list = new ArrayList<>();
    list.addAll(partTimeBeans);
    msgAdapter = new PartTimeAdapter(this, list, false);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(msgAdapter);
  }

  @Override
  public void onItemClick(PartTimeBean partTimeBean) {
    PartDetailActivity.start(this, partTimeBean.getId());
  }
  @OnClick({R.id.back})
  public void onClick(View view){
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
    }
  }

}
