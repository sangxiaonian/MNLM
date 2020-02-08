package com.hongniu.freight.ui.holder.order.helper.control;

import java.util.Map;

/**
 * 作者：  on 2020/2/8.
 */
public interface HelperControl {
    String getStatus(int status);

    /**
     * 根据状态，获取到指定荒唐的按钮
     * @return
     */
    Map<String, Integer> getButtons(int status);
}
