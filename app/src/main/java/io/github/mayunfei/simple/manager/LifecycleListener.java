package io.github.mayunfei.simple.manager;

/**
 * Created by mayunfei on 17-9-4.
 */

public interface LifecycleListener {
    void onStart();

    void onStop();

    void onDestroy();
}
