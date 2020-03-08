package com.hongniu.freight.ui;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.LBSTraceClient;
import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.LocationBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.PathBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.thirdlibrary.map.MarkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * 查看轨迹
 */
@Route(path = ArouterParamApp.activity_map_check_path)
public class OrderMapCheckPathActivity extends CompanyBaseActivity {


    private MapView mapView;

    private AMap aMap;
    private LBSTraceClient mTraceClient;
    private PolylineOptions lineOption;
    private OrderInfoBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_check_path);
        bean=getIntent().getParcelableExtra(Param.TRAN);
        initView();
        initData();
        initListener();
        mapView.onCreate(savedInstanceState);
        drawPathWithNoTrace(bean);

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void initView() {
        super.initView();
        mapView = findViewById(R.id.map);
        aMap = mapView.getMap();
//        item = findViewById(R.id.item_order);
//        item.hideButton(true);


    }


    @Override
    protected void initData() {
        super.initData();
        setWhitToolBar("查看轨迹");
        mTraceClient = LBSTraceClient.getInstance(this);
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        BitmapDescriptor mRedTexture = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_line);
        textureList.add(mRedTexture);
        List<Integer> textureIndexs = new ArrayList<Integer>();
        textureIndexs.add(0);
        lineOption = new PolylineOptions()
                .width(DeviceUtils.dip2px(mContext, 5))
                .color(Color.parseColor("#43BFA3"));


    }
    /**
     * 绘制轨迹
     *
     * @param linePatch
     */
    private void drawPath(List<LatLng> linePatch) {


        lineOption.addAll(linePatch);
        mapView.getMap().addPolyline(lineOption);
    }


    private void drawPathWithNoTrace(final OrderInfoBean bean) {
        HttpAppFactory.getPath(bean.getId())
                .map(new Function<CommonBean<PathBean>, List<LocationBean>>() {
                    @Override
                    public List<LocationBean> apply(CommonBean<PathBean> pathBeanCommonBean) throws Exception {
                        if (pathBeanCommonBean.getCode() == 200) {
                            return pathBeanCommonBean.getData().getList();

                        } else {
                            throw new NetException(pathBeanCommonBean.getCode(), pathBeanCommonBean.getMsg());
                        }

                    }
                })
                .map(new Function<List<LocationBean>, List<LatLng>>() {
                    @Override
                    public List<LatLng> apply(List<LocationBean> data) throws Exception {
                        if (!data.isEmpty()) {
                            MarkUtils.addMark(aMap,
                                    BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_carmap_50))
                                    , data.get(data.size() - 1).getLatitude(), data.get(data.size() - 1).getLongitude()
                            );

                        }
                        LatLngBounds.Builder builder2 = new LatLngBounds.Builder();
                        builder2.include(new LatLng(bean.getStartPlaceLat(), bean.getStartPlaceLon()));
                        List<LatLng> result = new ArrayList<>();
                        for (LocationBean o : data) {
                            if (o.getLatitude() != 0 && o.getLongitude() != 0) {
                                LatLng latLng = new LatLng(o.getLatitude(), o.getLongitude());
                                result.add(latLng);
                                builder2.include(latLng);

                            }
                        }
                        LatLngBounds latlngBounds = builder2.build();
                        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, DeviceUtils.dip2px(mContext, 50)));
                        return result;
                    }
                })

                .compose(RxUtils.<List<LatLng>>getSchedulersObservableTransformer())
                .subscribe(new BaseObserver<List<LatLng>>(this) {
                    @Override
                    public void onNext(List<LatLng> result) {
                        super.onNext(result);
                        if (result == null || result.isEmpty()) {
                            showErrorAlert(0, "当前订单暂无位置信息");
                        } else {
                            drawPath(result);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        drawMark(bean);

                    }
                });


    }

    private void drawMark(OrderInfoBean bean) {

        MarkUtils.addMark(aMap,
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.start))
                , bean.getStartPlaceLat(), bean.getStartPlaceLat()
        );
        MarkUtils.addMark(aMap,
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.end))
                , bean.getDestinationLat(), bean.getDestinationLon()
        );
    }


}
