package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/2/24.
 * 身份认证的基本信息
 */
public class BaseAttestationInfo {

    protected boolean	 isStaff	;//true	平台员工身份 1:是 0:否
    protected int	 isDriver	;//true	司机身份1:是 0:否
    protected int	 isPersonCarrier	;//true	个人承运人身份 1:是 0:否
    protected int	 isCompanyCarrier	;//true	公司承运人身份 1:是 0:否
    protected int	 isCompanyShipper	;//true	公司托运人身份 1:是 0:否
    protected int	 isPersonShipper	;//true	个人托运人身份 1:是 0:否
    protected int	 isStaffStatus	;//true	平台员工身份状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int	 isDriverStatus	;//true	认证司机状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int	 isPersonShipperStatus	;//true	认证个人托运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int	 isPersonCarrierStatus	;//true	认证个人承运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int	 isCompanyShipperStatus	;//true	认证公司托运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
    protected int	 isCompanyCarrierStatus	;//true	认证公司承运人状态 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败



    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public int getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(int isDriver) {
        this.isDriver = isDriver;
    }

    public int getIsPersonCarrier() {
        return isPersonCarrier;
    }

    public void setIsPersonCarrier(int isPersonCarrier) {
        this.isPersonCarrier = isPersonCarrier;
    }

    public int getIsCompanyCarrier() {
        return isCompanyCarrier;
    }

    public void setIsCompanyCarrier(int isCompanyCarrier) {
        this.isCompanyCarrier = isCompanyCarrier;
    }

    public int getIsCompanyShipper() {
        return isCompanyShipper;
    }

    public void setIsCompanyShipper(int isCompanyShipper) {
        this.isCompanyShipper = isCompanyShipper;
    }

    public int getIsPersonShipper() {
        return isPersonShipper;
    }

    public void setIsPersonShipper(int isPersonShipper) {
        this.isPersonShipper = isPersonShipper;
    }

    public int getIsStaffStatus() {
        return isStaffStatus;
    }

    public void setIsStaffStatus(int isStaffStatus) {
        this.isStaffStatus = isStaffStatus;
    }

    public int getIsDriverStatus() {
        return isDriverStatus;
    }

    public void setIsDriverStatus(int isDriverStatus) {
        this.isDriverStatus = isDriverStatus;
    }

    public int getIsPersonShipperStatus() {
        return isPersonShipperStatus;
    }

    public void setIsPersonShipperStatus(int isPersonShipperStatus) {
        this.isPersonShipperStatus = isPersonShipperStatus;
    }

    public int getIsPersonCarrierStatus() {
        return isPersonCarrierStatus;
    }

    public void setIsPersonCarrierStatus(int isPersonCarrierStatus) {
        this.isPersonCarrierStatus = isPersonCarrierStatus;
    }

    public int getIsCompanyShipperStatus() {
        return isCompanyShipperStatus;
    }

    public void setIsCompanyShipperStatus(int isCompanyShipperStatus) {
        this.isCompanyShipperStatus = isCompanyShipperStatus;
    }

    public int getIsCompanyCarrierStatus() {
        return isCompanyCarrierStatus;
    }

    public void setIsCompanyCarrierStatus(int isCompanyCarrierStatus) {
        this.isCompanyCarrierStatus = isCompanyCarrierStatus;
    }
}
