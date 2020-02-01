package com.fy.baselibrary.refrush;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by danyic on 2017/9/8.
 */

public class XRefreshLayout extends SmartRefreshLayout {
    public RecycleFooter recycleFooter;
    public XRefreshLayout(Context context) {
        this(context,null);
        initUI();
    }

    public XRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }



    private void initUI() {
        recycleFooter = new RecycleFooter(getContext());
        setRefreshHeader(new RecycleHeader(getContext()));
        setRefreshFooter(recycleFooter);
        setHeaderHeight(50);
        setFooterHeight(40);


    }

    /**
     * @param isFinish false 为没有更多数据了
     */
    public void loadmoreFinished(boolean isFinish){
        finishLoadMore();
        setNoMoreData(!isFinish);//1.0.5 以上
    }

    public void hideLoadFinish(){
        recycleFooter.hideLoadFinish();
    }
}
