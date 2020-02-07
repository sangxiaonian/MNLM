package com.hongniu.freight.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.baselibrary.utils.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.ui.holder.OrderHolderBuider;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2020/2/5.
 * 首页对应不同类型的fragment
 * <p>
 * 0 托运人
 * 1 承运人
 * 2 司机
 */
@Route(path = ArouterParamApp.fragment_home_type)
public class HomeTypeFragment extends CompanyBaseFragment implements View.OnClickListener {

    private Role type;
    private List<OrderInfoBean> beans;
    private RecyclerView rv;
    private View ll_more;
    private XAdapter<OrderInfoBean> adapter;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_home_type, null);
        rv = inflate.findViewById(R.id.rv);
        ll_more = inflate.findViewById(R.id.ll_more);
        return inflate;
    }


    @Override
    public void setBundle(Bundle bundle) {
        super.setBundle(bundle);
        upDate();
    }


    @Override
    protected void initData() {
        super.initData();
        beans = new ArrayList<>();
        adapter = new XAdapter<OrderInfoBean>(getContext(), beans) {
            @Override
            public BaseHolder<OrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new OrderHolderBuider(mContext).setParent(parent)
                        .setType(type)
                        .build();

            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        upDate();
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_more.setOnClickListener(this);
    }

    private void upDate() {
        if (getBundle() == null) {
            return;
        }
        ArrayList<OrderInfoBean> list = getBundle().getParcelableArrayList(Param.TRAN);
        type= (Role) getBundle().getSerializable(Param.TYPE);

        if (!CollectionUtils.isEmpty(list)) {
            if (beans != null) {
                beans.clear();
                beans.addAll(list);
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ll_more){
            ArouterUtils.getInstance().builder(ArouterParamApp.activity_my_order)
                    .withSerializable(Param.TRAN,type)
                    .navigation(mContext);
        }
    }
}
