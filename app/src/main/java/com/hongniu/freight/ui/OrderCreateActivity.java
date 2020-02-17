package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;

/**
 * @data 2020/2/14
 * @Author PING
 * @Description 创建订单
 */
@Route(path = ArouterParamApp.activity_order_create)
public class OrderCreateActivity extends CompanyBaseActivity {
    private View group_start;//选择发货信息
    private View group_end;//选择收货信息s
    private TextView tv_start;//发货地址
    private TextView tv_start_contact;//发货联系人
    private TextView tv_end;//收货地址
    private TextView tv_end_contact;//收货人
    private ItemTextView item_start_time;//发货时间
    private ItemTextView item_cargo;//货物名称
    private ItemTextView item_weight;//货物重量
    private ItemTextView item_size;//货物体积
    private ItemTextView item_price;//运费
    private ItemTextView item_pay_way;//支付类型
    private TextView tv_agreement;//鸿牛供应链协议
    private TextView tv_agreement_insurance;//保险链协议
    private ImageView img_insurance;//是否购买保险
    private TextView bt_sum;//确认按钮
    private ItemTextView item_cargo_price;//货物价值
    private ItemTextView item_insurance_name;//被保险人信息






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_create);
        setWhitToolBar("我要发货");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        group_start=findViewById(R.id.group_start);
        group_end=findViewById(R.id.group_end);
        tv_start=findViewById(R.id.tv_start);
        tv_start_contact=findViewById(R.id.tv_start_contact);
        tv_end=findViewById(R.id.tv_end);
        tv_end_contact=findViewById(R.id.tv_end_contact);
        item_start_time=findViewById(R.id.item_start_time);
        item_cargo=findViewById(R.id.item_cargo);
        item_weight=findViewById(R.id.item_weight);
        item_size=findViewById(R.id.item_size);
        item_price=findViewById(R.id.item_price);
        item_pay_way=findViewById(R.id.item_pay_way);
        tv_agreement=findViewById(R.id.tv_agreement);
        tv_agreement_insurance=findViewById(R.id.tv_agreement_insurance);
        img_insurance=findViewById(R.id.img_insurance);
        bt_sum=findViewById(R.id.bt_sum);
        item_cargo_price=findViewById(R.id.item_cargo_price);
        item_insurance_name=findViewById(R.id.item_insurance_name);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
