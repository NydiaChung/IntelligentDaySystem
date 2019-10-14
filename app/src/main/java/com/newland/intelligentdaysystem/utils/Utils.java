package com.newland.intelligentdaysystem.utils;

public class Utils {
    private static String TAG = "Utils";
    private static long mLastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - mLastClickTime < 500) {
            LogUtil.d(TAG, "fast double click");
            return true;
        }
        mLastClickTime = time;
        return false;
    }
}
