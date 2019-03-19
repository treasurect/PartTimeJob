package com.treasure.parttimejob.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.ui.adapter.MsgAdapter;
import com.treasure.parttimejob.ui.base.BaseFragment;
import com.treasure.parttimejob.ui.model.MsgBean;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 首页  消息 Fragment
 */
public class MsgFragment extends BaseFragment {
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.back)
  ImageView back;
  @BindView(R.id.recycler)
  RecyclerView recyclerView;
  private ArrayList<MsgBean> list;
  private MsgAdapter msgAdapter;

  @Override
  protected void initView(Bundle savedInstanceState) {
    setContentView(R.layout.fragment_msg);
    initRecyclerView();
  }

  private void initRecyclerView() {
    list = new ArrayList<>();
    List<MsgBean> msgBeanList = PJApp.userDaoManager.queryIdMsg(SharedPreferencesUtil.getInstance().getString(Contacts.USER_NAME));
    Collections.reverse(msgBeanList);
    list.add(new MsgBean());
    list.addAll(msgBeanList);
    msgAdapter = new MsgAdapter(getContext(), list);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(msgAdapter);
  }

  @Override
  protected void setListener() {
    title.setText("消息通知");
    back.setVisibility(View.GONE);
  }

  @Override
  protected void onUserVisible() {

  }

  public void onDataChange() {
    list.clear();
    List<MsgBean> msgBeanList = PJApp.userDaoManager.queryIdMsg(SharedPreferencesUtil.getInstance().getString(Contacts.USER_NAME));
    Collections.reverse(msgBeanList);
    list.add(new MsgBean());
    list.addAll(msgBeanList);
    msgAdapter.notifyDataSetChanged();
  }
}
