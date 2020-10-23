package tk.fishfish.simple.framework;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认路由（前缀树路由算法）
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public enum DefaultRouter implements Router {

    /**
     * 单实例
     */
    INSTANCE;

    private static final String PATH = "/";
    private static final String NAMING_PARAM_START = ":";
    private static final String WILDCARD = "*";

    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";
    private static final String HTTP_PUT = "PUT";
    private static final String HTTP_DELETE = "DELETE";

    private final Tree.Node root;

    DefaultRouter() {
        this.root = new Tree.Node();
        this.root.setId("/");
        this.root.setName("/");
    }

    @Override
    public Router get(String path, Handler handler) {
        checkArgs(path, handler);
        add(HTTP_GET, path, handler);
        return this;
    }

    @Override
    public Router post(String path, Handler handler) {
        checkArgs(path, handler);
        add(HTTP_POST, path, handler);
        return this;
    }

    @Override
    public Router put(String path, Handler handler) {
        checkArgs(path, handler);
        add(HTTP_PUT, path, handler);
        return this;
    }

    @Override
    public Router delete(String path, Handler handler) {
        checkArgs(path, handler);
        add(HTTP_DELETE, path, handler);
        return this;
    }

    @Override
    public Handler match(String path, String method, Map<String, Object> namingParams) {
        if (path == null) {
            throw new IllegalArgumentException("路径不能为空");
        }
        path = path.substring(1);
        String[] names = path.split(PATH);
        Tree.Node node = root;
        for (String name : names) {
            List<Tree.Node> children = node.getChildren();
            if (children == null) {
                return null;
            }
            Tree.Node exactNode = null;
            Tree.Node wildcardNode = null;
            for (Tree.Node child : children) {
                if (child.getId().equals(name)) {
                    exactNode = child;
                }
                if (WILDCARD.equals(child.getId())) {
                    wildcardNode = child;
                    namingParams.put(child.getName(), name);
                }
            }
            node = exactNode == null ? wildcardNode : exactNode;
            if (node == null) {
                return null;
            }
        }
        if (node.getMethods().contains(method)) {
            return node.getHandler();
        }
        return null;
    }

    private void checkArgs(String path, Handler handler) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("路径不能为空");
        }
        if (PATH.equals(path)) {
            throw new IllegalArgumentException("路径不合法");
        }
        if (!path.startsWith(PATH)) {
            throw new IllegalArgumentException("路径必须以'/'开始");
        }
        if (handler == null) {
            throw new IllegalArgumentException("处理器不能为空");
        }
    }

    private void add(String method, String path, Handler handler) {
        path = path.substring(1);
        String[] names = path.split(PATH);
        Tree.Node node = root;
        for (String name : names) {
            node = find(node, name);
        }
        if (node.getHandler() == null) {
            node.setHandler(handler);
        } else {
            if (!handler.equals(node.getHandler())) {
                throw new IllegalArgumentException("处理器以存在，重复注册");
            }
        }
        Set<String> methods = node.getMethods();
        if (methods == null) {
            methods = new HashSet<>();
            node.setMethods(methods);
        }
        methods.add(method);
    }

    private Tree.Node find(Tree.Node parent, String name) {
        String id = name;
        if (name.startsWith(NAMING_PARAM_START)) {
            id = WILDCARD;
            name = name.substring(1);
        }
        List<Tree.Node> children = getChildren(parent);
        for (Tree.Node child : children) {
            if (child.getId().equals(id)) {
                return child;
            }
        }
        Tree.Node node = new Tree.Node();
        node.setId(id);
        node.setName(name);
        children.add(node);
        return node;
    }

    private List<Tree.Node> getChildren(Tree.Node node) {
        List<Tree.Node> nodes = node.getChildren();
        if (nodes == null) {
            nodes = new ArrayList<>();
            node.setChildren(nodes);
        }
        return nodes;
    }

    @Override
    public String toString() {
        return "DefaultRouter{" +
                "root=" + root +
                '}';
    }

}
