package tree.binary_tree.tree_client;

import tree.binary_tree.basics.BinaryTree;

public class BinaryTreeClient_1 {
    public static void main(String[] args) {

        BinaryTree bt = new BinaryTree();
        System.out.println("Tree Structure:");
        bt.display();

        System.out.println("\nPre-order traversal:");
        bt.preOrder();

        System.out.println("Height: " + bt.height());
        System.out.println("Total nodes: " + bt.countNodes());
    }
}
