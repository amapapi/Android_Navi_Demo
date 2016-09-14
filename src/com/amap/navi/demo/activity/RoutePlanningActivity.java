package com.amap.navi.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.navi.demo.R;
import com.amap.navi.demo.util.TTSController;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;


public class RoutePlanningActivity extends Activity implements AMapNaviListener, View.OnClickListener {


    // 起点、终点坐标显示
    private EditText startInput;
    private EditText endInput;
    // 驾车线路：路线规划、模拟导航、实时导航按钮
    private Button mDriveRouteButton;
    // 步行线路：路线规划、模拟导航、实时导航按钮
    private Button mFootRouteButton;
    // 地图和导航资源
    private MapView mMapView;
    private AMap mAMap;

    // 起点终点坐标
    private NaviLatLng mNaviStart = new NaviLatLng(39.989614, 116.481763);
    private NaviLatLng mNaviEnd = new NaviLatLng(39.983456, 116.3154950);
    // 起点终点列表
    private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
    private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();

    // 规划线路
    private RouteOverLay mRouteOverLay;
    private TTSController ttsManager;
    private AMapNavi aMapNavi;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ttsManager = TTSController.getInstance(this);
        ttsManager.init();

        aMapNavi = AMapNavi.getInstance(this);
        aMapNavi.addAMapNaviListener(this);
        aMapNavi.addAMapNaviListener(ttsManager);
        aMapNavi.setEmulatorNaviSpeed(150);


        setContentView(R.layout.activity_route_planning);
        initView(savedInstanceState);
    }

    // 初始化View
    private void initView(Bundle savedInstanceState) {

        startInput = (EditText) findViewById(R.id.start_position_textview);
        endInput = (EditText) findViewById(R.id.end_position_textview);


        startInput.setText(mNaviStart.getLatitude() + ","
                + mNaviStart.getLongitude());
        endInput.setText(mNaviEnd.getLatitude() + ","
                + mNaviEnd.getLongitude());

        mDriveRouteButton = (Button) findViewById(R.id.car_navi_route);
        mFootRouteButton = (Button) findViewById(R.id.foot_navi_route);

        mDriveRouteButton.setOnClickListener(this);
        mFootRouteButton.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mRouteOverLay = new RouteOverLay(mAMap, null,getApplicationContext());
    }

    //计算驾车路线
    private void calculateDriveRoute() {
        mNaviStart = parseEditText(startInput.getText().toString());
        mNaviEnd = parseEditText(endInput.getText().toString());
        mStartPoints.clear();
        mEndPoints.clear();
        mStartPoints.add(mNaviStart);
        mEndPoints.add(mNaviEnd);

        boolean isSuccess = aMapNavi.calculateDriveRoute(mStartPoints,
                mEndPoints, null, PathPlanningStrategy.DRIVING_DEFAULT);
        if (!isSuccess) {
            showToast("路线计算失败,检查参数情况");
        }

    }

    private NaviLatLng parseEditText(String text) {
        try {
            double latD = Double.parseDouble(text.split(",")[0]);
            double lonD = Double.parseDouble(text.split(",")[1]);

            return new NaviLatLng(latD, lonD);


        } catch (Exception e) {
            Toast.makeText(this, "e:" + e, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "格式:[lat],[lon]", Toast.LENGTH_SHORT).show();
        }


        return null;
    }

    //计算步行路线
    private void calculateFootRoute() {
        mNaviStart = parseEditText(startInput.getText().toString());
        mNaviEnd = parseEditText(endInput.getText().toString());
        boolean isSuccess = aMapNavi.calculateWalkRoute(mNaviStart, mNaviEnd);
        if (!isSuccess) {
            showToast("路线计算失败,检查参数情况");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //-------------------------Button点击事件和返回键监听事件---------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_navi_route:
                calculateDriveRoute();
                break;
            case R.id.foot_navi_route:
                calculateFootRoute();
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }

    //--------------------导航监听回调事件-----------------------------
    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onArriveDestination(NaviStaticInfo naviStaticInfo) {

    }

    @Override
    public void onArrivedWayPoint(int arg0) {

    }

    @Override
    public void onCalculateRouteFailure(int arg0) {
        showToast("路径规划出错" + arg0);
    }

    @Override
    public void onCalculateRouteSuccess() {
        AMapNaviPath naviPath = aMapNavi.getNaviPath();
        if (naviPath == null) {
            return;
        }
        // 获取路径规划线路，显示到地图上
        mRouteOverLay.setAMapNaviPath(naviPath);
        mRouteOverLay.addToMap();
    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {

    }

    @Override
    public void onGpsOpenStatus(boolean arg0) {

    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo arg0) {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onStartNavi(int arg0) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

//------------------生命周期重写函数---------------------------

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mStartPoints.add(mNaviStart);
        mEndPoints.add(mNaviEnd);

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        aMapNavi.destroy();
        ttsManager.destroy();
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo arg0) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

}
