package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/8.
 */
public class BillInfoSearchParams {
  private String year;//		int	年份
  private String month;//	false	int	月份，从1开始，1代表1月，2代表2月以此类推。
  private String carNo;//	false	String	车牌号
  private String financeType;//	true	int	财务类型，0支出和收入；1支出；2收入
  private String pageNum;//	false	int	页面索引
  private String pageSize;//	false	int	页面记录条数
  private String feeType;//	false	int	1 运费，2保费。默认1运费。支出搜索时请传0或者1。

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
}
