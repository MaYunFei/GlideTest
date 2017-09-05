package io.github.mayunfei.simple.manager;

import java.util.Set;

import io.github.mayunfei.simple.RequestManager;

/**
 * Created by AlphaGo on 2017/9/5 0005.
 */

public interface RequestManagerTreeNode {
    /**
     * Returns all descendant {@link RequestManager}s relative to the context of the current
     * {@link RequestManager}.
     */
    Set<RequestManager> getDescendants();

}
