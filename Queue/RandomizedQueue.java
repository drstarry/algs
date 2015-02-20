/*
Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure.
Corner cases: The order of two or more iterators to the same randomized queue must be mutually independent; each iterator must maintain its own random order. Throw a java.lang.NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue; throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
Performance requirements: Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time and use space proportional to the number of items currently in the queue. That is, any sequence of M randomized queue operations (starting from an empty queue) should take at most cM steps in the worst case, for some constant c. Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; and construction in linear time; you may use a linear amount of extra memory per iterator.

*/

import java.util.*;
import java.lang.*;

/*
public class RandomizedQueueLL<Item> implements Iterable<Item> {

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

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random;
        if (size() == 1) {
            random = 0;
        }
        else
            random = StdRandom.uniform(0, size());
        Node cur = new Node();
        cur = Head.next;
        while (random > 0 && cur.next != Tail) {
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
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random;
        if (size() == 1) {
            random = 0;
        }
        else
            random = StdRandom.uniform(0, size());
        Node cur = Head.next;
        while (random > 0 && cur.next != Tail) {
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
            while(curNode!=Tail && curIdx!=size()) {
                itemShuffle[shuffle[curIdx]] = (Item) curNode.item;
                curNode = curNode.next;
                curIdx++;
            }
            curIdx = 0;
        }

        public boolean hasNext() {
            return curIdx != size();
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
        dec.enqueue(17);
        dec.print();
        dec.enqueue(11);
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
        // dec.enqueue(92);
        // dec.print();
        StdOut.println("---");
        int n = 10;
        while (n>0) {
            StdOut.println(dec.sample());
            n--;
        }
        StdOut.println("---");
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.print();
        dec.dequeue();
        Iterator<Integer> it = dec.iterator();
        StdOut.println(it.hasNext());
        StdOut.println(dec.isEmpty());
    }

}
*/

public class RandomizedQueue<Item> implements Iterable<Item> {

    private ResizingArrayStackOfStrings array;
    private int N;

    @SuppressWarnings("hiding")
    private class ResizingArrayStackOfStrings<Item> {

        private Object[] s;
        public ResizingArrayStackOfStrings()
        {
            s = new Object[1];
        }

        public void push(Item item)
        {
            if (N == s.length)
                resize(2 * s.length);
            s[N++] = item;
        }

        public void resize(int capacity)
        {
            Object[] copy = new Object[capacity];
            for (int i = 0; i < N; i++)
                copy[i] = (Item) s[i];
            s = copy;
        }

        public Item pop()
        {
            Item item = (Item) s[--N];
            s[N] = null;
            if (N > 0 && N == s.length/4)
                resize(s.length/2);
            return item;
        }

        public Item get(int i) {
            return (Item) (s[i]);
        }

        public void set(int i, Item item) {
            s[i] = item;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = new ResizingArrayStackOfStrings();
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
            throw new NullPointerException();
        }
        array.push(item);
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(0, size());
        Item temp;
        int last = size() - 1;
        if (random != size() - 1)
        {
            temp = (Item) array.get(random);
            array.set(random, (Item) array.get(last));
        }
        else
            temp = (Item) array.get(last);
        array.pop();
        return temp;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(0, size());
        Item temp = (Item) array.get(random);
        return temp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
       return new rdequeIterator<Item>();
    }

    @SuppressWarnings("hiding")
    private class rdequeIterator<Item> implements Iterator<Item> {

        private int curIdx;
        private int[] shuffle;

        public rdequeIterator() {
            this.curIdx = 0;
            shuffle = new int[size()];
            for (int i = 0; i<size(); i++)
                shuffle[i] = i;
            ShuffleArray(shuffle);
            curIdx = 0;
        }

        public boolean hasNext() {
            return curIdx != size();
        }

        @SuppressWarnings("unchecked")
        public Item next() {
            if(!hasNext())
                throw new NoSuchElementException();
            Item temp = (Item) array.get(shuffle[curIdx]);
            curIdx++;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void ShuffleArray(int[] arr)
        {
            int index, temp;
            Random random = new Random();
            for (int i = arr.length - 1; i > 0; i--)
            {
                index = random.nextInt(i + 1);
                temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
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
        StdOut.println(dec.size());
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
        dec.enqueue(17);
        dec.print();
        dec.enqueue(11);
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
        // dec.enqueue(92);
        // dec.print();
        StdOut.println("---");
        int n = 10;
        while (n>0) {
            StdOut.println(dec.sample());
            n--;
        }
        StdOut.println("---");
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.dequeue();
        dec.print();
        dec.dequeue();
        Iterator<Integer> it = dec.iterator();
        StdOut.println(it.hasNext());
        StdOut.println(dec.isEmpty());
    }

}
