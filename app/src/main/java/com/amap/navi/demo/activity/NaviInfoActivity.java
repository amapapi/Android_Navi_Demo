package com.amap.navi.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;


public class NaviInfoActivity extends Activity implements AMapNaviListener, AMapNaviViewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LinearLayout layout = new LinearLayout(this);
        layout.setGravity(Gravity.CENTER);
        TextView textView = new TextView(this);
        textView.setText("这是个空页面，请您看NaviInfoActivity.java的注释说明。");
        layout.addView(textView);
        setContentView(layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "初始化失败。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        //初始化成功回调
    }

    @Override
    public void onStartNavi(int type) {
        //开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {
        //交通信息更新回调
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前车位置回调。

        /**
         * 回调对象说明，请参见官网API文档，http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Navi_Doc/index.html
         **/
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //语音播报回调,调用科大讯飞语音合成接口，将需要语音播报的text传入。

    }

    @Override
    public void onEndEmulatorNavi() {
        //停止模拟导航回调
    }

    @Override
    public void onArriveDestination() {
        //到达目的地回调
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路径计算失败
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航重算回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵重算回调
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviSetting() {
        //点击导航界面的Setting按钮回调
    }

    @Override
    public void onNaviMapMode(int isLock) {
        //导航界面地图状态的回调。
        //isLock 地图状态，0:车头朝上状态；1:非锁车状态,即车标可以任意显示在地图区域内。
    }

    @Override
    public void onNaviCancel() {
    }


    @Override
    public void onNaviTurnClick() {
        //界面左上角转向操作的点击回调。
    }

    @Override
    public void onNextRoadClick() {
        //界面下一道路名称的点击回调。
    }


    @Override
    public void onScanViewButtonClick() {
        //通知APP全览按钮被点击了
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //废弃方法，不会回调。
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航信息更新回调

        /**
         * 回调对象说明，请参见官网API文档，http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Navi_Doc/index.html
         **/
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //1.8.0开始，不再回调该方法
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //1.8.0开始，不再回调该方法
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //通知APP显示转弯信息。


        /**
         * 回调对象说明，请参见官网API文档，http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Navi_Doc/index.html
         **/
    }

    @Override
    public void hideCross() {
        //通知APP隐藏转弯信息。
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //通知APP显示车道信息。

        /**
         * 回调对象说明，请参见官网API文档，http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Navi_Doc/index.html
         **/
    }

    @Override
    public void hideLaneInfo() {
        //通知APP隐藏车道信息。
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //多路线计算成功
    }

    @Override
    public void notifyParallelRoad(int i) {
        //主辅路发生变化更新此方法
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //巡航模式（无路线规划）下，道路设施信息更新回调

        /**
         * 回调对象说明，请参见官网API文档，http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Navi_Doc/index.html
         **/
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //巡航模式（无路线规划）下，统计信息更新回调,连续5个点大于15km/h后开始回调

        /**
         * 回调对象说明，请参见官网API文档，http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Navi_Doc/index.html
         **/
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //巡航模式（无路线规划）下，统计信息更新回调,当拥堵长度大于500米且拥堵时间大于5分钟时回调

        /**
         * 回调对象说明，请参见官网API文档，http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Navi_Doc/index.html
         **/
    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onLockMap(boolean isLock) {
        //当前地图锁定发生改变回调此方法，true表示地图是锁定状态。
    }

    @Override
    public void onNaviViewLoaded() {
        //导航页面加载成功
        //请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑
    }

    @Override
    public boolean onNaviBackClick() {
        //return true表示使用SDK的返回对话框。
        return false;
    }


}
