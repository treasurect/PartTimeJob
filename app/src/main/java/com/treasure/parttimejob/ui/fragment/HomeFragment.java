package com.treasure.parttimejob.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.ui.activity.PartDetailActivity;
import com.treasure.parttimejob.ui.activity.PartListActivity;
import com.treasure.parttimejob.ui.adapter.PartTimeAdapter;
import com.treasure.parttimejob.ui.base.BaseFragment;
import com.treasure.parttimejob.ui.model.PartTimeBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 主页
 */
public class HomeFragment extends BaseFragment implements PartTimeAdapter.OnItemClickListener {

  @BindView(R.id.recycler)
  RecyclerView recyclerView;
  private ArrayList<PartTimeBean> list;
  private PartTimeAdapter msgAdapter;

  @Override
  protected void initView(Bundle savedInstanceState) {
    setContentView(R.layout.fragment_home);
    initRecyclerView();
  }

  private void initRecyclerView() {
    List<PartTimeBean> partTimeBeans = PJApp.userDaoManager.queryAllPart();
    Collections.reverse(partTimeBeans);
    list = new ArrayList<>();
    list.add(new PartTimeBean());
    list.addAll(partTimeBeans);
    msgAdapter = new PartTimeAdapter(getContext(), list,true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(msgAdapter);
  }

  @Override
  protected void setListener() {
    msgAdapter.setOnItemClickListener(this);
  }

  @Override
  protected void onUserVisible() {

  }

  @Override
  public void onItemClick(PartTimeBean partTimeBean) {
    PartDetailActivity.start(getContext(),partTimeBean.getId());
  }

  public void onDataChange(){
    list.clear();
    List<PartTimeBean> partTimeBeans = PJApp.userDaoManager.queryAllPart();
    Collections.reverse(partTimeBeans);
    list.add(new PartTimeBean());
    list.addAll(partTimeBeans);
    msgAdapter.notifyDataSetChanged();
  }
}
