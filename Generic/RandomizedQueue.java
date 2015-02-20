/*
Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure.
Corner cases: The order of two or more iterators to the same randomized queue must be mutually independent; each iterator must maintain its own random order. Throw a java.lang.NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue; throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
Performance requirements: Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time and use space proportional to the number of items currently in the queue. That is, any sequence of M randomized queue operations (starting from an empty queue) should take at most cM steps in the worst case, for some constant c. Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; and construction in linear time; you may use a linear amount of extra memory per iterator.

*/

import java.util.*;
import java.lang.*;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node Head;
    private Node Tail;
    private int N;

    // nested class to define nodes
    private class Node{
        Item item;
        Node next;
        Node pre;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        Head = new Node();
        Tail = new Node();
        N = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return (size() == 0);
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NoSuchElementException();
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

    // delete and return a random item
    public Item dequeue() {
        int random = StdRandom.uniform(1, size());
        Node cur = new Node();
        cur = Head.next;
        while (random>0 && cur.next!=Tail) {
            cur = cur.next;
            random--;
        }
        cur.pre.next = cur.next;
        cur.next.pre = cur.pre;
        N--;
        return cur.item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        int random = StdRandom.uniform(1, size());
        Node cur = new Node();
        cur = Head.next;
        while (random>0 && cur.next!=Tail) {
            cur = cur.next;
            random--;
        }
        return cur.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
       return new rdequeIterator<Item>();
    }

    @SuppressWarnings("hiding")
    private class rdequeIterator<Item> implements Iterator<Item> {

        private Node curNode;
        private int curIdx;
        private int[] shuffle;
        private Object[] itemShuffle;

        public rdequeIterator() {
            this.curNode = Head.next;
            this.curIdx = 0;
            shuffle = new int[size()];
            itemShuffle = new Object[size()];
            for (int i = 0; i<size(); i++)
                shuffle[i] = i;
            ShuffleArray(shuffle);
            for (int i = 0; i<size(); i++)
                StdOut.print(shuffle[i]+",");
            StdOut.print("\n");
            while(curNode!=Tail && curIdx!=size()) {
                itemShuffle[shuffle[curIdx]] = (Item) curNode.item;
                curNode = curNode.next;
                curIdx++;
            }
            curIdx = 0;
        }

        public boolean hasNext() {
            return curIdx != size() ;
        }

        @SuppressWarnings("unchecked")
        public Item next() {
            if(!hasNext())
                throw new NoSuchElementException();
            Item temp = (Item) itemShuffle[curIdx];
            curIdx++;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void ShuffleArray(int[] array)
        {
            int index, temp;
            Random random = new Random();
            for (int i = array.length - 1; i > 0; i--)
            {
                index = random.nextInt(i + 1);
                temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }
    }

    private void print() {
        Iterator<Item> itr = iterator();
        while(itr.hasNext()) {
             Object element = itr.next();
             StdOut.print(element + " ");
        }
        StdOut.print("\n");
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> dec = new RandomizedQueue<Integer>();
        dec.enqueue(6);
        dec.print();
        dec.enqueue(7);
        dec.print();
        dec.enqueue(8);
        dec.print();
        dec.dequeue();
        dec.print();
        dec.enqueue(9);
        dec.print();
        dec.enqueue(10);
        dec.print();
        dec.enqueue(8);
        dec.print();
        dec.enqueue(8);
        dec.print();
        dec.dequeue();
        dec.print();
        dec.dequeue();
        dec.print();
        dec.enqueue(25);
        dec.print();
        dec.print();
        dec.print();
        dec.print();
        dec.enqueue(87);
        dec.print();
        dec.enqueue(4);
        dec.print();
        dec.enqueue(92);
        dec.print();
    }

}
