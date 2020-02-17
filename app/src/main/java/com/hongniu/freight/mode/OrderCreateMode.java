package com.hongniu.freight.mode;

import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.freight.control.OrderCreateControl;
import com.hongniu.freight.entity.TranMapBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者：  on 2020/2/17.
 */
public class OrderCreateMode implements OrderCreateControl.IOrderCreateMode {

    List<String> days;
    List<List<String>> hours;
    List<List<List<String>>> minutes;
    private TranMapBean startInfor;
    private TranMapBean endInfor;
    private boolean isInsurance;//是否购买保险 true 是
    private int payType =-1;//付款方式 -1未知 0现付 1到付
    List<String> payWays;//付款方式

    public OrderCreateMode() {
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        payWays = new ArrayList<>();
        payWays.add("现付");
        payWays.add("到付");
    }

    /**
     * @param result 发货地址
     */
    @Override
    public void saveStartInfo(TranMapBean result) {
        this.startInfor = result;
    }

    /**
     * @param result 收货地址
     */
    @Override
    public void saveEndInfo(TranMapBean result) {
        this.endInfor = result;

    }

    /**
     * 获取发货时间年月日
     *
     * @return
     */
    @Override
    public Observable<Integer> getTimeInfo() {
        if (!CollectionUtils.isEmpty(days)) {
            return Observable.just(1);
        } else {
            return Observable.just(1)
                    .map(new Function<Integer, Integer>() {
                        @Override
                        public Integer apply(Integer integer) throws Exception {
                            days.clear();
                            days.addAll(getCurentMonthDays(90));
                            hours.clear();
                            minutes.clear();
//                        days.remove(0);
                            days.add(0, "今天");
                            for (int i = 0; i < days.size(); i++) {

                                List<String> hour = new ArrayList<>();
                                hours.add(hour);

                                List<List<String>> minute = new ArrayList<>();
                                minutes.add(minute);
                                for (int j = 0; j < 24; j++) {
                                    List<String> min = new ArrayList<>();

                                    if (i == 0 && j == 0) {
                                        hour.add("立即取货");
                                        ArrayList<String> strings = new ArrayList<>();
                                        minute.add(strings);
                                        for (int k = 0; k < 60; k++) {
                                            strings.add("");
                                        }
                                    }
                                    for (int k = 0; k < 60; k++) {
                                        min.add(String.format(Locale.CHINESE, "%d分", (k)));
                                    }
                                    minute.add(min);
                                    hour.add(String.format(Locale.CHINESE, "%d点", (j)));
                                }
                            }
                            return integer;
                        }
                    })
                    .compose(RxUtils.<Integer>getSchedulersObservableTransformer());

        }
    }

    @Override
    public List<List<List<String>>> getMinutes() {
        return minutes;
    }

    @Override
    public List<String> getDays() {
        return days;
    }

    @Override
    public List<List<String>> getHours() {
        return hours;
    }

    /**
     * 是否购买保险
     *
     * @return
     */
    @Override
    public boolean getIsInsurance() {
        return isInsurance;
    }

    /**
     * 储存当前是是否购买保险
     *
     * @param isInsurance true 是
     */
    @Override
    public void saveIsInsurance(boolean isInsurance) {
        this.isInsurance = isInsurance;
    }

    /**
     * 显示付款方式
     *
     * @return
     */
    @Override
    public List<String> getPayWaysInfo() {
        return payWays;
    }

    /**
     * 获取当前付款方式
     *
     * @return
     */
    @Override
    public int getPayType() {
        return payType;
    }

    /**
     * 储存当前付款方式
     *
     * @param payType
     */
    @Override
    public void savePayType(int payType) {
        this.payType=payType;
    }


    /**
     * 获取当前月份的日期
     *
     * @return
     */
    public List<String> getCurentMonthDays(int count) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");

        List<String> times = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String format = simpleDateFormat.format(calendar.getTime());
            times.add(format);
        }
        return times;
    }


}
