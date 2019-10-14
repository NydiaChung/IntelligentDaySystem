package com.newland.intelligentdaysystem;

import android.app.Application;
import android.content.Context;

import com.newland.intelligentdaysystem.utils.CrashHandlerUtil;

/**
 * Created by jy on 2018/5/9.
 */

public class IntelligentDaySystemApplication extends Application {
    private static final String TAG = "IntelligentDaySystemApplication";

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CrashHandlerUtil.getmInstance().init(mContext);
    }

    public static Context getInstance() {
        return mContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
