package com.amap.navi.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.model.AMapNaviGuide;
import com.amap.api.navi.model.AMapNaviLink;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.navi.demo.R;

import java.util.List;


public class GetNaviStepsAndLinksActivity extends BaseActivity {


    private AMapNaviPath mAMapNaviPath;
    private List<AMapNaviStep> steps;
    private List<AMapNaviLink> links;
    private List<AMapNaviGuide> guides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mEndLatlng = new NaviLatLng(45.742367, 126.661665);
        mStartLatlng = new NaviLatLng(22.373594, 113.562575);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);


    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        super.onNaviInfoUpdate(naviinfo);
        int currentStep = naviinfo.getCurStep();
        int currentLink = naviinfo.getCurLink();
        Log.d("wlx", "当前Step index:" + currentStep + "当前Link index:" + currentLink);
    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();


        //概览
        guides = mAMapNavi.getNaviGuideList();

        //详情
        mAMapNaviPath = mAMapNavi.getNaviPath();
        steps = mAMapNaviPath.getSteps();

        if (guides.size() == steps.size()) {

            Toast.makeText(this, "看log", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < steps.size() - 1; i++) {
                //guide step相生相惜，指的是大导航段
                AMapNaviGuide guide = guides.get(i);
                Log.d("wlx", "AMapNaviGuide 路线经纬度:" + guide.getCoord() + "");
                Log.d("wlx", "AMapNaviGuide 路线名:" + guide.getName() + "");
                Log.d("wlx", "AMapNaviGuide 路线长:" + guide.getLength() + "m");
                Log.d("wlx", "AMapNaviGuide 路线耗时:" + guide.getTime() + "s");
                Log.d("wlx", "AMapNaviGuide 路线IconType" + guide.getIconType() + "");
                AMapNaviStep step = steps.get(i);
                Log.d("wlx", "AMapNaviStep 距离:" + step.getLength() + "m" + " " + "耗时:" + step.getTime() + "s");
                Log.d("wlx", "AMapNaviStep 红绿灯个数:" + step.getTrafficLightNumber());


                //link指的是大导航段中的小导航段
                links = step.getLinks();
                for (AMapNaviLink link : links) {
//          请看com.amap.api.navi.enums.RoadClass，以及帮助文档
                    Log.d("wlx", "AMapNaviLink 道路名:" + link.getRoadName() + " " + "道路等级:" + link.getRoadClass());
//          请看com.amap.api.navi.enums.RoadType，以及帮助文档
                    Log.d("wlx", "AMapNaviLink 道路类型:" + link.getRoadType());

                }
            }

        } else {
            Toast.makeText(this, "BUG！请联系我们", Toast.LENGTH_SHORT).show();
        }


    }
}
