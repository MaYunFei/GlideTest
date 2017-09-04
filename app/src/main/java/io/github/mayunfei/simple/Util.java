package io.github.mayunfei.simple;

import android.os.Looper;

/**
 * Created by mayunfei on 17-9-4.
 */

public class Util {
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

}
