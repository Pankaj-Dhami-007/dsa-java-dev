package linkedList_1;

import java.util.NoSuchElementException;

public class Linked_List {

    public class Node{
        int data;
        Node next;
    }
    private Node head;
    private Node tail;
    private int size;
    public void addFirst(int data){
        Node nn = new Node();
        nn.data = data;
        if(size == 0){
            head = nn;
            tail = nn;
        } else {
            nn.next = head;
            head = nn;
        }
        size++;
    }

    public void addLast(int data){
        Node nn = new Node();
        nn.data = data;
        if(size == 0){
            addFirst(data);
        } else {
            tail.next = nn;
            tail = nn;
            size++;
        }
    }
public void addAtIdx(int data, int idx){
    if (idx < 0 || idx > size) {
        throw new IndexOutOfBoundsException("Invalid index: " + idx);
    }
    if (idx == 0) {
        addFirst(data);
        return;
    }

    if (idx == size) {
        addLast(data);
        return;
    }
        Node nn = new Node();
        nn.data = data;
    Node prev = findKthNode(idx - 1);
    nn.next = prev.next;
    prev.next = nn;
    size++;
}

//deletion
public Node deleteFirst() {
    if (head == null) {
        throw new NoSuchElementException("Cannot delete from empty list");
    }

    Node deletedNode = head;

    if (head == tail) {
        head = null;
        tail = null;
    } else {
        head = head.next;
    }

    deletedNode.next = null;  // Clean up reference
    size--;
    return deletedNode;
}

    public Node deleteLast(){
        if (head == null) {
            throw new NoSuchElementException("Cannot delete from empty list");
        }
        Node deletedNode = tail;
        if (head == tail) {
            // Only one node in list
            head = null;
            tail = null;
        } else{
            // Find the second-to-last node
            Node prev = head;
            while (prev.next != tail) {
                prev = prev.next;
            }

            // Update references
            prev.next = null;
            tail = prev;  // Update tail to second-to-last node
        }
        size--;
        return deletedNode;
    }

    public Node deleteAtIdx(int idx) {
        if (head == null) {
            throw new NoSuchElementException("Cannot delete from empty list");
        }

        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + idx);
        }

        if (idx == 0) {
            return deleteFirst();
        }

        if (idx == size - 1) {
            return deleteLast();
        }

        // Find the node before the one to delete
        Node prev = head;
        for (int i = 0; i < idx - 1; i++) {
            prev = prev.next;
        }

        Node deletedNode = prev.next;
        prev.next = prev.next.next; // Bypass the node to delete

        deletedNode.next = null; // Clean up reference
        size--;
        return deletedNode;
    }

    public void display(){
        Node temp = head;
        while(temp != null){
            System.out.print(temp.data+"->");
            if (temp.next == null) {
                System.out.print("null");
            }
            temp = temp.next;
        }
        System.out.println();
    }

    public int getSize(){
        return size;
    }
    public int getHead(){
        return head.data;
    }

    public int getTailData(){
        return tail.data;
    }

    private Node findKthNode(int k){
        Node kthNode = head;
        for(int i = 0; i < k; i++){
            kthNode = kthNode.next;
        }

        return kthNode;
    }
}
