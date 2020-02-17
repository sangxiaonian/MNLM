package com.hongniu.thirdlibrary.map;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * 作者： ${PING} on 2018/9/11.
 * 地图上的导航状态消息被点击
 */
public class MapClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       moveToFront(context);
    }

    private void moveToFront(Context context) {
        // 枚举进程
        ActivityManager mAm = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (context instanceof Activity) {
            mAm.moveTaskToFront(((Activity) context).getTaskId(), 0);
        } else {
            //获得当前运行的task
            List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
            if (taskList != null && taskList.size() == 2) {
                for (ActivityManager.RunningTaskInfo rti : taskList) {
                    if (rti.id != 1) {
                        mAm.moveTaskToFront(rti.id, 0);
                    }
                }
            }
        }
    }
}
