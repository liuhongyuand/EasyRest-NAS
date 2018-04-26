package com.easyrest.network.router;

import java.util.HashMap;
import java.util.Map;

public class PathNode {

    private String path;

    private Map<String, PathNode> childPathMap = new HashMap<>();

    public PathNode(String path) {
        if (path.startsWith("{") && path.endsWith("}")){
            path = "*";
        }
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, PathNode> getChildPathMap() {
        return childPathMap;
    }

    public void putChildPathMap(PathNode child) {
        this.childPathMap.put(child.getPath(), child);
    }

}
