package com.fy.baselibrary.refrush;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.baselibrary.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by  on 2017/9/7.
 */

public class RecycleFooter extends LinearLayout implements RefreshFooter {
    private View loadFinish;
    private TextView tv;
    private Context cxt;
    private ImageView ivLoading;
    private AnimationDrawable animationDrawable;

    public RecycleFooter(Context context) {
        super(context);
        cxt = context;
        initView(context);
    }

    public RecycleFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        cxt = context;
        this.initView(context);
    }

    public RecycleFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.HORIZONTAL);

        ivLoading = new ImageView(context);
        ivLoading.setImageResource(R.drawable.loading_more);
        animationDrawable = ((AnimationDrawable) ivLoading.getDrawable());

        loadFinish = LayoutInflater.from(cxt).inflate(R.layout.layout_loading_more_finish, null);
        tv = new TextView(context);
        tv.setPadding(DeviceUtils.dip2px(context, 5), 0, 0, 0);
        tv.setText("正在加载更多数据......");
        tv.setTextColor(Color.parseColor("#666666"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, DeviceUtils.dip2px(context, 12));
        int dimen = DeviceUtils.dip2px(context, 16);
        addView(ivLoading, dimen, dimen);
        addView(tv);
        addView(loadFinish);
        setMinimumHeight(DeviceUtils.dip2px(context, 30));
        loadFinish.setVisibility(GONE);
        animationDrawable.start();//开始动画
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
        animationDrawable.start();//开始动画
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        if (success) {
//            mHeaderText.setText("刷新完成");
        } else {
//            mHeaderText.setText("刷新失败");
        }
        return 0;//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
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


    public void hideLoadFinish() {
        loadFinish.setVisibility(GONE);
        ivLoading.setVisibility(GONE);
        tv.setVisibility(GONE);
        setNoMoreData(true);
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     *
     * @param noMoreData 是否有更多数据 true 显示没有更多数据了 false 还可以加载更多数据
     * @return true 支持全部加载完成的状态显示 false 不支持
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (noMoreData) {
            ivLoading.setVisibility(GONE);
            tv.setVisibility(GONE);
            loadFinish.setVisibility(VISIBLE);
            animationDrawable.stop();
        } else {
            ivLoading.setVisibility(VISIBLE);
            tv.setVisibility(VISIBLE);
            loadFinish.setVisibility(GONE);
            animationDrawable.start();
        }
        return true;
    }
}
