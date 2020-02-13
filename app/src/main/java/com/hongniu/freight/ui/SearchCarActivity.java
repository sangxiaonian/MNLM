package com.hongniu.freight.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.SearchTitleView;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.androidlibrary.widget.span.RoundedBackgroundSpan;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.CarInfoListBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 *@data  2020/2/13
 *@Author PING
 *@Description
 * 搜索车辆信息
 *
 */
@Route(path = ArouterParamApp.activity_search_car)

public class SearchCarActivity extends RefrushActivity<CarInfoListBean> implements  SearchTextWatcher.SearchTextChangeListener {

    private SearchTitleView searchTitleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car);
        setWhitToolBar("选择派送车辆");
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        searchTitleView=findViewById(R.id.search);
    }

    @Override
    protected void initListener() {
        super.initListener();
        searchTitleView.setSearchTextChangeListener(this);
    }

    /**
     * 获取空界面
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected Observable<CommonBean<PageBean<CarInfoListBean>>> getListDatas() {
        CommonBean<PageBean<CarInfoListBean>> common = new CommonBean<>();
        common.setCode(200);
        PageBean<CarInfoListBean> pageBean = new PageBean<>();
        common.setData(pageBean);
        List<CarInfoListBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CarInfoListBean orderInfoBean = new CarInfoListBean();
            list.add(orderInfoBean);
        }
        pageBean.setData(list);
        return Observable.just(common);
    }

    @Override
    protected XAdapter<CarInfoListBean> getAdapter(List<CarInfoListBean> datas) {
        return new XAdapter<CarInfoListBean>(mContext, datas) {
            @Override
            public BaseHolder<CarInfoListBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<CarInfoListBean>(context, parent, R.layout.item_search_car) {
                    @Override
                    public void initView(View itemView, int position, final CarInfoListBean data) {
                        super.initView(itemView, position, data);
                        TextView tv_title = itemView.findViewById(R.id.tv_car_info);
                        TextView tv_car_type = itemView.findViewById(R.id.tv_car_type);
                        TextView tv_phone = itemView.findViewById(R.id.tv_phone);

                        tv_car_type.setText("测试车辆类型");
                        tv_phone.setText("15515761537");

                        tv_title.setText(String.format("%s-%s","司机姓名","沪A12345"));
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent();
                                intent.putExtra(Param.TRAN,data);
                                setResult(Activity.RESULT_OK,intent);
                                finish();
                            }
                        });

                    }
                };
            }
        };
    }


    @Override
    public void onSearchTextChange(String msg) {
        queryData(true);

    }
}
