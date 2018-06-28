package deques;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int count = 0;
    public Deque() {
        first = null;
        last = null;
    }
    public boolean isEmpty(){
        return count==0;
    }
    public int size(){
       return count;
    }
    public void addFirst(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        Node newFirstNode =  new Node();
        newFirstNode.setValue(item);
        Node oldFirstNode = first;
        if(first != null){
            first.setPrevious(newFirstNode);
        }
        newFirstNode.setNext(oldFirstNode);
        first = newFirstNode;
        count++;
        if(count == 1){
            last = newFirstNode;
        }
    }
    public void addLast(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }

        Node newLastNode = new Node();
        newLastNode.setValue(item);
        Node oldLastNode = last;
        if(oldLastNode != null){
            oldLastNode.setNext(newLastNode);
        }
        newLastNode.setPrevious(oldLastNode);
        last = newLastNode;
        count++;
        if(count == 1){
            first = newLastNode;
        }
    }
    public Item removeFirst(){
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        if(count == 1){
            count--;
            return first.getValue();
        }
        Node currentFirst = first;
        first = currentFirst.getNext();
        first.setPrevious(null);
        count--;
        return currentFirst.getValue();
    }
    public Item removeLast(){
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        if(count == 1){
            count--;
            return first.getValue();
        }
        Node currentLast = last;
        last = currentLast.getPrevious();
        currentLast.setPrevious(null);
        count--;
        return currentLast.getValue();
    }
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    public static void main(String[] args){
        Deque<Integer> dd = new Deque<>();
        for(int num = 0; num <= 50 ; num++){
            dd.addFirst(num);
        }
        for(int num = 0; num <= 50 ; num++){
            System.out.println(dd.removeLast());
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.getValue();
            current = current.getNext();
            return item;
        }
    }

    private class Node {
        private Node next;
        private Node previous;
        private Item value;

        private Node getNext() {
            return next;
        }

        private void setNext(Node next) {
            this.next = next;
        }

        private Node getPrevious() {
            return previous;
        }

        private void setPrevious(Node previous) {
            this.previous = previous;
        }

        private Item getValue() {
            return value;
        }

        private void setValue(Item value) {
            this.value = value;
        }
    }
}
