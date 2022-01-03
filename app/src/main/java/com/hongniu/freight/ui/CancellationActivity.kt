package com.hongniu.freight.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import com.alibaba.android.arouter.facade.annotation.Route
import com.fy.androidlibrary.toast.ToastUtils
import com.fy.androidlibrary.utils.DeviceUtils
import com.fy.baselibrary.utils.ArouterUtils
import com.fy.companylibrary.config.ArouterParamApp
import com.fy.companylibrary.net.NetObserver
import com.fy.companylibrary.ui.CompanyBaseActivity
import com.hongniu.freight.R
import com.hongniu.freight.databinding.ActivityCancellationBinding
import com.hongniu.freight.net.HttpAppFactory
import com.hongniu.freight.utils.InfoUtils


@Route(path = ArouterParamApp.activity_cancellation)
class CancellationActivity : CompanyBaseActivity() {
    private val binding: ActivityCancellationBinding by lazy {
        ActivityCancellationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setWhitToolBar("申请注销账号")

        val builder = SpannableStringBuilder()

        builder.append("你提交的申请注销生效前，我们将进行以下验证以保证你的账号、财产安全")
            .append("\n\n")

        var start = builder.length
        builder.append("1、用户无未完成的有效订单")
        var end = builder.length
        builder.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_of_333333)),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        builder.setSpan(
            AbsoluteSizeSpan(DeviceUtils.dip2px(this, 20f)),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        builder.append("\n\n")
            .append("如有未完成的订单，请处理为已完成状态，或者等所有订单状态为已完成或无效状态后再申请，否则会导致不可逆的损失")
            .append("\n\n")
        start = builder.length
        builder.append("2、保证该账号支付财产结清")
        end = builder.length
        builder.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_of_333333)),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        builder.setSpan(
            AbsoluteSizeSpan(DeviceUtils.dip2px(this, 20f)),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )

        builder.append("\n\n")
            .append("用户账户中如存在余额，请先申请提现，待提现服务完成后方可申请注销")
            .append("\n\n")

        binding.tvTitle.text = builder

        binding.btSum.setOnClickListener {
            HttpAppFactory.cancelAccount().subscribe(object : NetObserver<Any>(this) {
                override fun doOnSuccess(data: Any?) {
                    ToastUtils.getInstance().show("注销成功")
                    InfoUtils.loginOut()
                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_login)
                        .navigation(mContext)
                    finish()
                }
            })


        }
    }
}