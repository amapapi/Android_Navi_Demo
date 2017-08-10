package com.amap.navi.demo.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapTrafficStatus;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.view.TrafficBarView;
import com.amap.navi.demo.R;

import java.util.List;

public class CustomTrafficBarViewActivity extends BaseActivity {

    private TrafficBarView mTrafficBarView;
    private NaviInfo lastNaviInfo;
    private int remainingDistance;
    private AMapNaviPath naviPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_traffic_bar_view);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        //设置布局完全不可见
        AMapNaviViewOptions viewOptions = mAMapNaviView.getViewOptions();
        viewOptions.setLayoutVisible(false);
        //主动隐藏蚯蚓线
        viewOptions.setTrafficBarEnabled(false);
        //可以自由设置自车位置，第一个参数为在宽的百分之多少处
        //第二个参数为在高的百分之多少处
        viewOptions.setPointToCenter(1.0 / 2, 1.0 / 2);
        viewOptions.setTilt(0);
        mAMapNaviView.setViewOptions(viewOptions);
        mAMapNaviView.setAMapNaviViewListener(this);


        mTrafficBarView = (TrafficBarView) findViewById(R.id.myTrafficBar);
        //设置原始高度的百分比，其中若值<0.1 则用0.1 若值>1 则用1
        //设置横屏时高度为原始图片的0.5
        mTrafficBarView.setTmcBarHeightWhenLandscape(0.5);
        //设置竖屏时高度和原始图片一致
        mTrafficBarView.setTmcBarHeightWhenPortrait(1);

        mTrafficBarView.setUnknownTrafficColor(Color.WHITE);
        mTrafficBarView.setSmoothTrafficColor(Color.GREEN);
        mTrafficBarView.setSlowTrafficColor(Color.YELLOW);
        mTrafficBarView.setJamTrafficColor(Color.DKGRAY);
        mTrafficBarView.setVeryJamTrafficColor(Color.BLACK);

    }
    //如果你想完全自定义一个样式，你可以学习以下注释后的代码

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        super.onNaviInfoUpdate(naviinfo);
        lastNaviInfo = naviinfo;

//        每次NaviInfo更新的时候 准确的获取接下来的路长，以及接下来的路况
        remainingDistance = lastNaviInfo.getPathRetainDistance();
    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);

        if (mAMapNavi != null) {
            naviPath = mAMapNavi.getNaviPath();
        }

        int end = naviPath.getAllLength();
        int start = 0;
//    第一次算路成功进行整个路况的绘制
//    首先获取整个路况信息
        List<AMapTrafficStatus> totalRoadCondition = mAMapNavi.getTrafficStatuses(start, end);

//        将路况和路长两项传入进行绘制
        mTrafficBarView.update(totalRoadCondition, end);
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }

    @Override
    public void onTrafficStatusUpdate() {
//        路况变化的时候重新绘制余下路况的蚯蚓线

        int end = naviPath.getAllLength();
        int start = end - remainingDistance;

        List<AMapTrafficStatus> remainingRoadCondition = mAMapNavi.getTrafficStatuses(start, end);
        mTrafficBarView.update(remainingRoadCondition, remainingDistance);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        boolean isLandscape = isOrientationLandscape();
        mTrafficBarView.onConfigurationChanged(isLandscape);
    }

    private boolean isOrientationLandscape() {
        return this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }


}
