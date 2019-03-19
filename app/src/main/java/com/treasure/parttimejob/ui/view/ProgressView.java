package com.treasure.parttimejob.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.treasure.parttimejob.helper.OnFinishListener;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/2/26
 * description:
 * ============================================================
 */
public class ProgressView extends View {

  private RectF rectF;
  private Paint paint;

  //进度条圆弧宽度
  private int strokeWidth = 20;
  //初始进度
  private float startPro;
  //最大进度
  private float maxPro = 100;

  public ProgressView(Context context) {
    super(context);
    init();
  }

  public ProgressView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    rectF = new RectF();
    paint = new Paint();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int width = getWidth();
    int height = getHeight();
    if (width != height){
      int min = Math.min(width, height);
      width = min;
      height = min;
    }

    //位置
    rectF.left = strokeWidth/2;
    rectF.top = strokeWidth/2;
    rectF.right = width - strokeWidth/2;
    rectF.bottom = height -strokeWidth/2;

    //透明背景
    canvas.drawColor(Color.TRANSPARENT);

    //圆形背景
    paint.setColor(Color.parseColor("#33000000"));
    canvas.drawOval(rectF,paint);

    //进度条
    paint.setAntiAlias(true);
    paint.setColor(Color.parseColor("#FF0000"));
    paint.setStyle(Paint.Style.STROKE);
    canvas.drawArc(rectF,-90,-(startPro/maxPro)*360,false,paint);

//    paint.setColor(Color.parseColor("#000000"));
//    paint.setTextSize(15);
//    canvas.drawText("1s",0,0,paint);
  }

  public void startCountDown(final long time, final OnFinishListener listener){
    maxPro = 100;
    new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = (int)maxPro; i >=0; i--) {
          try {
            Thread.sleep((long) (time/maxPro));
            if (i == 0 && listener != null){
              listener.onFinish();
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          startPro = (float)i;
          ProgressView.this.postInvalidate();
        }
      }
    }).start();
  }
}
