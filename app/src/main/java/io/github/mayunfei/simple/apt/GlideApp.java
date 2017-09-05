package io.github.mayunfei.simple.apt;

import android.content.Context;

import io.github.mayunfei.simple.*;

/**
 * GlideApp 模拟
 * Created by mayunfei on 17-9-4.
 */

public class GlideApp {
    private GlideApp() {
    }
    public static GlideRequests with(Context context) {
        return (GlideRequests) Glide.with(context);
    }
}
