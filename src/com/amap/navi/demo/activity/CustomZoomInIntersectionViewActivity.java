package com.amap.navi.demo.activity;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.view.ZoomInIntersectionView;
import com.amap.navi.demo.R;


public class CustomZoomInIntersectionViewActivity extends BaseActivity implements AMapNaviListener {

    private ZoomInIntersectionView mZoomInIntersectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_zoomin_intersection_view);
        mZoomInIntersectionView = (ZoomInIntersectionView) findViewById(R.id.myZoomInIntersectionView);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        //设置布局完全不可见
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);

        mAMapNaviView.setLazyZoomInIntersectionView(mZoomInIntersectionView);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        mAMapNavi.calculateDriveRoute(sList,eList,null,5);
    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }
}
