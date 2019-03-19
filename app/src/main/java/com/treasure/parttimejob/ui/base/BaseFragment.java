package com.treasure.parttimejob.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/3/7
 * description:
 * ============================================================
 */
public abstract class BaseFragment extends Fragment {
  private View contentView;
  private Unbinder unbinder;

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser) {
      onUserVisible();
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (contentView == null) {
      initView(savedInstanceState);
      setListener();
    }else {
      ViewGroup parent = (ViewGroup) contentView.getParent();
      if (parent != null){
        parent.removeView(contentView);
      }
    }
    return contentView;
  }

  protected abstract void initView(Bundle savedInstanceState);

  protected abstract void setListener();

  protected abstract void onUserVisible();

  protected void setContentView(@LayoutRes int layoutRes){
    contentView = LayoutInflater.from(getContext()).inflate(layoutRes,null);
    unbinder = ButterKnife.bind(this,contentView);
  }

  @Override
  public void onDestroy() {
    if (unbinder != null){
      unbinder.unbind();
      unbinder = null;
    }
    super.onDestroy();
  }
}
