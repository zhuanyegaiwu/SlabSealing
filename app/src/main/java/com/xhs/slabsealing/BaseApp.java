package com.xhs.slabsealing;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.xhs.slabsealing.constant.Constant;
import com.xhs.slabsealing.utils.SPUtils;

/**
 * 作者: 布鲁斯.李 on 2017/12/4 10 41
 * 邮箱: lzp_lizhanpeng@163.com
 */

public class BaseApp extends Application {

    public static BaseApp instance=null;
    public String ip=null;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        Logger.init();
        ip=getIp();
    }

    private String getIp() {
        String ip = (String) SPUtils.getParam(this, Constant.IP, "");
        return ip;
    }

    public static BaseApp getInstance(){
        return instance;
    }
}
