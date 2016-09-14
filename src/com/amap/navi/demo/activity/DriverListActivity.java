package com.amap.navi.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amap.navi.demo.R;


public class DriverListActivity extends Activity {

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {//单路径
                startActivity(new Intent(DriverListActivity.this, SingleRouteCalculateActivity.class));
            } else if (position == 1) {//多路径
                startActivity(new Intent(DriverListActivity.this, RestRouteShowActivity.class));
            }

        }
    };
    private String[] examples = new String[]

            {"驾车路线规划(单路径)", "驾车路线规划(多路径)"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initView();

    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples));
        setTitle("路线规划");
        listView.setOnItemClickListener(mItemClickListener);
    }

}
