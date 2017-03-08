package com.football.yuedong.happysports;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.football.yuedong.happysports.utils.FileLog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

/**
 * Created by liuyanjun on 2015/12/14.
 */
public class ApplicationLoader extends Application {

    public static final String TAG = ApplicationLoader.class.getSimpleName();

    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;
    private static volatile boolean applicationInited = false;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
        Fresco.initialize(applicationContext);
//        postInitChat();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static void postInitApplication() {
        if (applicationInited) {
            return;
        }
    }


    public void postInitChat(){
        applicationContext = this;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null ||!processAppName.equalsIgnoreCase(applicationContext.getPackageName())) {
            FileLog.d(TAG, "enter the service process!");
            //"com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(true);
        EMClient.getInstance().init(applicationContext, options);
        EMClient.getInstance().setDebugMode(true);
    }

    public static String getAppName(int pID){
        String processName = null;
        ActivityManager am = (ActivityManager) applicationContext.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = applicationContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }

    public static void logout(){
        EMClient.getInstance().logout(true);
    }
}
