package com.football.yuedong.happysports;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.football.yuedong.happysports.utils.AndroidUtilities;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

/**
 * Created by liuyanjun on 2016/2/26.
 */
public class InternalServer extends Service {


    private static final String TAG = InternalServer.class.getSimpleName();

    private Handler mMainHandler;
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        mMainHandler = new Handler(Looper.getMainLooper());
        HandlerThread thread = new HandlerThread(TAG, android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        //register connection listener;
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {

        }
    }

    //connection listener;
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            //TODO has been connect;
        }
        @Override
        public void onDisconnected(final int error) {
            AndroidUtilities.runOnUIThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登陆
                    } else {
                        if (NetUtils.hasNetwork(InternalServer.this)) {
                            //连接不到聊天服务器
                        } else {
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }
}
