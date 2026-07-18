package tree.binary_tree.basics;

public class StaticBinaryTree {
    private Node root;

    public class Node {
        int val;
        Node left;
        Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    // Method 1: Build complete binary tree
    public static StaticBinaryTree buildCompleteTree(int[] values) {
        StaticBinaryTree tree = new StaticBinaryTree();
        if (values.length == 0) return tree;

        tree.root = buildComplete(values, 0);
        return tree;
    }

    private static Node buildComplete(int[] values, int index) {
        if (index >= values.length) return null;

        Node node = new StaticBinaryTree().new Node(values[index]);
        node.left = buildComplete(values, 2 * index + 1);
        node.right = buildComplete(values, 2 * index + 2);
        return node;
    }

    // Method 2: Build specific tree structures
    public static StaticBinaryTree buildSampleTree1() {
        StaticBinaryTree tree = new StaticBinaryTree();
        tree.root = tree.new Node(1);
        tree.root.left = tree.new Node(2);
        tree.root.right = tree.new Node(3);
        tree.root.left.left = tree.new Node(4);
        tree.root.left.right = tree.new Node(5);
        tree.root.right.right = tree.new Node(6);
        return tree;
    }

    public static StaticBinaryTree buildSampleTree2() {
        StaticBinaryTree tree = new StaticBinaryTree();
        tree.root = tree.new Node(10);
        tree.root.left = tree.new Node(5);
        tree.root.right = tree.new Node(15);
        tree.root.left.left = tree.new Node(2);
        tree.root.left.right = tree.new Node(7);
        tree.root.right.left = tree.new Node(12);
        tree.root.right.right = tree.new Node(20);
        return tree;
    }

    public void display() {
        display(root, 0);
    }

    private void display(Node node, int level) {
        if (node == null) return;

        display(node.right, level + 1);

        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }
        System.out.println("└─ " + node.val);

        display(node.left, level + 1);
    }
}
