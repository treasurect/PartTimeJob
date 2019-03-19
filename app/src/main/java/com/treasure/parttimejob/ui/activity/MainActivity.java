package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.helper.DataChangeEvent;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.fragment.HomeFragment;
import com.treasure.parttimejob.ui.fragment.MineBossFragment;
import com.treasure.parttimejob.ui.fragment.MineFragment;
import com.treasure.parttimejob.ui.fragment.MsgFragment;
import com.treasure.parttimejob.helper.FinishEvent;
import com.treasure.parttimejob.ui.model.PartTimeBean;
import com.treasure.parttimejob.utils.LogUtil;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

  public static void start(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    context.startActivity(intent);
  }

  @BindView(R.id.sports_img)
  ImageView recommendImg;
  @BindView(R.id.recommend_txt)
  TextView recommendText;
  @BindView(R.id.find_img)
  ImageView followImg;
  @BindView(R.id.follow_txt)
  TextView followText;
  @BindView(R.id.mine_img)
  ImageView mineImg;
  @BindView(R.id.mine_txt)
  TextView mineText;

  private HomeFragment homeFragment;
  private MsgFragment recommendFragment;
  private MineFragment mineFragment;
  private MineBossFragment mineBossFragment;
  private Fragment lastFragment;
  private FragmentManager fm;
  private int lastId;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_main);
  }

  @Override
  protected void initView() {
    EventBus.getDefault().register(this);
    //是否已经初始化过数据
    boolean isInit = SharedPreferencesUtil.getInstance().getBoolean(Contacts.INIT_DATA, false);
    if (!isInit)
      initData();
    homeFragment = new HomeFragment();
    fm = getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    transaction.add(R.id.container, homeFragment, "HomeFragment").commit();
    lastFragment = homeFragment;

    recommendImg.setSelected(true);
    recommendText.setSelected(true);
    lastId = recommendImg.getId();

    AndPermission.with(this)
        .requestCode(200)
        .permission(Permission.MICROPHONE, Permission.LOCATION)
        .callback(listener)
        .start();
  }

  //初始化虚拟数据
  private void initData() {
    PJApp.userDaoManager.deletePartAll();

    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552230727267&di=16ada90baecb95fafdf420319404707b&imgtype=0&src=http%3A%2F%2Fs9.knowsky.com%2Fbizhi%2Fl%2F1-5000%2F2009528163710676869630.jpg";
    String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552230727267&di=416554c099900bd185f9bbc6ee35f4b2&imgtype=0&src=http%3A%2F%2Fs9.knowsky.com%2Fbizhi%2Fl%2F1-5000%2F2009528135754177631020.jpg";
    String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552230727266&di=9429a534cfa8c86d735ea44a860e0ca0&imgtype=0&src=http%3A%2F%2Fs9.knowsky.com%2Fbizhi%2Fl%2F1-5000%2F200952811371467217027.jpg";
    String content0 = "工作职责：负责酒店夜班值日，工作轻快";
    String content1 = "岗位职责：热爱摄影职业\n任职资格：有婚礼摄影经验\n工作时间：7小时内";
    String content2 = "岗位职责：热爱书法小提琴\n任职资格：有书法小提琴工作经验\n工作时间：2小时/天";
    String content3 = "岗位职责：热爱工作\n任职资格：本科学历以上\n工作时间：8小时/天";
    for (int i = 0; i < 3; i++) {
      PartTimeBean partTimeBean0 = new PartTimeBean((long) i, "服务员夜班", "长期",
          content0, "北京朝阳门",  39.9306586016,  116.4411194435, "100元/天", "男女不限",
          "日结", "实习", (long) i, "在招", url, url2, "广州恒泰", "17611223456", false, 0,0f);
      PartTimeBean partTimeBean1 = new PartTimeBean((long) (i + 10), "兼职婚礼摄影师", "短期",
          content1, "北京建国门", 39.9142947315, 116.4422971045, "500元/天",
          "男女不限", "日结", "技能", (long) (i + 10), "在招", url2,
          url, "广州恒泰", "17611223456", false, 0,0f);
      PartTimeBean partTimeBean2 = new PartTimeBean((long) i + 20, "书法小提琴老师", "长期",
          content2, "北京西直门", 39.9462354501,  116.3620972899, "150元/小时",
          "男女不限", "月结", "技能", (long) (i + 20), "在招", url3, url,
          "广州恒泰", "17611223456", false, 0,0f);
      PartTimeBean partTimeBean3 = new PartTimeBean((long) i + 30, "公司实习生招聘", "长期",
          content3, "北京东直门", 39.9475882759, 116.4409399693, "250元/天", "男女不限", "月结",
          "实习", (long) (i + 30), "在招", url3, url, "广州恒泰", "17611223456", false, 0,0f);

      PJApp.userDaoManager.insertPart(partTimeBean0);
      PJApp.userDaoManager.insertPart(partTimeBean1);
      PJApp.userDaoManager.insertPart(partTimeBean2);
      PJApp.userDaoManager.insertPart(partTimeBean3);
    }

    SharedPreferencesUtil.getInstance().putBoolean(Contacts.INIT_DATA,true);
  }

  @Override
  protected void setListener() {

  }

  @OnClick({R.id.sports_layout, R.id.find_layout, R.id.mine_layout})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.sports_layout:
        if (homeFragment == null) {
          homeFragment = new HomeFragment();
        }

        if (lastFragment != homeFragment) {
          FragmentTransaction fragmentTransaction = fm.beginTransaction();
          if (!homeFragment.isAdded()) {
            fragmentTransaction.add(R.id.container, homeFragment, "HomeFragment").hide(lastFragment).commit();
          } else {
            fragmentTransaction.show(homeFragment).hide(lastFragment).commit();
          }

          lastFragment = homeFragment;
        }
        changeIcon(R.id.sports_img);

        break;
      case R.id.find_layout:
        if (recommendFragment == null) {
          recommendFragment = new MsgFragment();
        }

        if (lastFragment != recommendFragment) {
          FragmentTransaction fragmentTransaction = fm.beginTransaction();
          if (!recommendFragment.isAdded()) {
            fragmentTransaction.add(R.id.container, recommendFragment, "MsgFragment").hide(lastFragment).commit();
          } else {
            fragmentTransaction.show(recommendFragment).hide(lastFragment).commit();
          }

          lastFragment = recommendFragment;
        }
        changeIcon(R.id.find_img);
        break;
      case R.id.mine_layout:
        if (SharedPreferencesUtil.getInstance().getString(Contacts.USER_IDENTITY).equals("personal")){
          if (mineFragment == null) {
            mineFragment = new MineFragment();
          }
          if (lastFragment != mineFragment) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            if (!mineFragment.isAdded()) {
              fragmentTransaction.add(R.id.container, mineFragment, "MineFragment").hide(lastFragment).commit();
            } else {
              fragmentTransaction.show(mineFragment).hide(lastFragment).commit();
            }

            lastFragment = mineFragment;
          }
        }else {
          if (mineBossFragment == null) {
            mineBossFragment = new MineBossFragment();
          }
          if (lastFragment != mineBossFragment) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            if (!mineBossFragment.isAdded()) {
              fragmentTransaction.add(R.id.container, mineBossFragment, "MineBossFragment").hide(lastFragment).commit();
            } else {
              fragmentTransaction.show(mineBossFragment).hide(lastFragment).commit();
            }

            lastFragment = mineBossFragment;
          }
        }

        changeIcon(R.id.mine_img);
        break;
      case R.id.container:
        break;
    }

  }

  /**
   * 改变底部按钮颜色
   */
  public void changeIcon(int selectedId) {
    if (selectedId != lastId) {
      switch (selectedId) {
        case R.id.sports_img:
          changeTxt(recommendImg, recommendText);
          break;
        case R.id.find_img:
          changeTxt(followImg, followText);
          break;
        case R.id.mine_img:
          changeTxt(mineImg, mineText);
          break;
      }
    }
  }

  private void changeTxt(ImageView imageView, TextView textView) {
    imageView.setSelected(true);
    textView.setSelected(true);
    switch (lastId) {
      case R.id.sports_img:
        noSelected(recommendImg, recommendText);
        break;
      case R.id.find_img:
        noSelected(followImg, followText);
        break;
      case R.id.mine_img:
        noSelected(mineImg, mineText);
        break;
    }
    lastId = imageView.getId();
  }

  private void noSelected(ImageView imageView, TextView textView) {
    imageView.setSelected(false);
    textView.setSelected(false);
  }

  private PermissionListener listener = new PermissionListener() {
    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
      if (requestCode == 200) {


      }
    }

    @Override
    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
      if (requestCode == 200) {

      }

    }
  };

  /**
   * EventBus 接收方法
   * @param event
   */
  @Subscribe
  public void onEventMainThread(FinishEvent event){
    finish();
  }
  @Subscribe
  public void onDataChange(DataChangeEvent event){
    if (event.getType().equals("home")){
      if (homeFragment != null){
        homeFragment.onDataChange();
      }
    }else if (event.getType().equals("msg")){
      if (recommendFragment != null){
        recommendFragment.onDataChange();
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
