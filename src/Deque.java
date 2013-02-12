
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michal
 */
public class Deque<Item> implements Iterable<Item> {
    
    private Node first = null;
    private Node last = null;
    private int dSize = 0;
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    public Deque() {
        // construct an empty deque
    }
    public boolean isEmpty() {
        // is the deque empty?
        return first == null;
    }
    public int size() {
        // return the number of items on the deque
        return dSize;
    }
    public void addFirst(Item item) {
        // insert the item at the front
        if (item == null) { throw new java.lang.NullPointerException(); }
        if (dSize == 0) {
            //DQueue is empty
            first = new Node();
            first.item = item;
            //first.next = first.prev = null;
            last = first;
        }
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.prev = null;
            oldfirst.prev = first;
        }
        dSize++;
    }
    public void addLast(Item item) {
        // insert the item at the end
        if (item == null) 
            throw new java.lang.NullPointerException();
        if (dSize == 0) {
            //DQueue is empty
            last = new Node();
            last.item = item;
            //last.next = last.prev = null;
            first = last;
        }
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.prev = oldlast;
            last.next = null;
            oldlast.next = last;
        }  
        dSize++;
    }
    public Item removeFirst() {
        // delete and return the item at the front
        if (dSize == 0)
            throw new java.util.NoSuchElementException();
        Item item;
        if (dSize == 1) {
            item = first.item;
            first = null;
            last = null;
        }
        else {
            item = first.item;
            first.next.prev = null;
            first = first.next;
        }
        dSize--;
        return item;
    }
    public Item removeLast() {
        // delete and return the item at the end
        if (dSize == 0)
            throw new java.util.NoSuchElementException();
        Item item;
        if (dSize == 1) {
            item = last.item;
            first = null;
            last = null;
        }
        else {
            item = last.item;
            last.prev.next = null;
            last = last.prev;
        }
        dSize--;
        return item;
    }
    
    @Override
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new DListIterator();
    }
    
    private class DListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }
    
    public static void main(String[] args) {
        Deque<Double> dq = new Deque<Double>();
        
        dq.addFirst(1.0);
        dq.addLast(2.0);
        assert !dq.isEmpty();
        assert dq.removeFirst() == 1.0;
        assert dq.removeLast() == 2.0;
        assert dq.isEmpty();  
        StdOut.println("All tests passed");
    }
    
}
