package io.github.mayunfei.simple.apt;

import io.github.mayunfei.simple.Glide;
import io.github.mayunfei.simple.RequestManager;
import io.github.mayunfei.simple.manager.Lifecycle;
import io.github.mayunfei.simple.manager.RequestManagerRetriever;
import io.github.mayunfei.simple.manager.RequestManagerTreeNode;

/**
 * Created by AlphaGo on 2017/9/5 0005.
 */

public class GeneratedRequestManagerFactory implements RequestManagerRetriever.RequestManagerFactory {
    @Override
    public RequestManager build(Glide glide, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode) {
        return new GlideRequests(glide,lifecycle,requestManagerTreeNode);
    }
}
