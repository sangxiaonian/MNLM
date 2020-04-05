package com.fy.baselibrary.refrush;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.baselibrary.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


/**
 * Created by danyic on 2017/9/7.
 */

public class RecycleHeader extends LinearLayout implements RefreshHeader {
    private ImageView ivLoading;
//    private AnimationDrawable animationDrawable;

    public RecycleHeader(Context context) {
        super(context);
        initView(context);
    }

    public RecycleHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public RecycleHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);


        removeAllViews();
        int size = DeviceUtils.dip2px(context, 50);
        ivLoading = new ImageView(context);
        Glide.with(getContext()).load(R.raw.loading).into(ivLoading);


        addView(ivLoading, size  , size  );
        setMinimumHeight(size);
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
//        mProgressDrawable.start();//开始动画
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 0;//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh://下拉开始刷新
//                iv.setVisibility(VISIBLE);
//                ivLoading.setVisibility(GONE);
//                gifView.setVisibility(GONE);
//                gifFromResource.seekTo(0);
//                gifFromResource.stop();
                break;
            case Refreshing://正在刷新

//                iv.setVisibility(GONE);
//                ivLoading.setVisibility(VISIBLE);
//                gifView.setVisibility(VISIBLE);
//                gifFromResource.start();
                break;
            case ReleaseToRefresh://拉到底
                break;
            case RefreshFinish://刷新完成
                break;
        }
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
    }

    /**
     * 手指拖动下拉（会连续多次调用，添加isDragging并取代之前的onPulling、onReleasing）
     *
     * @param isDragging    true 手指正在拖动 false 回弹动画
     * @param percent       下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+maxDragHeight) / footerHeight )
     * @param offset        下拉的像素偏移量  0 - offset - (footerHeight+maxDragHeight)
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }


    /**
     * 释放时刻（调用一次，将会触发加载）
     *
     * @param refreshLayout RefreshLayout
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }


    @Override
    public void setPrimaryColors(int... colors) {
    }
}
