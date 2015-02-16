/*
The goal of this assignment is to implement elementary data structures using arrays and linked lists, and to introduce you to generics and iterators.
Dequeue: A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports inserting and removing items from either the front or the back of the data structure.
Corner cases: Throw a java.lang.NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque; throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
Performance requirements: Your deque implementation must support each deque operation in constant worst-case time and use space proportional to the number of items currently in the deque. Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.

*/
public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {

    }

    // return the number of items on the deque
    public int size() {

    }

    // insert the item at the front
    public void addFirst(Item item) {

    }

    // insert the item at the end
    public void addLast(Item item) {

    }

    // delete and return the item at the front
    public Item removeFirst() {

    }

    // delete and return the item at the end
    public Item removeLast() {

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {

    }

    // unit testing
    public static void main(String[] args) {

    }
}
