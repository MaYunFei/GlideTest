package io.github.mayunfei.simple.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.FragmentActivity;

import io.github.mayunfei.simple.Glide;
import io.github.mayunfei.simple.RequestManager;
import io.github.mayunfei.simple.Util;

/**
 * Created by mayunfei on 17-9-4.
 */

public class RequestManagerRetriever {
    //UI 线程 切换
    private volatile RequestManager applicationManager;


    public RequestManager get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        } else if (Util.isOnMainThread() && !(context instanceof Application)) {
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
}
