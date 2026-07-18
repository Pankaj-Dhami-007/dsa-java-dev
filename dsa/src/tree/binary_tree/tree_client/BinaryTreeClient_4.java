package tree.binary_tree.tree_client;

import tree.binary_tree.basics.StaticBinaryTree;

public class BinaryTreeClient_4 {
    public static void main(String[] args) {

        // Method 1: Complete tree from array
        int[] values = {1, 2, 3, 4, 5, 6, 7};
        StaticBinaryTree tree1 = StaticBinaryTree.buildCompleteTree(values);
        tree1.display();

       // Method 2: Pre-built trees
        StaticBinaryTree tree2 = StaticBinaryTree.buildSampleTree1();
        tree2.display();

        StaticBinaryTree tree3 = StaticBinaryTree.buildSampleTree2();
        tree3.display();
    }
}
