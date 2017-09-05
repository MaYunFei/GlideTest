package io.github.mayunfei.simple.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import io.github.mayunfei.simple.Glide;
import io.github.mayunfei.simple.RequestManager;
import io.github.mayunfei.simple.Util;

/**
 * Created by mayunfei on 17-9-4.
 */

public class RequestManagerRetriever implements Handler.Callback{
    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;
    /**
     * Pending adds for SupportRequestManagerFragments.
     */
    final Map<FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments =
            new HashMap<>();

    //UI 线程 切换
    private volatile RequestManager applicationManager;
    private final Handler handler;
    private final RequestManagerFactory factory;

    public RequestManagerRetriever(RequestManagerFactory factory) {
        this.factory = factory != null ? factory : DEFAULT_FACTORY;
        handler = new Handler(Looper.getMainLooper(), this /* Callback */);
    }

    public RequestManager get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        }
        else if (Util.isOnMainThread() && !(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return get((FragmentActivity) context);
            } else if (context instanceof Activity) {
                return get((Activity) context);
            } else if (context instanceof ContextWrapper) {
                return get(((ContextWrapper) context).getBaseContext());
            }
        }

        return getApplicationManager(context);
    }

    public RequestManager get(FragmentActivity activity){
        if (Util.isOnBackgroundThread()) {
            return get(activity.getApplicationContext());
        } else {
//            assertNotDestroyed(activity); 判断是不是已经销毁
            FragmentManager fm = activity.getSupportFragmentManager();
            return supportFragmentGet(activity, fm, null /*parentHint*/);
        }
    }

    private RequestManager supportFragmentGet(Context context, FragmentManager fm,
                                              Fragment parentHint) {
        SupportRequestManagerFragment current = getSupportRequestManagerFragment(fm, parentHint);
        RequestManager requestManager = current.getRequestManager();
        if (requestManager == null) {
            // TODO(b/27524013): Factor out this Glide.get() call.
            Glide glide = Glide.get(context);
            requestManager =
                    factory.build(glide, current.getGlideLifecycle(), current.getRequestManagerTreeNode());
            current.setRequestManager(requestManager);
        }
        return requestManager;
    }

    SupportRequestManagerFragment getSupportRequestManagerFragment(
            final FragmentManager fm, Fragment parentHint) {
        SupportRequestManagerFragment current =
                (SupportRequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = pendingSupportRequestManagerFragments.get(fm);
            if (current == null) {
                current = new SupportRequestManagerFragment();
                current.setParentFragmentHint(parentHint);
                pendingSupportRequestManagerFragments.put(fm, current);
                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
                handler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget();
            }
        }
        return current;
    }

    private RequestManager getApplicationManager(Context context) {
        // Either an application context or we're on a background thread.
        if (applicationManager == null) {
            synchronized (this) {
                if (applicationManager == null) {
                    // Normally pause/resume is taken care of by the fragment we add to the fragment or
                    // activity. However, in this case since the manager attached to the application will not
                    // receive lifecycle events, we must force the manager to start resumed using
                    // ApplicationLifecycle.

                    // TODO(b/27524013): Factor out this Glide.get() call.
                    Glide glide = Glide.get(context);
                    applicationManager =
                            factory.build(glide, new ApplicationLifecycle(), new EmptyRequestManagerTreeNode());
                }
            }
        }

        return applicationManager;
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }

    /**
     * Used internally to create {@link RequestManager}s.
     */
    public interface RequestManagerFactory {
        RequestManager build(
                Glide glide, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode);
    }

    private static final RequestManagerFactory DEFAULT_FACTORY = new RequestManagerFactory() {
        @Override
        public RequestManager build(Glide glide, Lifecycle lifecycle,
                                    RequestManagerTreeNode requestManagerTreeNode) {
            return new RequestManager(glide, lifecycle, requestManagerTreeNode);
        }
    };
}
