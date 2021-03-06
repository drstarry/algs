/*
The goal of this assignment is to implement elementary data structures using arrays and linked lists, and to introduce you to generics and iterators.
Dequeue: A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports inserting and removing items from either the front or the back of the data structure.
Corner cases: Throw a java.lang.NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque; throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
Performance requirements: Your deque implementation must support each deque operation in constant worst-case time and use space proportional to the number of items currently in the deque. Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.

*/

import java.util.*;
import java.lang.*;

public class Deque<Item> implements Iterable<Item> {

    private Node Head;
    private Node Tail;
    private int N;

    // nested class to define nodes
    private class Node{
        Item item;
        Node next;
        Node pre;
    }

    // construct an empty deque with Head and Tail pointer
    public Deque() {
        Head = new Node();
        Tail = new Node();
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size() == 0);
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node newItem = new Node();
        newItem.item = item;
        if ((isEmpty())) {
            Head.next = newItem;
            Tail.pre = newItem;
            newItem.next = Tail;
            newItem.pre = Head;
        }
        else {
            newItem.next = Head.next;
            Head.next.pre = newItem;
            Head.next = newItem;
            newItem.pre = Head;
        }
        N++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node newItem = new Node();
        newItem.item = item;
        if (isEmpty()) {
            Head.next = newItem;
            Tail.pre = newItem;
            newItem.next = Tail;
            newItem.pre = Head;
        }
        else {
            newItem.next = Tail;
            newItem.pre = Tail.pre;
            Tail.pre.next = newItem;
            Tail.pre = newItem;
        }
        N++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item firstItem = Head.next.item;
        Head.next = Head.next.next;
        Head.next.pre = Head;
        N--;
        return firstItem;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item lastItem = Tail.pre.item;
        Tail.pre = Tail.pre.pre;
        Tail.pre.next = Tail;
        N--;
        return lastItem;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
       return new dequeIterator<Item>();
    }

    @SuppressWarnings("hiding")
    private class dequeIterator<Item> implements Iterator<Item> {

        private Node cur;
        public dequeIterator() {
            if (size()>0) {
                this.cur = Head.next;
            }
            else
                this.cur = Tail;
        }
        public boolean hasNext() {
            return cur != Tail ;
        }

        @SuppressWarnings("unchecked")
        public Item next() {
            if(!hasNext())
                throw new NoSuchElementException();
            Item temp = (Item) cur.item;
            cur = cur.next;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void print() {
        Iterator<Item> itr = iterator();
        StdOut.print(itr.hasNext());
        StdOut.print("# ");
        while(itr.hasNext()) {
             Object element = itr.next();
             StdOut.print(element + " ");
        }
        StdOut.print("\n");
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> dec = new Deque<Integer>();
        dec.addLast(6);
        dec.print();
        dec.addLast(7);
        dec.print();
        dec.addLast(8);
        dec.print();
        dec.removeFirst();
        dec.print();
        dec.removeLast();
        dec.print();
        dec.removeLast();
        dec.print();
        Iterator<Integer> it = dec.iterator();
        StdOut.println(it.hasNext());
        StdOut.println(dec.isEmpty());
    }
}
