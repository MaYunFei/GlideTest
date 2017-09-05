package io.github.mayunfei.simple.manager;

import java.util.Collections;
import java.util.Set;

import io.github.mayunfei.simple.RequestManager;

/**
 * Created by AlphaGo on 2017/9/5 0005.
 */

public class EmptyRequestManagerTreeNode implements RequestManagerTreeNode {
    @Override
    public Set<RequestManager> getDescendants() {
        return Collections.emptySet();
    }
}
