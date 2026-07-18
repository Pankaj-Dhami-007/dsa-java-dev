package tree.binary_tree.tree_client;

import tree.binary_tree.basics.BinaryTreeLevelOrder;

public class BinaryTreeClient_3 {

    public static void main(String[] args) {
        Integer[] levelOrder = {1, 2, 3, 4, 5, 6, 7};
        BinaryTreeLevelOrder tree = new BinaryTreeLevelOrder(levelOrder);
        tree.levelOrderDisplay();
    }
}
/*
output-->

1
2 3
4 5 6 7
 */
