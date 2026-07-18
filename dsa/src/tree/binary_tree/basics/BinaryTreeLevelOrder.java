package tree.binary_tree.basics;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeLevelOrder {
    private Node root;

    public class Node {
        int val;
        Node left;
        Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    // Efficient level-order construction
    public BinaryTreeLevelOrder(Integer[] levelOrder) {
        if (levelOrder == null || levelOrder.length == 0 || levelOrder[0] == null) {
            return;
        }

        this.root = new Node(levelOrder[0]);
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < levelOrder.length) {
            Node current = queue.poll();

            // Left child
            if (i < levelOrder.length && levelOrder[i] != null) {
                current.left = new Node(levelOrder[i]);
                queue.add(current.left);
            }
            i++;

            // Right child
            if (i < levelOrder.length && levelOrder[i] != null) {
                current.right = new Node(levelOrder[i]);
                queue.add(current.right);
            }
            i++;
        }
    }

    public void levelOrderDisplay() {
        if (root == null) return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Node current = queue.poll();
                System.out.print(current.val + " ");

                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
            System.out.println();
        }
    }
}
