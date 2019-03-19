package com.treasure.parttimejob.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.treasure.parttimejob.R;

/**
 * Created by Yang on 2015/8/12 012.
 */
public class GlideUtils {

  /**
   * Glide圆角处理
   */
  public static class GlideRoundTransform extends BitmapTransformation {
    private float radius = 0f;

    public GlideRoundTransform(Context context) {
      this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {
      super(context);
      this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
      return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
      if (source == null) return null;

      Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
      if (result == null) {
        result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
      }

      Canvas canvas = new Canvas(result);
      Paint paint = new Paint();
      paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
      paint.setAntiAlias(true);
      RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
      canvas.drawRoundRect(rectF, radius, radius, paint);
      return result;
    }

    @Override
    public String getId() {
      return getClass().getName() + Math.round(radius);
    }
  }


  //图片圆角处理
  public static class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
      super(context);
    }

    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
      return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
      if (source == null) return null;
      int size = Math.min(source.getWidth(), source.getHeight());
      int x = (source.getWidth() - size) / 2;
      int y = (source.getHeight() - size) / 2;
      // TODO this could be acquired from the pool too
      Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
      Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
      if (result == null) {
        result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
      }
      Canvas canvas = new Canvas(result);
      Paint paint = new Paint();
      paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
      paint.setAntiAlias(true);
      float r = size / 2f;
      canvas.drawCircle(r, r, r, paint);
      return result;
    }

    @Override
    public String getId() {
      return getClass().getName();
    }
  }

  /**
   * Glide 加载带圆角
   *
   * @param context
   * @param url           加载图片的url地址  String
   * @param imageView     加载图片的ImageView 控件
   * @param default_image 图片展示错误的本地图片 id
   */
  public static void loadRoundImg(Context context, String url, ImageView imageView, int default_image, int dp) {
    if (context != null) {
      Glide.with(context).load(url).centerCrop().error(R.mipmap.ic_launcher_logo).crossFade
          ().diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideUtils.GlideRoundTransform(context, dp)).into(imageView);
    } else {
      LogUtil.e("~~~~~~~~~~loadImageRoundSyc:" + "Picture loading failed,context is null");
    }
  }

  /**
   * Glide  加载原图
   *
   * @param context
   * @param url
   * @param imageView
   * @param default_image
   */
  public static void loadCircleImg(Context context, String url, ImageView imageView, int default_image) {
    if (context != null) {
      Glide.with(context).load(url).centerCrop().error(default_image).crossFade
          ().placeholder(default_image).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(context)).into(imageView);
    } else {
      LogUtil.e("~~~~~~~~~~loadCircleImg:" + "Picture loading failed,context is null");
    }
  }

  public static void loadImg(Context context, String url, ImageView imageView, int default_image) {
    if (context != null) {
      Glide.with(context).load(url).centerCrop().error(default_image).crossFade
          ().placeholder(default_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    } else {
      LogUtil.e("~~~~~~~~~~loadCircleImg:" + "Picture loading failed,context is null");
    }
  }
}
