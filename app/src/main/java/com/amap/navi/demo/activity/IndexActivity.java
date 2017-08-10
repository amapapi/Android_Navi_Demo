package com.amap.navi.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.navi.demo.R;
import com.amap.navi.demo.activity.custom.CustomUiActivity;
import com.amap.navi.demo.util.AmapTTSController;
import com.amap.navi.demo.util.CheckPermissionsActivity;
import com.amap.navi.demo.util.SpeechUtils;

/**
 * Created by shixin on 16/8/23.
 * bug反馈QQ:1438734562
 */
public class IndexActivity extends CheckPermissionsActivity implements INaviInfoCallback {

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {//有终点
                LatLng epoint = new LatLng(39.935039, 116.492446);
                Poi epoi = new Poi("乐视大厦", epoint, "");
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(epoi), IndexActivity.this);
            } else if (position == 1) {//HUD导航
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(null), IndexActivity.this);
            } else if (position == 2) {//驾车
                startActivity(new Intent(IndexActivity.this, DriverListActivity.class));
            } else if (position == 3) {//步行路线规划
                startActivity(new Intent(IndexActivity.this, WalkRouteCalculateActivity.class));
            } else if (position == 4) {//骑行路线规划
                startActivity(new Intent(IndexActivity.this, RideRouteCalculateActivity.class));
            } else if (position == 5) {//实时导航
                startActivity(new Intent(IndexActivity.this, GPSNaviActivity.class));
            } else if (position == 6) {//模拟导航
                startActivity(new Intent(IndexActivity.this, EmulatorActivity.class));
            } else if (position == 7) {//只能巡航
                startActivity(new Intent(IndexActivity.this, IntelligentBroadcastActivity.class));
            } else if (position == 8) {//使用设备外GPS数据导航
                startActivity(new Intent(IndexActivity.this, UseExtraGpsDataActivity.class));
            } else if (position == 9) {//导航UI在自定义
                startActivity(new Intent(IndexActivity.this, CustomUiActivity.class));
            } else if (position == 10) {//HUD导航
                startActivity(new Intent(IndexActivity.this, HudDisplayActivity.class));
            } else if (position == 11) {//导航数据(回调)
                startActivity(new Intent(IndexActivity.this, NaviInfoActivity.class));
            } else if (position == 12) {//展示导航路径详情
                startActivity(new Intent(IndexActivity.this, GetNaviStepsAndLinksActivity.class));
            }

        }
    };
    private String[] examples = new String[]

            {"高德地图(有终点)", "高德地图(无终点)", "驾车路线规划", "步行路线规划", "骑行路线规划 ( NEW!! )", "实时导航", "模拟导航", "智能巡航", "使用设备外GPS数据导航",
                    "导航UI自定义", "HUD导航", "导航回调说明(见代码)", "展示导航路径详情(见代码)"
            };
    AmapTTSController amapTTSController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        // SpeechUtils.getInstance(this).speakText();系统自带的语音播报
        amapTTSController = AmapTTSController.getInstance(getApplicationContext());
        amapTTSController.init();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples));
        setTitle("导航SDK " + AMapNavi.getVersion());
        listView.setOnItemClickListener(mItemClickListener);
    }

    /**
     * 返回键处理事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);// 退出程序
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {
        amapTTSController.onGetNavigationText(s);
    }

    @Override
    public void onStopSpeaking() {
        amapTTSController.stopSpeaking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        amapTTSController.destroy();
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

}
