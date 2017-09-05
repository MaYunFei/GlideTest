package io.github.mayunfei.simple.manager;

/**
 * Created by AlphaGo on 2017/9/5 0005.
 */

public class ApplicationLifecycle implements Lifecycle {
    @Override
    public void addListener(LifecycleListener listener) {
        listener.onStart();
    }

    @Override
    public void removeListener(LifecycleListener listener) {

    }
}
