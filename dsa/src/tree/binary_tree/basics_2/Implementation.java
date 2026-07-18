package tree.binary_tree.basics_2;

public class Implementation {

    public static class Node{
        int val;//0
        Node left;//null
        Node right;//null
        public Node(int val){
            this.val =val;
        }
    }

    public static void display(Node root){
        if(root == null){
            return;
        }
        System.out.print(root.val+" -> ");
        System.out.print(root.left != null ? root.left.val + " " : "null ");
        System.out.print(root.right != null ? root.right.val + " " : "null ");
        System.out.println();
        display(root.left);
        display(root.right);
    }

    public static void main(String[] args) {
        Node root = new Node(1);//root.val = 1;
        Node a = new Node(2);
        Node b = new Node(3);
        root.left = a;
        root.right= b;
        Node c = new Node(4);
        Node d = new Node(5);
        a.left = c;
        a.right = d;
        Node e = new Node(6);
        b.right = e;
        //System.out.println(root.val);
        display(root);

    }
}
