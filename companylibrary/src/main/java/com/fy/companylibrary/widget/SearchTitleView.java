package com.fy.companylibrary.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fy.companylibrary.R;

/**
 * 作者：  on 2019/11/22.
 */
public class SearchTitleView extends FrameLayout {

    private String hintText = "请输入搜索内容";
    private TextView tvTitle;
    private LinearLayout llSearch;
    private ImageView imgSearch;
    private int hintColor;
    private int textColor;


    public SearchTitleView(@NonNull Context context) {
        this(context, null);
    }

    public SearchTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.search_title_child, this, false);
        tvTitle = inflate.findViewById(R.id.tv_title);
        llSearch = inflate.findViewById(R.id.ll_search);
        imgSearch = inflate.findViewById(R.id.img);
        tvTitle.setText(hintText);
        addView(inflate);
        hintColor = Color.parseColor("#c7c7c7");
        textColor = Color.parseColor("#333333");
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        if (TextUtils.isEmpty(title)) {
            tvTitle.setText(hintText);
            tvTitle.setTextColor(hintColor);
        }
    }

    public void setSearchBackGround(Drawable searchBackGround) {
        llSearch.setBackground(searchBackGround);
    }


    public void setHintText(String hintText, int hintColor, int textColor, Drawable img, Drawable background) {
        this.hintText = hintText;
        this.hintColor = hintColor;
        this.textColor = textColor;
        tvTitle.setText(hintText);
        tvTitle.setTextColor(hintColor);
        imgSearch.setImageDrawable(img);
        llSearch.setBackground(background);
    }

    private String title;

    public void setTitle(String title) {
        this.title = title;
        if (TextUtils.isEmpty(title)) {
            clear();
        } else {
            tvTitle.setText(title);
            tvTitle.setTextColor(textColor);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
            params.weight = 1;
            tvTitle.setLayoutParams(params);
        }
    }

    private void clear() {
        tvTitle.setText(hintText);
        tvTitle.setTextColor(hintColor);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
        params.weight = 0;
        tvTitle.setLayoutParams(params);
    }

    public String getTitle() {
        return title;
    }
}
