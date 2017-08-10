package com.amap.navi.demo.activity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.navi.demo.R;

public class UseExtraGpsDataActivity extends BaseActivity {


    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setSettingMenuEnabled(true);
        mAMapNaviView.setViewOptions(options);

        mAMapNaviView.setAMapNaviViewListener(this);

        //使用外部GPS数据
        mAMapNavi.setIsUseExtraGPSData(true);

        Toast.makeText(this, "点击右下角menu按钮实现设置外部GPS", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNaviSetting() {
        super.onNaviSetting();
        setExtraGPSData();
    }

    public void setExtraGPSData() {


        Location location = new Location("gps仪器型号");
        location.setLongitude(116.4 - 0.01 * i);
        location.setLatitude(39.9);
        location.setSpeed(5);
        location.setAccuracy(1);
        location.setBearing(5);
        location.setTime(System.currentTimeMillis());

        //以上6项数据缺一不可!!!
        mAMapNavi.setExtraGPSData(location);

        Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show();

        i++;
    }


    @Override
    public void onInitNaviSuccess() {
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, PathPlanningStrategy.DRIVING_DEFAULT);
    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
