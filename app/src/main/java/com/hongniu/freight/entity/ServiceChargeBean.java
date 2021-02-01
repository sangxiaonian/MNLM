package com.hongniu.freight.entity;

/**
 * 作者：  on 2021/1/28.
 */
public class ServiceChargeBean {
  float serviceCharge;//	true	object	手续费
  float money;//	true	object	运费
  float totalMoney;//	true	object	总费用
  float policyMoney;//	true	object	保险费 如果没有买保险默认是0
  float charge;//	true	手续费率 如0.06

    public float getServiceCharge() {
        return serviceCharge;
    }

    public float getCharge() {
        return charge;
    }

    public void setCharge(float charge) {
        this.charge = charge;
    }

    public void setServiceCharge(float serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getPolicyMoney() {
        return policyMoney;
    }

    public void setPolicyMoney(float policyMoney) {
        this.policyMoney = policyMoney;
    }
}
