package com.amap.navi.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.navi.demo.util.TTSController;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;
import java.util.List;


public class NaviInfoActivity extends Activity implements AMapNaviListener, AMapNaviViewListener {

    protected AMapNaviView mAMapNaviView;
    protected AMapNavi mAMapNavi;
    protected TTSController mTtsManager;
    protected NaviLatLng mEndLatlng = new NaviLatLng(39.925846, 116.432765);
    protected NaviLatLng mStartLatlng = new NaviLatLng(39.925041, 116.437901);
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    protected List<NaviLatLng> mWayPointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //实例化语音引擎
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();

        //
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);

        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();

//        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        mTtsManager.stopSpeaking();
//
//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
//        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
        mTtsManager.destroy();
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
        //语音播报回调

    }

    @Override
    public void onEndEmulatorNavi() {
        //停止模拟导航回调
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onArriveDestination(NaviStaticInfo naviStaticInfo) {

    }

    @Override
    public void onCalculateRouteSuccess() {
        //路径计算成功
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
        finish();
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
    public void onCalculateMultipleRoutesSuccess(int[] ints) {
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
    public void onLockMap(boolean isLock) {
        //当前地图锁定发生改变回调此方法，true表示地图是锁定状态。
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public boolean onNaviBackClick() {
        //return true表示使用SDK的返回对话框。
        return false;
    }


}
