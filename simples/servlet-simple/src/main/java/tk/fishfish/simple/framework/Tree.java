package tk.fishfish.simple.framework;

import java.util.List;
import java.util.Set;

/**
 * 树
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class Tree {

    /**
     * 节点
     */
    private List<Node> nodes;

    public static class Node {

        /**
         * 唯一标志（同一层级）
         */
        private String id;

        /**
         * 路径参数，或命名参数
         */
        private String name;

        /**
         * 支持方法
         */
        private Set<String> methods;

        /**
         * 处理器
         */
        private Handler handler;

        /**
         * 下一级
         */
        private List<Node> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<String> getMethods() {
            return methods;
        }

        public void setMethods(Set<String> methods) {
            this.methods = methods;
        }

        public Handler getHandler() {
            return handler;
        }

        public void setHandler(Handler handler) {
            this.handler = handler;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", methods=" + methods +
                    ", handler=" + handler +
                    ", children=" + children +
                    '}';
        }

    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "nodes=" + nodes +
                '}';
    }

}
