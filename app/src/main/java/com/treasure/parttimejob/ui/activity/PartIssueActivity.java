package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.helper.DataChangeEvent;
import com.treasure.parttimejob.helper.GenderChangeListener;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.model.MsgBean;
import com.treasure.parttimejob.ui.model.PartTimeBean;
import com.treasure.parttimejob.ui.view.AlertDialog;
import com.treasure.parttimejob.utils.GlideUtils;
import com.treasure.parttimejob.utils.LogUtil;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PartIssueActivity extends BaseActivity implements BDLocationListener {

  public static void start(Context context) {
    Intent intent = new Intent(context, PartIssueActivity.class);
    context.startActivity(intent);
  }

  @BindView(R.id.background)
  ImageView backGround;
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.edit_title)
  EditText editTitle;
  @BindView(R.id.edit_price)
  EditText editPrice;
  @BindView(R.id.edit_clearing)
  EditText editClearing;
  @BindView(R.id.edit_type)
  EditText editType;
  @BindView(R.id.edit_duration)
  EditText editDuration;
  @BindView(R.id.edit_sex)
  EditText editSex;
  @BindView(R.id.edit_detail)
  EditText editDetail;

  String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552230727267&di=416554c099900bd185f9bbc6ee35f4b2&imgtype=0&src=http%3A%2F%2Fs9.knowsky.com%2Fbizhi%2Fl%2F1-5000%2F2009528135754177631020.jpg";
  public LocationClient mLocationClient = null;
  private double user_latitude = 0,user_longitude = 0;
  private String address="北京";

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_part_issue);
  }

  @Override
  protected void initView() {
    title.setText("兼职发布");
    GlideUtils.loadImg(this, url, backGround, R.mipmap.pic_welcome);
    initLocation();
  }

  @Override
  protected void setListener() {

  }

  @OnClick({R.id.back, R.id.issue})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.issue:
        String title = editTitle.getText().toString().trim();
        String price = editPrice.getText().toString().trim();
        String clearing = editClearing.getText().toString().trim();
        String type = editType.getText().toString().trim();
        String duration = editDuration.getText().toString().trim();
        String sex = editSex.getText().toString().trim();
        String detail = editDetail.getText().toString().trim();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(price) || TextUtils.isEmpty(clearing) ||
            TextUtils.isEmpty(duration) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(detail)||TextUtils.isEmpty(type)){
          ToastUtils.showSingleToast("请填写完整");
          return;
        }
        SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();
        PartTimeBean partTimeBean = new PartTimeBean(System.currentTimeMillis(), title, duration,
            detail, address, user_latitude,  user_longitude, price, sex, clearing, type, System.currentTimeMillis(),
            "在招", url, instance.getString(Contacts.USER_IMAGE), instance.getString(Contacts.USER_NAME),
            instance.getString(Contacts.USER_NAME), false, 0,0f);
        PJApp.userDaoManager.insertPart(partTimeBean);
        ToastUtils.showSingleToast("发布成功");
        MsgBean msgBean = new MsgBean(System.currentTimeMillis(), instance.getString(Contacts.USER_NAME),
            instance.getString(Contacts.USER_IMAGE), partTimeBean.getPart_name(), "恭喜您，已成功发布职位");
        PJApp.userDaoManager.insertMsg(msgBean);
        EventBus.getDefault().post(new DataChangeEvent("home"));
        EventBus.getDefault().post(new DataChangeEvent("msg"));
        finish();
        break;
    }
  }
  private void initLocation() {
    mLocationClient = new LocationClient(getApplicationContext());     //定位初始化
    mLocationClient.registerLocationListener(this);//定位注册

    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
    option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
    option.setScanSpan(2000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
    option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
    option.setOpenGps(true);//可选，默认false,设置是否使用gps
    option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
    option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
    option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
    option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
    option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
    option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
    option.setNeedDeviceDirect(true);
    mLocationClient.setLocOption(option);
  }

  @Override
  public void onReceiveLocation(BDLocation bdLocation) {
    int locType = bdLocation.getLocType();
    switch (locType) {
      case BDLocation.TypeCacheLocation:
      case BDLocation.TypeOffLineLocation:
      case BDLocation.TypeGpsLocation:
      case BDLocation.TypeNetWorkLocation:

        user_latitude = bdLocation.getLatitude();
        user_longitude = bdLocation.getLongitude();
        String city = bdLocation.getCity();
        String district = bdLocation.getDistrict();
        if (TextUtils.isEmpty(district)){
          address = city;
        }else{
          address = district;
        }
        break;
      default:
        LogUtil.e("~~~~~~~~~~~~~定位失败：" + locType);
        break;
    }
  }
  @Override
  public void onStart() {
    super.onStart();
    if (!mLocationClient.isStarted()) {
      mLocationClient.start();//开启定位
    }
  }
  @Override
  public void onStop() {
    super.onStop();
    mLocationClient.stop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mLocationClient != null){
      mLocationClient.unRegisterLocationListener(this);
    }
  }
}
