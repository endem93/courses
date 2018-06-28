package deques;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queueArray;
    private int numberOfItems;

    public RandomizedQueue(){
        queueArray = (Item[]) new Object[2];
        numberOfItems = 0;
    }

    public boolean isEmpty() {
        return numberOfItems == 0;
    }

    public int size() {
        return numberOfItems;
    }


    private void resize(int capacity) {
        assert capacity >= numberOfItems;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfItems; i++) {
            temp[i] = queueArray[i];
        }
        queueArray = temp;
    }



    public void enqueue(Item item) {
        if(item == null){
            throw new IllegalArgumentException();
        }

        if (numberOfItems == queueArray.length) resize(2* queueArray.length);
        queueArray[numberOfItems++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        if(numberOfItems==1){
            return queueArray[0];
        }
        int random = StdRandom.uniform(0,numberOfItems-1);

        Item item = queueArray[random];
        queueArray[random] = queueArray[numberOfItems-1];
        queueArray[numberOfItems-1] = null;
        numberOfItems--;

        if (numberOfItems > 0 && numberOfItems == queueArray.length/4) resize(queueArray.length/2);
        return item;
    }


    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");

        return queueArray[numberOfItems -1];
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public static void main(String[] args){
        RandomizedQueue<Integer> dd = new RandomizedQueue<>();
        for(int num = 0; num <= 3 ; num++){
            dd.enqueue(num);
        }

        System.out.println(dd.dequeue());
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;
        private int[] myOrder = new int[numberOfItems];

        public ReverseArrayIterator() {
            i = numberOfItems -1;
            for(int order = 0; order <= numberOfItems -1; order++){
                myOrder[order] = order;
            }
            StdRandom.shuffle(myOrder);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queueArray[myOrder[i--]];
        }
    }

}
