package com.hongniu.freight.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.hongniu.freight.R;

/**
 * 作者： PING
 * 日期： 2020/3/29.
 */
public class ImageInforView extends LinearLayout implements View.OnClickListener {

    private ImageView img;
    private View bt_img;
    private OnClickListener listener;

    public ImageInforView(Context context) {
        this(context, null);
    }

    public ImageInforView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageInforView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_image_camera, this, false);
        img = inflate.findViewById(R.id.img);
        bt_img = inflate.findViewById(R.id.bt_img);
        addView(inflate);
        bt_img.setOnClickListener(this);
        bt_img.setVisibility(GONE);
        setImageInfo(null);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
      this.listener=l;

    }

    /**
     * 设置图片
     * @param image
     */
    public void setImageInfo(Object image){
        if (image==null){
            ImageLoader.getLoader().load(getContext(), this.img, new ColorDrawable(getResources().getColor(R.color.color_of_f6f5f8)));
        }else {
            ImageLoader.getLoader().load(getContext(), this.img, image);
        }
        bt_img.setVisibility(isEnabled() ? VISIBLE : GONE);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            bt_img.setVisibility(GONE);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (listener!=null&&isEnabled()){
            listener.onClick(this);
        }
    }
}
