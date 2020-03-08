package com.hongniu.freight.ui;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.widget.span.CenterAlignImageSpan;
import com.fy.androidlibrary.widget.span.XClickableSpan;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.control.OrderDetailControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.presenter.OrderDetailPresenter;

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
    private TextView bt_left;//
    private TextView bt_right;//


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
        presenter.queryDetail(this);
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
        bt_left = findViewById(R.id.bt_left);
        bt_right = findViewById(R.id.bt_right);
    }

    @Override
    protected void initListener() {
        super.initListener();
        img_start_chat.setOnClickListener(this);
        img_end_chat.setOnClickListener(this);
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
    }

    protected void setWhitToolBar(String title) {
        if (tvToolbarTitle != null && title != null) {
            tvToolbarTitle.setText(title);
        }
        configToolbar(
                getResources().getColor(R.color.color_of_21222c),
                getResources().getColor(R.color.color_of_ffffff),
                R.drawable.ic_back_white,
                getResources().getColor(R.color.color_of_ffffff),
                false
        );
    }


    /**
     * 显示订单状态和编号
     *
     * @param status
     * @param orderInfo
     */
    @Override
    public void showOrderState(String status, String orderInfo) {
        //订单状态
        CommonUtils.setText(tv_statute, status);
        CommonUtils.setText(tv_order, orderInfo);
    }

    /**
     * 显示收货发货地址信息
     *
     * @param infoBean
     */
    @Override
    public void showOrderAddressInfo(OrderInfoBean infoBean) {
        CommonUtils.setText(tv_start, infoBean.getStartPlaceInfo());
        CommonUtils.setText(tv_end, infoBean.getDestinationInfo());
        tv_start_contact.setText(String.format("%s  %s", infoBean.getShipperName(), infoBean.getShipperMobile()));
        tv_end_contact.setText(String.format("%s  %s", infoBean.getReceiverName(), infoBean.getReceiverMobile()));
        img_start_chat.setVisibility(TextUtils.isEmpty(infoBean.getShipperMobile()) ? View.GONE : View.VISIBLE);
        img_end_chat.setVisibility(TextUtils.isEmpty(infoBean.getReceiverMobile()) ? View.GONE : View.VISIBLE);
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
        int titleColor = getResources().getColor(R.color.color_of_666666);
        int contactColor = getResources().getColor(R.color.color_of_3d5688);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        append(titleColor, "司机姓名", color, infoBean.getDriverName(), builder);
        append(titleColor, builder, "司机手机");
        builder.append(gap);
        append(color, builder, infoBean.getDriverMobile());
        appendLine(builder);
        appendClick(builder, "联系司机", new XClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ToastUtils.getInstance().show("联系司机");
            }

        }
                .setColor(contactColor));
        tv_driver_contact.setText(builder);
    }

    //添加一个点击事件
    private void appendClick(SpannableStringBuilder builder, String content, ClickableSpan clickableSpan) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        builder.append(content);

        builder.setSpan(clickableSpan, builder.length() - 4, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

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
        int titleColor = getResources().getColor(R.color.color_of_666666);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        append(titleColor, "实际运费", color, "1600元", builder);
        append(titleColor, "货物保费", color, "1600元", builder);
        append(titleColor, "货物运费", color, "1500元", builder);
        append(titleColor, "下单时间", color, ConvertUtils.formatTime(infoBean.getCreateTime(),"yyyy-MM-dd HH:mm:ss"), builder);
        append(titleColor, "发货时间", color, TextUtils.isEmpty(infoBean.getDepartureTime()) ? "立即发货" : infoBean.getDepartureTime(), builder);
        append(titleColor, "货物名称", color, infoBean.getGoodName(), builder);

        append(titleColor, builder, "货物信息");
        builder.append(gap);
        append(color, builder, infoBean.getGoodWeight());
        appendLine(builder);
        append(color, builder, infoBean.getGoodVolume());
        appendLine(builder);
        append(color, builder, infoBean.getGoodPrice());

        tv_detail.setText(builder);
    }

    //添加一条竖线
    private void appendLine(SpannableStringBuilder builder) {
        builder.append("\t");
        builder.append("\t");
        int lineStart = builder.length();
        int lineEnd = builder.length() + 1;
        builder.append("\t");
        builder.append("\t");
        builder.setSpan(new CenterAlignImageSpan(this, R.drawable.ovl_line_v), lineStart, lineEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

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
        int titleColor = getResources().getColor(R.color.color_of_666666);
        int contactColor = getResources().getColor(R.color.color_of_3d5688);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        append(titleColor, "车牌号码", color, infoBean.getCarNum(), builder);
        append(titleColor, "承运人姓名", color, infoBean.getOwnerName(), builder);
        append(titleColor, builder, "承运人手机");
        builder.append(gap);
        append(color, builder, infoBean.getOwnerMobile());
        appendLine(builder);
        appendClick(builder, "联系承运人"
                , new XClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        ToastUtils.getInstance().show("联系承运人");
                    }

                }
                        .setColor(contactColor));
        tv_car_detail.setText(builder);
    }

    /**
     * 更改底部按钮数据
     *
     * @param infoBean
     */
    @Override
    public void showButton(String[] infoBean) {
        bt_left.setVisibility(TextUtils.isEmpty(infoBean[0]) ? View.GONE : View.VISIBLE);
        bt_right.setVisibility(TextUtils.isEmpty(infoBean[1]) ? View.GONE : View.VISIBLE);
        bt_left.setText(TextUtils.isEmpty(infoBean[0]) ? "" : infoBean[0]);
        bt_right.setText(TextUtils.isEmpty(infoBean[1]) ? "" : infoBean[1]);
    }

    /**
     * 拨打电话
     *
     * @param mobile
     */
    @Override
    public void statCall(String mobile) {
        CommonUtils.call(mContext, mobile);
    }

    private void append(int color, SpannableStringBuilder builder, String msg) {
        if (msg == null) {
            msg = "";
        }
        int nameStart = builder.length();
        builder.append(msg);
        int nameEnd = builder.length();
        builder.setSpan(new ForegroundColorSpan(color), nameStart, nameEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    private void append(int titleColor, String title, int color, String content, SpannableStringBuilder builder) {

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
            presenter.contactStart();
//            ToastUtils.getInstance().show("和发货人聊天");
        } else if (v.getId() == R.id.img_end_chat) {
//            ToastUtils.getInstance().show("和收货人聊天");
            presenter.contactEnd();

        } else if (v.getId() == R.id.bt_left) {
            ToastUtils.getInstance().show("左侧按钮");

        } else if (v.getId() == R.id.bt_right) {
            ToastUtils.getInstance().show("右侧按钮");

        }

    }
}
