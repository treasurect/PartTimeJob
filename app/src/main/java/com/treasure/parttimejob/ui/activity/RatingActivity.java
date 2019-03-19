package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.model.PartTimeBean;

import butterknife.BindView;
import butterknife.OnClick;

public class RatingActivity extends BaseActivity {

  private int type;
  private long id;

  //0 评价   1查看评价
  public static void start(Context context, int type, Long id) {
    Intent intent = new Intent(context, RatingActivity.class);
    intent.putExtra("type", type);
    intent.putExtra("id", id);
    context.startActivity(intent);
  }

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.rating_bar)
  RatingBar ratingBar;
  @BindView(R.id.apply)
  TextView apply;
  @BindView(R.id.edit)
  TextView edit;
  private float rating;
  private PartTimeBean partTimeBean;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_rating);
  }

  @Override
  protected void initView() {
    Intent intent = getIntent();
    if (intent != null) {
      type = intent.getIntExtra("type", 0);
      id = intent.getLongExtra("id", 0);
    }
    partTimeBean = PJApp.userDaoManager.queryIdPart(id);
    if (type == 0){
      title.setText("评价");
      apply.setVisibility(View.VISIBLE);
      ratingBar.setRating(partTimeBean.getRating());
    }else {
      title.setText("查看评价");
      apply.setVisibility(View.GONE);
      ratingBar.setRating(partTimeBean.getRating());
      ratingBar.setEnabled(false);
      edit.setVisibility(View.GONE);
    }
  }

  @Override
  protected void setListener() {
    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        RatingActivity.this.rating = rating;
      }
    });
  }

  @OnClick({R.id.back, R.id.apply})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.apply:
        applyRating();
        break;
    }
  }

  private void applyRating() {
    partTimeBean.setRating(rating);
    PJApp.userDaoManager.updatePart(partTimeBean);
    finish();
  }
}
