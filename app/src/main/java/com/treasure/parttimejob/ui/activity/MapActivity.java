package com.treasure.parttimejob.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.MapOrientationListener;
import com.treasure.parttimejob.ui.base.BaseActivity;
import com.treasure.parttimejob.ui.model.PartTimeBean;
import com.treasure.parttimejob.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseActivity implements BDLocationListener, OnMapLoadedCallback {

  public static void start(Context context, double latitude, double longitude){
    Intent intent = new Intent(context, MapActivity.class);
    intent.putExtra("latitude",latitude);
    intent.putExtra("longitude",longitude);
    context.startActivity(intent);
  }
  @BindView(R.id.bmapView)
  MapView mapView;
  @BindView(R.id.back)
  ImageView back;
  @BindView(R.id.title)
  TextView title;

  private BaiduMap map;
  private double user_latitude = 39.915168, user_longitude = 116.403875;
  private float radius = 0;
  public LocationClient mLocationClient = null;
  private float mCurrentX;
  private BitmapDescriptor marker_bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker);
  private double latitude,longitude;

  @Override
  protected void loadContentLayout() {
    setContentView(R.layout.activity_map);
  }

  @Override
  protected void initView() {
    title.setText("商家地址");

    Intent intent = getIntent();
    if (intent != null){
      latitude = intent.getDoubleExtra("latitude", 0);
      longitude = intent.getDoubleExtra("longitude", 0);
    }
    useLocationOrientationListener();
    initMap();
    initLocation();
  }

  @Override
  protected void setListener() {

  }

  private void useLocationOrientationListener() {
    MapOrientationListener orientationListener = new MapOrientationListener(this);
    orientationListener.start();
    orientationListener.setMapOrientationListener(new MapOrientationListener.onOrientationListener() {
      @Override
      public void onOrientationChanged(float x) {//监听方向的改变，方向改变时，需要得到地图上方向图标的位置
        mCurrentX = x;
      }
    });
  }

  private void initMap() {
    // 隐藏百度的LOGO
//        View child = mapView.getChildAt(1);
//        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
//            child.setVisibility(View.INVISIBLE);
//        }
//        mapView.showScaleControl(false);// 不显示地图上比例尺
//        mapView.showZoomControls(false);// 不显示地图缩放控件（按钮控制栏）
    map = mapView.getMap();
    map.setIndoorEnable(true);//开启室内图
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
    map.setOnMapLoadedCallback(this);
  }

  @OnClick({R.id.btn_location,R.id.back})
  public void onViewClick(View view) {
    switch (view.getId()) {
      case R.id.btn_location:
        showUserLocation();
        break;
      case R.id.back:
        finish();
        break;
    }
  }

  //定位到用户当前位置
  private void showUserLocation() {
    LatLng latLng = new LatLng(user_latitude, user_longitude);
    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
    map.animateMapStatus(msu);
  }

  //添加标注
  public void addOverlay(double latitude,double longitude) {
    OverlayOptions overlayoptions = null;
    Marker marker = null;

    overlayoptions = new MarkerOptions()//
        .position(new LatLng(latitude,longitude))// 设置marker的位置
        .icon(marker_bitmap)// 设置marker的图标
        .animateType(MarkerOptions.MarkerAnimateType.grow)
        .zIndex(9);// 設置marker的所在層級
    marker = (Marker) map.addOverlay(overlayoptions);

//    Bundle bundle = new Bundle();
//    bundle.putSerializable("marker", info);
//    marker.setExtraInfo(bundle);
  }

  @Override
  public void onMapLoaded() {
    mapView.postDelayed(new Runnable() {
      @Override
      public void run() {
        showUserLocation();
        addOverlay(latitude,longitude);
      }
    }, 2000);
  }


  @Override
  public void onReceiveLocation(BDLocation bdLocation) {
    int locType = bdLocation.getLocType();
    switch (locType) {
      case BDLocation.TypeCacheLocation:
      case BDLocation.TypeOffLineLocation:
      case BDLocation.TypeGpsLocation:
      case BDLocation.TypeNetWorkLocation:

        radius = bdLocation.getRadius();
        user_latitude = bdLocation.getLatitude();
        user_longitude = bdLocation.getLongitude();

        MyLocationData data = new MyLocationData.Builder()
            .accuracy(radius)
            .direction(mCurrentX)
            .latitude(user_latitude)
            .longitude(user_longitude)
            .build();
        map.setMyLocationData(data);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null);
        map.setMyLocationConfigeration(config);
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
  public void onResume() {
    super.onResume();
    map.setMyLocationEnabled(true);
    mapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    map.setMyLocationEnabled(false);
    mapView.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    mLocationClient.stop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mapView != null){
      mapView.onDestroy();
    }
    if (mLocationClient != null){
      mLocationClient.unRegisterLocationListener(this);
    }
  }
}
