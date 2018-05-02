package tech.dbgsoftware.easyrest.network.router;

import tech.dbgsoftware.easyrest.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PathStore {

    private static final Map<String, PathNode> ROUTER_MAPPING = new HashMap<>();

    public static String queryPath(String url){
        StringBuilder modifiedUrl = new StringBuilder();
        String[] paths = StringUtils.split(url, "/");
        PathNode parent = null;
        PathNode matchAny = new PathNode("*");
        for (String path : paths) {
            modifiedUrl.append("/");
            PathNode pathNode = new PathNode(path);
            if (parent == null) {
                parent = pathNode;
                if (ROUTER_MAPPING.containsKey(parent.getPath())) {
                    modifiedUrl.append(parent.getPath());
                    parent = ROUTER_MAPPING.get(parent.getPath());
                } else {
                    if (ROUTER_MAPPING.containsKey(matchAny.getPath())){
                        modifiedUrl.append("*");
                        parent = ROUTER_MAPPING.get(matchAny.getPath());
                    } else {
                        return null;
                    }
                }
            } else {
                if (!parent.getChildPathMap().containsKey(pathNode.getPath())){
                    if (!parent.getChildPathMap().containsKey(matchAny.getPath())) {
                        return null;
                    } else {
                        modifiedUrl.append("*");
                        parent = parent.getChildPathMap().get(matchAny.getPath());
                    }
                } else {
                    modifiedUrl.append(pathNode.getPath());
                    parent = parent.getChildPathMap().get(pathNode.getPath());
                }

            }
        }
        return modifiedUrl.toString();
    }

    public static void putPath(String url){
        String[] paths = StringUtils.split(url, "/");
        PathNode parent = null;
        for (String path : paths) {
            PathNode pathNode = new PathNode(path);
            if (parent == null) {
                parent = pathNode;
                if (ROUTER_MAPPING.containsKey(parent.getPath())) {
                    parent = ROUTER_MAPPING.get(parent.getPath());
                } else {
                    ROUTER_MAPPING.put(parent.getPath(), parent);
                }
            } else {
                if (!parent.getChildPathMap().containsKey(pathNode.getPath())) {
                    parent.getChildPathMap().put(pathNode.getPath(), pathNode);
                }
                parent = parent.getChildPathMap().get(pathNode.getPath());
            }
        }
    }

}
