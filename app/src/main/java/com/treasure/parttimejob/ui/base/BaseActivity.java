package com.treasure.parttimejob.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.treasure.parttimejob.ui.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * ============================================================
 * Author：   treasure
 * time：2019/2/19
 * description:
 * ============================================================
 */
public abstract class BaseActivity extends AppCompatActivity {
  private LoadingDialog.Builder builder;
  private LoadingDialog loading;
  private Context context;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    loadContentLayout();
    context = this;
    ButterKnife.bind(this);
    initView();
    setListener();
  }
  public void showLoading(String name) {
    builder = new LoadingDialog.Builder(this)
        .setMessage(name + "...")
        .setCancelOutside(false);
    loading = builder.create();
    loading.setOnKeyListener(new DialogInterface.OnKeyListener() {
      @Override
      public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
          loading.dismiss();
          finish();
        }
        return false;
      }
    });

    if (!loading.isShowing() && isValidContext(context))
      loading.show();
  }

  public void dissLoading() {
    if (context != null && !((Activity) context).isFinishing() &&
        loading != null && loading.isShowing() && isValidContext(context))
      loading.dismiss();
  }

  private boolean isValidContext(Context c) {

    Activity a = (Activity) c;

    if (a.isDestroyed() || a.isFinishing()) {
      return false;
    } else {
      return true;
    }
  }

  protected abstract void loadContentLayout();

  protected abstract void initView();

  protected abstract void setListener();
}
