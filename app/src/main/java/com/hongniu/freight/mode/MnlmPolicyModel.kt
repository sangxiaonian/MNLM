package com.hongniu.freight.mode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fy.androidlibrary.net.listener.TaskControl
import com.fy.companylibrary.entity.CommonBean
import com.fy.companylibrary.net.NetObserver
import com.hongniu.freight.entity.PolicyCaculParam
import com.hongniu.freight.entity.PolicyInfoBean
import com.hongniu.freight.net.HttpAppFactory
import io.reactivex.Observable

/**
 *@data  2022/7/13$
 *@Author PING
 *@Description
 *
 *
 */
class MnlmPolicyModel : ViewModel() {

    var policyInfo = MutableLiveData<PolicyInfoBean>()
    var policyResult = MutableLiveData<PolicyCaculParam>()
    var params: PolicyCaculParam? = null

    fun saveInfo(policyCaculParam: PolicyCaculParam?) {
        params = policyCaculParam ?: PolicyCaculParam()
    }

    fun queryPolicyInfo(listener: TaskControl.OnTaskListener) {
        HttpAppFactory.queryPolicyInfo()
            .subscribe(object : NetObserver<PolicyInfoBean>(listener) {
                override fun doOnSuccess(data: PolicyInfoBean?) {
                    super.doOnSuccess(data)
                    data?.let {
                        policyInfo.postValue(it)
                    }
                }
            })
    }

    /**
     * 开始计算保费
     */
    fun caculatePolicyInfo1(listener: TaskControl.OnTaskListener) {
        HttpAppFactory.calculatePolicyInfo(params)
            .subscribe(object : NetObserver<String>(listener) {
                override fun doOnSuccess(data: String?) {
                    super.doOnSuccess(data)
                    params?.policyPrice = data
                    params?.let {
                        policyResult.postValue(it)
                    }

                }
            })
    }

    /**
     * 开始计算保费
     */
    fun caculatePolicyInfo(listener: TaskControl.OnTaskListener): Observable<CommonBean<String>> {
        return HttpAppFactory.calculatePolicyInfo(params)

    }
}