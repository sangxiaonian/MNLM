package com.hongniu.freight.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.hongniu.thirdlibrary.map.MapUtils;

import io.reactivex.disposables.Disposable;

/**
 * 作者：  on 2019/11/2.
 */
public class MapPointFragment extends CompanyBaseFragment implements MapUtils.OnMapChangeListener {

    private MapView mMapView;
    private AMap aMap;
    //    private Marker marker;
    MapUtils mapUtils;
    private boolean start = true;

    //移动地图的时候是否更新数据
    private boolean upData = true;
    private ViewGroup llMarkDes;
    private TextView tv_title;
    private TextView tv_des;
    OnMapPointChangeListener listener;

    MapUtils.OnMapChangeListener changeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frament_map_point, null, false);
        mMapView = inflate.findViewById(R.id.map);
        llMarkDes = inflate.findViewById(R.id.ll);
        tv_title = inflate.findViewById(R.id.tv_title);
        tv_des = inflate.findViewById(R.id.tv_des);
        aMap = mMapView.getMap();



        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        mapUtils = new MapUtils();
        mapUtils.init(getContext(), aMap);
        mapUtils.setMapListener(this);
        aMap.getUiSettings().setScaleControlsEnabled(false);//隐藏定位标签
        llMarkDes.setVisibility(View.GONE);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMapPointChangeListener) {
            listener = (OnMapPointChangeListener) context;

        }
        if (context instanceof MapUtils.OnMapChangeListener) {
            changeListener = (MapUtils.OnMapChangeListener) context;

        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 定位改变监听
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void loactionChangeListener(double latitude, double longitude) {
        mapUtils.moveTo(latitude, longitude);
    }

    /**
     * 地图移动变化
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void onCameraChange(double latitude, double longitude) {
//        MarkUtils.moveMark(marker, latitude, longitude);
        llMarkDes.setVisibility(View.GONE);
        if (changeListener != null) {
            changeListener.onCameraChange(latitude, longitude);
        }
    }

    /**
     * 地图移动完成
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void onCameraChangeFinish(double latitude, double longitude) {
        queryData(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 100000));
        if (changeListener != null) {
            changeListener.onCameraChangeFinish(latitude, longitude);
        }
    }

    private void queryData(PoiSearch.SearchBound keSearch) {
        upData = false;
        PoiSearch search = getBoundParams(keSearch);

        HttpAppFactory.searchPio(search)
                .subscribe(new NetObserver<PageBean<PoiItem>>(null) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        llMarkDes.setVisibility(View.VISIBLE);
                        tv_des.setText("加载中...");
                        tv_title.setVisibility(View.GONE);
                    }

                    @Override
                    public void doOnSuccess(PageBean<PoiItem> data) {
                        //查询到所地址
                        if (!CollectionUtils.isEmpty(data.getList())) {
                            PoiItem poiItem = data.getList().get(0);
                            //移动到第一个的位置
//                            MarkUtils.moveMark(marker, poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                            setTagInfor(poiItem);

                        } else {
                            tv_des.setText("找不到位置");
                        }

                    }
                });


    }


    //根据当前地理位置进行搜索
    private PoiSearch getBoundParams(PoiSearch.SearchBound searchBound) {
        upData = true;
        PoiSearch.Query query = new PoiSearch.Query("", "", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(Param.PAGE_SIZE);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        query.setCityLimit(true);
        query.requireSubPois(true);
        PoiSearch poiSearch = new PoiSearch(getContext(), query);
        if (searchBound != null) {
            poiSearch.setBound(searchBound);
        }
        return poiSearch;
    }


    public void moveToPoint(PoiItem poiItem) {
        mapUtils.moveTo(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
        setTagInfor(poiItem);
    }

    private void setTagInfor(PoiItem poiItem) {
        llMarkDes.setVisibility(View.VISIBLE);
        String placeInfor = Utils.dealPioPlace(poiItem);
        tv_des.setText(placeInfor);
        tv_title.setText(poiItem.getTitle());
        tv_title.setVisibility(View.VISIBLE);
        if (listener != null) {
            listener.onMapPointChange(poiItem);
        }

    }

    public interface OnMapPointChangeListener {
        /**
         * 地图选中地点更改
         *
         * @param poiItem
         */
        void onMapPointChange(PoiItem poiItem);
    }

}
