package com.hongniu.freight.ui;

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
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.androidlibrary.widget.span.RoundedBackgroundSpan;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.utils.Utils;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/10
 * @Author PING
 * @Description 我的车辆
 */
@Route(path = ArouterParamApp.activity_my_car_list)
public class MyCarListActivity extends RefrushActivity<CarInfoBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_list);
        setWhitToolBar("我的车辆");
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void initData() {
        super.initData();
        adapter.addFoot(new PeakHolder(mContext, rv,R.layout.item_car_foot) {
            @Override
            public void initView(View itemView, int position) {
                super.initView(itemView, position);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.getInstance().show("添加车辆");
                    }
                });
            }
        });
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
    protected Observable<CommonBean<PageBean<CarInfoBean>>> getListDatas() {
        CommonBean<PageBean<CarInfoBean>> common = new CommonBean<>();
        common.setCode(200);
        PageBean<CarInfoBean> pageBean = new PageBean<>();
        common.setData(pageBean);
        List<CarInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CarInfoBean orderInfoBean = new CarInfoBean();
            list.add(orderInfoBean);
        }
        pageBean.setData(list);
        return Observable.just(common);
    }

    @Override
    protected XAdapter<CarInfoBean> getAdapter(List<CarInfoBean> datas) {
        return new XAdapter<CarInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<CarInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<CarInfoBean>(context, parent, R.layout.item_car) {
                    @Override
                    public void initView(View itemView, int position, CarInfoBean data) {
                        super.initView(itemView, position, data);
                        TextView tv_title = itemView.findViewById(R.id.tv_title);
                        TextView tv_car_type = itemView.findViewById(R.id.tv_car_type);
                        TextView tv_state = itemView.findViewById(R.id.tv_state);
                        RoundedBackgroundSpan span = new RoundedBackgroundSpan(mContext);
                        span.setBackgroundColor(context.getResources().getColor(R.color.color_of_14_ffffff));
                        span.setCornerRadius(DeviceUtils.dip2px(context, 1));
                        span.setTextSize(DeviceUtils.dip2px(context, 11));
                        span.setTextColor(context.getResources().getColor(R.color.color_of_aaabaf));
                        span.setPadding(DeviceUtils.dip2px(context, 4), 0, DeviceUtils.dip2px(context, 4), 0);
                        span.setMargin(DeviceUtils.dip2px(context, 5), 0, 0, 0);
                        span.setBordColor(Color.TRANSPARENT);
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append("浙A37222");
                        int start = builder.length();
                        builder.append("已认证");
                        int end = builder.length();
                        builder.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        tv_title.setText(builder);
                        tv_car_type.setText("测试车辆类型");
                        tv_state.setText("查看信息");

                        tv_state.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().show("测试信息");
                            }
                        });

                    }
                };
            }
        };
    }
}
