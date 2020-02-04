package com.hongniu.freight.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CenterAlignImageSpan;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.XClickableSpan;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.presenter.OrderDetailPresenter;
import com.hongniu.freight.ui.holder.order.helper.OrderHelper;
import com.hongniu.freight.ui.holder.order.helper.OrderUtils;

import org.w3c.dom.Text;

/**
 * @data 2020/2/8
 * @Author PING
 * @Description 订单详情页
 */
@Route(path = ArouterParamApp.activity_order_detail)
public class OrderDetailActivity extends CompanyBaseActivity implements OrderDetailControl.IOrderDetailView, View.OnClickListener {

    private TextView tv_statute;//订单状态
    private TextView tv_order;//订单编号
    private TextView tv_start;//发车地址
    private TextView tv_start_contact;//发车联系人信息
    private ImageView img_start_chat;//发车联系人聊天
    private TextView tv_end;//收货地址
    private TextView tv_end_contact;//收货联系人信息
    private ImageView img_end_chat;//收货联系人聊天
    private TextView tv_driver_contact;//司机信息
    private TextView tv_detail;//订单详情
    private TextView tv_car_detail;//车辆信息

    OrderDetailControl.IOrderDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setWhitToolBar("订单详情");
        initView();
        initData();
        initListener();
        presenter = new OrderDetailPresenter(this);
        OrderInfoBean infoBean = getIntent().getParcelableExtra(Param.TRAN);
        presenter.initInfo(infoBean);
    }

    @Override
    protected void initView() {
        super.initView();
        tv_statute = findViewById(R.id.tv_statute);
        tv_order = findViewById(R.id.tv_order);
        tv_start = findViewById(R.id.tv_start);
        tv_start_contact = findViewById(R.id.tv_start_contact);
        img_start_chat = findViewById(R.id.img_start_chat);
        tv_end = findViewById(R.id.tv_end);
        tv_end_contact = findViewById(R.id.tv_end_contact);
        img_end_chat = findViewById(R.id.img_end_chat);
        tv_driver_contact = findViewById(R.id.tv_driver_contact);
        tv_detail = findViewById(R.id.tv_detail);
        tv_car_detail = findViewById(R.id.tv_car_detail);
    }

    @Override
    protected void initListener() {
        super.initListener();
        img_start_chat.setOnClickListener(this);
        img_end_chat.setOnClickListener(this);
    }

    protected void setWhitToolBar(String title) {
        if (tvToolbarTitle != null && title != null) {
            tvToolbarTitle.setText(title);
        }
        configToolbar(
                getResources().getColor(R.color.color_or_21222c),
                getResources().getColor(R.color.color_of_ffffff),
                R.drawable.ic_back_white,
                getResources().getColor(R.color.color_of_ffffff),
                false
        );
    }


    /**
     * 显示订单状态和编号
     *
     * @param infoBean
     */
    @Override
    public void showOrderState(OrderInfoBean infoBean) {
        //订单状态
        CommonUtils.setText(tv_statute, OrderUtils.getStatus(infoBean.getStatus()));
        tv_order.setText(String.format("订单编号  %s", "123567890"));
    }

    /**
     * 显示收货发货地址信息
     *
     * @param infoBean
     */
    @Override
    public void showOrderAddressInfo(OrderInfoBean infoBean) {
        CommonUtils.setText(tv_start, "测试发货地址");
        CommonUtils.setText(tv_end, "测试收货地址");
        tv_start_contact.setText(String.format("%s  %s", "发货人", "15515851515"));
        tv_end_contact.setText(String.format("%s  %s", "收货人", "15555555555"));
    }

    private String gap = "\t\t";

    /**
     * 初始化司机信息
     *
     * @param infoBean
     */
    @Override
    public void initDriverInfo(OrderInfoBean infoBean) {
        tv_driver_contact.setMovementMethod(LinkMovementMethod.getInstance());
        int color = getResources().getColor(R.color.color_of_040000);
        int titleColor = getResources().getColor(R.color.color_or_666666);
        int contactColor = getResources().getColor(R.color.color_or_3d5688);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        append(titleColor,"司机姓名",color,"测试司机姓名",builder);
        append(titleColor, builder, "司机手机");
        builder.append(gap);
        append(color, builder, "15515871516");
        builder.append("\t");
        int lineStart = builder.length();
        int lineEnd = builder.length() + 1;
        builder.append(" ");
        builder.append("\t");
        builder.setSpan(new CenterAlignImageSpan(this, R.drawable.ovl_line_v), lineStart, lineEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.append("联系司机");
        XClickableSpan clickableSpan = new XClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ToastUtils.getInstance().show("联系司机");
            }

        }
                .setColor(contactColor);
        builder.setSpan(clickableSpan, builder.length() - 4, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_driver_contact.setText(builder);
    }

    /**
     * 显示订单详情数据
     *
     * @param infoBean
     */
    @Override
    public void showOrderDetail(OrderInfoBean infoBean) {
        tv_detail.setMovementMethod(LinkMovementMethod.getInstance());
        int color = getResources().getColor(R.color.color_of_040000);
        int titleColor = getResources().getColor(R.color.color_or_666666);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        append(titleColor,"实际运费",color,"1600元",builder);
        append(titleColor,"货物运费",color,"1500元",builder);
        append(titleColor,"下单时间",color,"2020-11-12",builder);
        append(titleColor,"发货时间",color,"2020-11-11",builder);
        append(titleColor,"货物名称",color,"测试货物名称",builder);
        append(titleColor,"货物信息",color,"测试货物",builder);
        append(titleColor,"支付方式",color,"在线支付",builder);
        builder.delete(builder.length()-1,builder.length());
        tv_detail.setText(builder);
    }

    /**
     * 显示车辆信息
     *
     * @param infoBean
     */
    @Override
    public void showCarInfo(OrderInfoBean infoBean) {
        tv_car_detail.setMovementMethod(LinkMovementMethod.getInstance());
        int color = getResources().getColor(R.color.color_of_040000);
        int titleColor = getResources().getColor(R.color.color_or_666666);
        int contactColor = getResources().getColor(R.color.color_or_3d5688);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        append(titleColor,"车牌号码",color,"浙A28394",builder);
        append(titleColor,"承运人姓名",color,"测试承运人",builder);
        append(titleColor, builder, "承运人手机");
        builder.append(gap);
        append(color, builder, "15515871516");
        builder.append("\t");
        int lineStart = builder.length();
        int lineEnd = builder.length() + 1;
        builder.append(" ");
        builder.append("\t");
        builder.setSpan(new CenterAlignImageSpan(this, R.drawable.ovl_line_v), lineStart, lineEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.append("联系承运人");
        XClickableSpan clickableSpan = new XClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ToastUtils.getInstance().show("联系承运人");
            }

        }
                .setColor(contactColor);
        builder.setSpan(clickableSpan, builder.length() - 5, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_car_detail.setText(builder);
    }

    private void append(int color, SpannableStringBuilder builder, String msg) {
        int nameStart = builder.length();
        builder.append(msg);
        int nameEnd = builder.length();
        builder.setSpan(new ForegroundColorSpan(color), nameStart, nameEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    private void append(int titleColor, String title, int color, String content, SpannableStringBuilder builder ) {
        append(titleColor, builder, title);
        builder.append(gap);
        append(color, builder, content);
        builder.append("\n");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_start_chat) {
            ToastUtils.getInstance().show("和发货人聊天");
        } else if (v.getId() == R.id.img_end_chat) {
            ToastUtils.getInstance().show("和收货人聊天");

        }

    }
}
