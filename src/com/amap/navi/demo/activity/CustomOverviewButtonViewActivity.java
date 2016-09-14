package com.amap.navi.demo.activity;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.view.OverviewButtonView;
import com.amap.navi.demo.R;


public class CustomOverviewButtonViewActivity extends BaseActivity implements AMapNaviListener {

    private OverviewButtonView mOverviewButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_overview_button_view);
        mOverviewButtonView = (OverviewButtonView) findViewById(R.id.myOverviewButtonView);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        //设置布局完全不可见
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);
        mAMapNaviView.setLazyOverviewButtonView(mOverviewButtonView);
    }

}
