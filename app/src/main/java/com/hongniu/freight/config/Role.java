package com.hongniu.freight.config;

/**
 * 作者：  on 2020/2/7.
 * 角色
 */
public enum  Role {
    SHIPPER(0,"托运人"),//托运人
    CARRIER(1,"承运人"),//承运人
    DRIVER(2,"司机"),//司机
    PLATFORM(4,"平台"),//司机
    ;
    private final String name;
    private final int type;
    Role(int type,String name) {
       this. type=type;
       this.name=name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
