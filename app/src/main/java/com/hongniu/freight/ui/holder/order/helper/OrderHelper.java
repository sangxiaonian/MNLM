package com.hongniu.freight.ui.holder.order.helper;

import com.hongniu.freight.config.Role;
import com.hongniu.freight.ui.holder.order.helper.control.HelperControl;
import com.hongniu.freight.utils.Utils;

import java.util.Map;

/**
 * 作者：  on 2020/2/8.
 */
public class OrderHelper implements HelperControl {
    HelperControl helper;
   Role role;
    public OrderHelper(Role role) {
        this.role=role;
        if (role==Role.SHIPPER){
            helper=new TYRHelper(Utils.getShipperType());
        }else if (role==Role.CARRIER){
            helper=new CYRHelper();
        }else if (role==Role.DRIVER){
            helper=new DriverHelper();
        }
    }

    @Override
    public String getStatus(int status) {
        return helper.getStatus(status);
    }

    /**
     * 根据状态，获取到指定荒唐的按钮
     *
     * @param status
     * @return
     */
    @Override
    public Map<String, Integer> getButtons(int status) {
        return helper.getButtons(status);
    }
}
