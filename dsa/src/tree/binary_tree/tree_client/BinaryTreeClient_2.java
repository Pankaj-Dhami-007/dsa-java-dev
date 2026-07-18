package tree.binary_tree.tree_client;

import tree.binary_tree.basics.BinaryTreeFromArray;

public class BinaryTreeClient_2 {

    public static void main(String[] args) {
        Integer[] treeData = {1, 2, 4, null, null, 5, null, null, 3, null, 6, null, null};
        BinaryTreeFromArray tree = new BinaryTreeFromArray(treeData);
        tree.display();
    }
}
/*
output->

   3
      6
1
      5
   2
      4
 */
