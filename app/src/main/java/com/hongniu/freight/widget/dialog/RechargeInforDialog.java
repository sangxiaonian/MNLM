package com.hongniu.freight.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.QueryBindHuaInforsBean;
import com.hongniu.freight.widget.dialog.inter.DialogControl;


/**
 * 作者： ${PING} on 2018/10/9.
 * 充值到账方式
 */
public class RechargeInforDialog implements DialogControl.IDialog, View.OnClickListener {

    private View imgCancel ,btSume;//取消按钮
    private TextView tvInfor  ;//取消按钮
    private Dialog dialog;
    OnEntryClickListener clickListener;
    private QueryBindHuaInforsBean countInfor;
    private TextView tvTitle;

    public void setInfor(QueryBindHuaInforsBean countInfor) {
        this.countInfor=countInfor;
    }

    public interface OnEntryClickListener{
        void onClickEntry(String msg);
    }


    public void setClickListener(OnEntryClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RechargeInforDialog(@NonNull Context context) {
        this(context, 0);
    }


    public RechargeInforDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }

    private void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_recharge_infor, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);

        tvInfor = inflate.findViewById(R.id.tv_infor);
        tvTitle = inflate.findViewById(R.id.tv_2);
        btSume = inflate.findViewById(R.id.bt_sum);

        imgCancel.setOnClickListener(this);
        btSume.setOnClickListener(this);

        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }



    @Override
    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    public void show() {

        dialog.show();
        if (countInfor!=null) {
            StringBuffer buffer=new StringBuffer();
            buffer.append("户名：")
                    .append(countInfor.getOthBankPayeeSubAccName())
                    .append("\n")
                    .append("账号：")
                    .append(countInfor.getOthBankPayeeSubAcc())
                    .append("\n")
                    .append("支行：")
                    .append(countInfor.getOthBankPayeeSubAccSetteName());
            tvInfor.setText(buffer.toString());
            tvTitle.setText("请采用你绑定的银行卡"+countInfor.getBankCardNum()+"，通过网银转账或汇款方式转入华夏银行监管账户，收款账户信息如下：");
        }
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_cancel) {
            dismiss();
        }else if (v.getId()==R.id.bt_sum){
            if (clickListener!=null){
                clickListener.onClickEntry(tvInfor.getText().toString().trim());
            }

            dismiss();

        }
    }
}
