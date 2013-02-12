
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
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int N = 0;
    //private int rDSize = 0;

    public RandomizedQueue() {
        // construct an empty randomized queue
        arr = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        // is the queue empty?
        return N == 0;
    }

    public int size() {
        // return the number of items on the queue
        return N;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        arr[N++] = item;
        if (N >= arr.length) {
            resize(2 * N);
        }
    }

    public Item dequeue() {
        // delete and return a random item
        if (N == 0) {
            throw new java.util.NoSuchElementException();
        }
        int ind = StdRandom.uniform(N);
        Item item;

        item = arr[ind];
        arr[ind] = arr[--N];
        arr[N] = null;


        if (N > 0 && N < arr.length / 4) {
            resize(arr.length / 2);
        }
        return item;

    }

    public Item sample() {
        // return (but do not delete) a random item
        if (N == 0) {
            throw new java.util.NoSuchElementException();
        }
        return arr[StdRandom.uniform(N)];
    }

    @Override
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private Item[] rArr;
        private int rInd = 0;

        public RandomIterator() {
            rArr = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                rArr[i] = arr[i];
            }
            StdRandom.shuffle(rArr);
        }

        @Override
        public boolean hasNext() {
            return rInd < N;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return rArr[rInd++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private void resize(final int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        assert rq.isEmpty();
        double avg = 0;
        int nTest = 30;
        final int maxInt = 100;
        for (int i = 0; i < maxInt; i++) {
            rq.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
            StdOut.print(rq.sample() + " ");
        }
        StdOut.println("---------------------------");
        for (int i = 0; i < nTest; i++) {
            int cumm = 0;
            for (int it : rq) {
                cumm += it;
            }
            //StdOut.println("---------------------------");
            avg += (double) cumm / rq.size();
        }
        avg /= nTest;
        StdOut.println("avg: " + avg);
        StdOut.println("All tests passed");
        double epsilon = 0.1;
        assert (avg - (maxInt - 1) / 2.0) < epsilon;
    }
}
