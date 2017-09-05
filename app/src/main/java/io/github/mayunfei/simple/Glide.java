package io.github.mayunfei.simple;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;

import io.github.mayunfei.simple.apt.GeneratedRequestManagerFactory;
import io.github.mayunfei.simple.manager.RequestManagerRetriever;

/**
 * Created by mayunfei on 17-9-4.
 */

public class Glide implements ComponentCallbacks {
    private static volatile Glide glide;
    private static volatile boolean isInitializing;
    private final RequestManagerRetriever requestManagerRetriever;

    public Glide(RequestManagerRetriever requestManagerRetriever) {
        //模拟 glide 初始化
        this.requestManagerRetriever = requestManagerRetriever;
    }

    public static RequestManager with(Context context) {
        return Glide.get(context).getRequestManagerRetriever().get(context);
    }

    public RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }

    public static Glide get(Context context) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    checkAndInitializeGlide(context);
                }
            }
        }
        return glide;
    }

    private static void checkAndInitializeGlide(Context context) {
        // 你不能在 注册的组件中调用 Glide.get(context)
        // In the thread running initGlide(), one or more classes may call Glide.get(context).
        // Without this check, those calls could trigger infinite recursion.
        if (isInitializing) {
            throw new IllegalStateException("You cannot call Glide.get() in registerComponents(),"
                    + " use the provided Glide instance instead");
        }
        isInitializing = true;
        initializeGlide(context);
        isInitializing = false;
    }

    private static void initializeGlide(Context context) {
        //使用glide builder 创建 glide
        //1 通过注解的方式 获得 model
        //或者 通过 Manifest文件获得
        //将model 关联到 glide builder
        //
        Glide glide = new Glide(new RequestManagerRetriever(new GeneratedRequestManagerFactory()));
        context.getApplicationContext().registerComponentCallbacks(glide); //将系统状态注册到Glide中
        Glide.glide = glide;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void onLowMemory() {

    }
}
