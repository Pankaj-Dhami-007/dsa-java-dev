package tree.binary_tree.basics;

public class BinaryTreeFromArray {
    private Node root;
    private int index = 0;

    public class Node {
        int val;
        Node left;
        Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    // Using Integer[] to allow null values
    public BinaryTreeFromArray(Integer[] preorder) {
        this.index = 0;
        this.root = buildFromPreorder(preorder);
    }

    private Node buildFromPreorder(Integer[] arr) {
        if (index >= arr.length || arr[index] == null) {
            index++;
            return null;
        }

        Node node = new Node(arr[index++]);
        node.left = buildFromPreorder(arr);
        node.right = buildFromPreorder(arr);
        return node;
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
        System.out.println(node.val);

        display(node.left, level + 1);
    }
}
