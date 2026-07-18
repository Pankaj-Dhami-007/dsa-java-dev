package tree.binary_tree.basics_2;

public class SizeMaxSumHeight {
static int sizeNr =0;
    public static class Node{
        int val;//0
        Node left;//null
        Node right;//null
        public Node(int val){
            this.val =val;
        }
    }

    //traversal
    public static void preorder(Node root){
        if(root == null){
            return;
        }
        System.out.print(root.val+" ");
        sizeNr++;
        preorder(root.left);
        preorder(root.right);
    }

    public static int size(Node root){
        if(root == null) return 0;
        int ts = size(root.left) + size(root.right)+1;
        return ts;
    }

    public static int sum(Node root){
        if(root == null) return 0;
       return sum(root.left) + sum(root.right) + root.val;
    }

    public static int max(Node root){ //a = max(left, right) , max(a, root)
        if(root == null) return Integer.MIN_VALUE;
        int a = root.val;
        int b = max(root.left);
        int c = max(root.right);
        return Math.max(a, Math.max(b, c));
    }

    public static int height(Node root){
        if(root == null) return 0;
        if(root.left == null && root.right == null) return 0;
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    public static void nthLevel(Node root, int n){
        if(root == null){
            return;
        }
        if(n==1){
            System.out.print(root.val+" ");
            return;
        }
        nthLevel(root.left, n-1);
        nthLevel(root.right, n-1);
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
        preorder(root);
        System.out.println();
        System.out.println("size-> "+ sizeNr+" not rec size ");
        System.out.println(size(root));
        System.out.println(sum(root));
        System.out.println(max(root));
        System.out.println(height(root));

        int level = height(root) +1;
        for(int i = 1; i<= level; i++){
            nthLevel(root, i);
            System.out.println();
        }
    }
}
