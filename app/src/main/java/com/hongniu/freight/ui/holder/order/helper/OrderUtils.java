package com.hongniu.freight.ui.holder.order.helper;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Status;

import java.util.Map;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderUtils {

    /**
     * 根据所给的状态获取状态值
     *
     * @param status
     * @return
     */
    public static String getStatus(int status) {
        String result;
        if (status == Status.WAITE_PAY.getStatus()) {
            result = "待支付";
        } else if (status == Status.WAITE_RECEIVING_ORDER.getStatus()) {
            result = "待接单";
        } else if (status == Status.WAITE_PAY_BALANCE.getStatus()) {
            result = "差额支付中";
        } else if (status == Status.WAITE_CAR.getStatus()) {
            result = "待派车";
        } else if (status == Status.FIND_CAR.getStatus()) {
            result = "找车中";
        } else if (status == Status.WAITE_DEPART_NO_INSURANCE.getStatus()) {
            result = "待发车(未买保险)";
        } else if (status == Status.WAITE_DEPART_INSURANCE.getStatus()) {
            result = "待发车(已买保险)";
        } else if (status == Status.IN_TRANSIT.getStatus()) {
            result = "运输中";
        } else if (status == Status.ARRIVE.getStatus()) {
            result = "已到达";
        } else if (status == Status.RECEIVING.getStatus()) {
            result = "已收货";
        } else {
            result = "未知状态";
        }

        return result;
    }


    public static TextView getButton(Context context, int type, String msg){
        TextView button =new TextView(context);
        button.setGravity(Gravity.CENTER);
        button.setText(msg);
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
        if (type==0){
            button.setBackgroundResource(R.drawable.ovl_5_eeeeee);
            button.setTextColor(context.getResources().getColor(R.color.color_of_040000));
        }else {
            button.setBackgroundResource(R.drawable.ovl_5_e50000);
            button.setTextColor(Color.WHITE);

        }
        LinearLayout.MarginLayoutParams  params=new LinearLayout.MarginLayoutParams( DeviceUtils.dip2px(context,72), DeviceUtils.dip2px(context,28));
        params.leftMargin=DeviceUtils.dip2px(context,10);
        button.setLayoutParams(params);
        return button;
    }


}
