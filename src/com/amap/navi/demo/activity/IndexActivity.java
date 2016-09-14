package com.amap.navi.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.navi.demo.R;
import com.amap.navi.demo.activity.custom.CustomUiActivity;


public class IndexActivity extends Activity {

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {//单路径
                startActivity(new Intent(IndexActivity.this, DriverListActivity.class));
            }else if (position == 1) {//步行路线规划
                startActivity(new Intent(IndexActivity.this, WalkRouteCalculateActivity.class));
            } else if (position == 2) {//实时导航
                startActivity(new Intent(IndexActivity.this, GPSNaviActivity.class));
            } else if (position == 3) {//模拟导航
                startActivity(new Intent(IndexActivity.this, EmulatorActivity.class));
            } else if (position == 4) {//只能巡航
                startActivity(new Intent(IndexActivity.this, IntelligentBroadcastActivity.class));
            } else if (position == 5) {//使用设备外GPS数据导航
                startActivity(new Intent(IndexActivity.this, UseExtraGpsDataActivity.class));
            } else if (position == 6) {//导航UI在自定义
                startActivity(new Intent(IndexActivity.this, CustomUiActivity.class));
            } else if (position == 7) {//HUD导航
                startActivity(new Intent(IndexActivity.this, HudDisplayActivity.class));
            } else if (position == 8) {//导航数据(回调)
                startActivity(new Intent(IndexActivity.this, NaviInfoActivity.class));
            } else if (position == 9) {//展示导航路径详情
                startActivity(new Intent(IndexActivity.this, GetNaviStepsAndLinksActivity.class));
            }

        }
    };
    private String[] examples = new String[]

            {"驾车路线规划", "步行路线规划", "实时导航", "模拟导航", "智能巡航", "使用设备外GPS数据导航",
                    "导航UI自定义", "HUD导航", "导航回调说明(见代码)", "展示导航路径详情(见代码)"
            };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initView();

        Toast.makeText(this, "demo数量:" + examples.length, Toast.LENGTH_SHORT).show();
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
    public void onDestroy() {
        super.onDestroy();
    }

}
