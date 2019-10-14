package com.newland.intelligentdaysystem.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.newland.intelligentdaysystem.activity.WelcomeActivity;

public class CrashHandlerUtil implements Thread.UncaughtExceptionHandler {
    private static String TAG = "CrashHandlerUtil";
    private static CrashHandlerUtil mInstance;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public static CrashHandlerUtil getmInstance() {
        if (null == mInstance) {
            mInstance = new CrashHandlerUtil();
        }
        return  mInstance;
    }

    public void init(Context context) {
        LogUtil.d(TAG, "init");
        this.mContext = context;
        this.mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private boolean handleException(Throwable throwable) {
        if (null == throwable) {
            return true;
        }
        LogUtil.d(TAG, "handleException");
        throwable.printStackTrace();
        return true;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogUtil.d(TAG, "uncaughtException");
        if (!handleException(e) && null != mDefaultUncaughtExceptionHandler) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            LogUtil.d(TAG, "user is not processed, the default processor of the system is processed.");
            mDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            reStartApp();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                e.printStackTrace();
            }
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
        }
    }

    //重启应用
    private void reStartApp() {
        LogUtil.d(TAG, "reStartApp");
        Intent intent = new Intent(mContext, WelcomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent);
    }
}
