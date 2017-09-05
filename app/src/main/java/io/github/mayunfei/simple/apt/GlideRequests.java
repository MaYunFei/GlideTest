package io.github.mayunfei.simple.apt;

import io.github.mayunfei.simple.Glide;
import io.github.mayunfei.simple.RequestManager;
import io.github.mayunfei.simple.manager.Lifecycle;
import io.github.mayunfei.simple.manager.RequestManagerTreeNode;

/**
 * 如果在同一个 activity 或者是 同一个 fragment 会得到同一个 GlideRequests，也就是 RequestManager
 * Created by mayunfei on 17-9-4.
 */

public class GlideRequests extends RequestManager {
    public GlideRequests(Glide glide, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode) {
        super(glide, lifecycle, requestManagerTreeNode);
    }
}
