package com.treasure.parttimejob.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.GenderChangeListener;

/**
 * Created by 18410 on 2017/8/25.
 */

public class AlertDialog extends Dialog {
  private GenderChangeListener listener;
  private Context context;
  private TextView titleTxt, doneTxt;

  public AlertDialog(@NonNull Context context) {
    super(context, R.style.gender_change);
    this.context = context;
  }

  public void setListener(GenderChangeListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog_alert);
    init();
    Window window = getWindow();
    window.setGravity(Gravity.CENTER);
    window.setWindowAnimations(R.style.gender_change);
    titleTxt = (TextView) findViewById(R.id.title);
    doneTxt = (TextView) findViewById(R.id.done);
//        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.width = ScreenUtil.getScreenWidth(context);
//        attributes.height = ScreenUtil.dip2px(context,180);
//        attributes.horizontalMargin = 100;
//        window.setAttributes(attributes);
  }

  private void init() {
    findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.genderClick(R.id.done);
      }
    });

  }

  public void setContent(String title, String done) {
    if (titleTxt != null) titleTxt.setText(title);
    if (doneTxt != null) doneTxt.setText(done);

  }
}
