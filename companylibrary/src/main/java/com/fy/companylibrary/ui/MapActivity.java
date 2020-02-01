package com.fy.companylibrary.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.baidu.mapapi.map.MapView;

/**
 * 作者：  on 2019/11/20.
 */
public class MapActivity extends CompanyBaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected MapView getMapView() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (getMapView() != null)
            getMapView().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (getMapView() != null)
            getMapView().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (getMapView() != null)
            getMapView().onDestroy();
    }


}
