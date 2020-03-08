package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/8.
 */
public class AccountFlowParams extends PageParams {
  private int  flowtype;//	true	string	1余额明细2待入账明细
  private String  startDate;//	false	string	流水开始时间(yyyy-MM-dd)
  private String  endDate;//	false	string	流水结束时间(yyyy-MM-dd)

    public int getFlowtype() {
        return flowtype;
    }

    public void setFlowtype(int flowtype) {
        this.flowtype = flowtype;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
