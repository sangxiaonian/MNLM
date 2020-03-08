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
