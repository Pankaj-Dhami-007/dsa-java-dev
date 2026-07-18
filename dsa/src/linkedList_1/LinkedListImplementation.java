package linkedList_1;

public class LinkedListImplementation {

    public static void main(String[] args) {
        Linked_List ll = new Linked_List();
        ll.addFirst(10);
        ll.addFirst(20);
        ll.addFirst(30);
        ll.display();
        System.out.print("LinkedList size is --->"+ll.getSize()+" and head is "+ll.getHead());
        ll.addLast(100);
        ll.addLast(200);
        System.out.println();
        ll.display();
        System.out.println("LinkedList size is --->"+ll.getSize()+" tail is "+ll.getTailData());

        ll.addAtIdx(1000, 3);
        ll.display();
        ll.deleteFirst();
        ll.display();
        ll.deleteLast();
        System.out.println();
        ll.display();
        ll.deleteAtIdx(2);
        System.out.println();
        ll.display();
    }
}
