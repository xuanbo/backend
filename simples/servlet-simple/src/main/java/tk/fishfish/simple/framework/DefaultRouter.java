package tk.fishfish.simple.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认路由（前缀树算法）
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

    /**
     * 每个请求方法的处理器对应一棵前缀树
     */
    private final Map<String, Tree.Node> roots = new HashMap<>();

    DefaultRouter() {
        roots.put(HTTP_GET, createRoot());
        roots.put(HTTP_POST, createRoot());
        roots.put(HTTP_PUT, createRoot());
        roots.put(HTTP_DELETE, createRoot());
    }

    private Tree.Node createRoot() {
        Tree.Node root = new Tree.Node();
        root.setId("/");
        root.setName("/");
        return root;
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
        Tree.Node root = roots.get(method);
        if (root == null) {
            return null;
        }
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
        return node.getHandler();
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
        Tree.Node root = roots.get(method);
        if (root == null) {
            throw new IllegalArgumentException("请求方法不支持: " + method);
        }
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

}
