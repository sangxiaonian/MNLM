package com.hongniu.thirdlibrary.picture.utils;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.hongniu.thirdlibrary.BuildConfig;
import com.hongniu.thirdlibrary.picture.ImageInforBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 作者：  on 2020/3/3.
 */
public class PicUtils {
    /**
     * 对图片进行锅炉筛选
     *
     * @param imageType 1 意见反馈、个人资料 2 户型图  3 房源图片
     * @param datas     原始数据
     * @param paths     从相册选择的图片数据
     */
    public static void filtImage(int imageType, List<ImageInforBean> datas, List<LocalMedia> paths) {
        //第一步，移除数据源中被删除的图片
        List<ImageInforBean> temp = new ArrayList<>();
        Map<String, LocalMedia> pathList = new TreeMap<>();
        for (LocalMedia media : paths) {
            pathList.put(media.getPath(), media);

        }
        for (ImageInforBean imageInforBean : datas) {
            //有路径，表示从相册中选取，地址不包含，新选择的数据没有指定数据，删除
            if (!TextUtils.isEmpty(imageInforBean.getPath()) && !pathList.keySet().contains(imageInforBean.getImageAddress())) {
                temp.add(imageInforBean);
            }

        }
        datas.removeAll(temp);
        //第二步，将新的数据添加到源数据中
        for (String key : pathList.keySet()) {
            boolean isRepeat = false;
            for (ImageInforBean data : datas) {
                //只要找到一个有相同地址的，表示已经包含有此数据了
                if (key.equals(data.getPath()) || key.equals(data.getPathUrl())) {
                    isRepeat = true;
                }
            }
            //如果没有重复，就把图片放入原始数据中
            if (!isRepeat) {
                ImageInforBean bean = new ImageInforBean();
                String path;
                if (DeviceUtils.getSdkVersion() > 28) {
                    path = pathList.get(key).getAndroidQToPath();
                } else {
                    path = key;
                }
                bean.setPathOriginal(key);//原始路径
                bean.setPath(path);
                bean.setType(imageType);
                datas.add(bean);
            }
        }

    }

    public static String getPath(LocalMedia localMedia) {
        String path;
        if (DeviceUtils.getSdkVersion() > 28) {
            if (localMedia.isCompressed()) {
                path = localMedia.getCompressPath();
            } else {
                path = localMedia.getPath();
            }
        } else {
            if (localMedia.isCompressed()) {
                path = localMedia.getCompressPath();
            } else {
                path = localMedia.getPath();
            }
        }
        return path;
    }
}
