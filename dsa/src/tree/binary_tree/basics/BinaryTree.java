package tree.binary_tree.basics;

import java.util.Scanner;
/*

public class Node {
    int val;
    Node left;
    Node right;
}
Represents a single node in the binary tree
Contains:
val: the integer value stored in the node
left: reference to left child node
right: reference to right child node

2. Tree Construction Process
The createTree() method builds the tree recursively using pre-order traversal:
value
hasLeftChild? (true/false)
hasRightChild? (true/false)

Example Input Sequence:
1        // root value
true     // has left child?
2        // left child value
false    // left child has no left child?
true     // left child has right child?
3        // left child's right child value
false    // no left grandchild?
false    // no right grandchild?
false    // root has no right child?

This would create:

    1
   /
  2
   \
    3

    3. Display Method
    Format: leftChildValue <-- nodeValue --> rightChildValue
    Uses . to represent null children

    How the Code Executes
    Step-by-Step Execution:

 1.Main method creates BinaryTree object
2.Constructor calls createTree() to build the tree
3.createTree() works recursively:
Reads node value
Creates new node
If has left child: recursively creates left subtree
If has right child: recursively creates right subtree
4.display() prints the tree structure

Example Input/Output

1
true
2
true
4
false
false
true
3
false
false

Resulting Tree:
      1
     / \
    2   3
   /
  4

  Display Output:

 <--1-->
2<--2-->3
4<--4-->.
.<--3-->.
 */
public class BinaryTree {

    public class Node{
        int val;
        Node left;
        Node right;
    }
    Scanner sc = new Scanner(System.in);
    private Node root;

    public BinaryTree(){
          root = createTree();
    }

    private Node createTree(){

        int item = sc.nextInt();
        Node nn = new Node();
        nn.val = item;
        Boolean hlc = sc.nextBoolean();
        if(hlc){
           nn.left = createTree();
        }
        Boolean hrc = sc.nextBoolean();
        if(hrc){
           nn.right = createTree();
        }
        return nn;
    }

    public void display(){
        display(root);
    }
    private void display(Node node){
        if(node == null) return;
        String s = "";
        s = "<--"+node.val+"-->";
        if(node.left != null){
            s = node.left.val + s;
        }else{
            s = "."+s;
        }

        if(node.right != null){
            s = s+ node.right.val;
        }else{
            s = s+".";
        }
        System.out.println(s);
        display(node.left);
        display(node.right);
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node) {
        if (node == null) return;
        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }

    public boolean find(int value) {
        return find(root, value);
    }

    private boolean find(Node node, int value) {
        if (node == null) return false;
        if (node.val == value) return true;
        return find(node.left, value) || find(node.right, value);
    }


    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}
