package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushFragmet;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BillInfoListBean;
import com.hongniu.freight.ui.holder.BillHeadHolder;
import com.hongniu.freight.ui.holder.BillHolder;
import com.hongniu.freight.ui.holder.EmptyHolder;
import com.hongniu.freight.widget.VistogramView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/12.
 */
@Route(path = ArouterParamApp.fragment_bill_month_child)
public class BillMonthChildFragment extends RefrushFragmet<BillInfoListBean> {

    private int type;
    private BillHeadHolder billHeadHolder;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        upData();
        super.onActivityCreated(savedInstanceState);
        queryData(true);
    }

    private void upData() {
        if (getBundle() != null) {
            type = getBundle().getInt(Param.TYPE, 0);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_bill_month_child, null);
        return inflate;

    }

    @Override
    protected void initData() {
        super.initData();
        billHeadHolder = new BillHeadHolder(mContext, rv);
        billHeadHolder.setType(type);
        List<List<VistogramView.VistogramBean>> datas=new ArrayList<>();
        int colorBlue=getResources().getColor(R.color.color_of_1890ff);
        int colorRed=getResources().getColor(R.color.color_of_e50000);
        for (int i = 0; i < 12; i++) {
            List<VistogramView.VistogramBean> childs=new ArrayList<>();
            VistogramView.VistogramBean vistogramBean=new VistogramView.VistogramBean(colorRed, ConvertUtils.getRandom(0,5000),"2020-"+i);
            childs.add(vistogramBean);
            if (type==0){
                VistogramView.VistogramBean vistogramBean1=new VistogramView.VistogramBean(colorBlue, ConvertUtils.getRandom(0,5000),"2020-"+i);
                childs.add(vistogramBean1);
            }
            datas.add(childs);
        }

        billHeadHolder.setDatas(datas);
        adapter.addHeard(billHeadHolder);
    }

    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);
        upData();
    }

    /**
     * 获取空数据
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        EmptyHolder emptyHolder = new EmptyHolder(mContext, parent);
        emptyHolder.setEmptyMsg("未找到相应账单");
        return emptyHolder;
    }

    @Override
    protected Observable<CommonBean<PageBean<BillInfoListBean>>> getListDatas() {
        CommonBean<PageBean<BillInfoListBean>> common = new CommonBean<>();
        common.setCode(200);
        PageBean<BillInfoListBean> pageBean = new PageBean<>();
        common.setData(pageBean);
        List<BillInfoListBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BillInfoListBean orderInfoBean = new BillInfoListBean();
            list.add(orderInfoBean);
        }
        pageBean.setList(list);
        return Observable.just(common);
    }

    @Override
    protected XAdapter<BillInfoListBean> getAdapter(List<BillInfoListBean> datas) {
        return new XAdapter<BillInfoListBean>(mContext, datas) {
            @Override
            public BaseHolder<BillInfoListBean> initHolder(ViewGroup parent, int viewType) {
                return new BillHolder(mContext, parent);
            }
        };
    }
}
