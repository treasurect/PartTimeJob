package com.treasure.parttimejob.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.GenderChangeListener;
import com.treasure.parttimejob.utils.ScreenUtil;


/**
 * Created by 18410 on 2017/8/24.
 */

public class GenderChangeDialog extends Dialog {
    private GenderChangeListener listener;
    private Context context;

    public GenderChangeDialog(@NonNull Context context) {
        super(context, R.style.gender_change);
        this.context = context;
    }


    public void setListener(GenderChangeListener listener){
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_genderchange);
        init();
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.gender_change);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ScreenUtil.getScreenWidth(context);
        attributes.height = ScreenUtil.dip2px(context,240);
        window.setAttributes(attributes);
    }

    private void init() {
        findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.genderClick(R.id.man);
            }
        });
        findViewById(R.id.women).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.genderClick(R.id.women);
            }
        });
        findViewById(R.id.unkonow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.genderClick(R.id.unkonow);
            }
        });
        findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.genderClick(R.id.cancle);
            }
        });

    }
}
