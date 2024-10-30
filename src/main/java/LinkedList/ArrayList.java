package LinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class ArrayList<T> implements ListADT<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private static final int FACTOR = 2;

    protected T[] list;
    protected int count;
    protected int modCount;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        this.list = (T[]) (new Object[initialCapacity]);
        this.count = 0;
        this.modCount = 0;
    }

    @Override
    public T removeFirst() {
        if (count == 0){
            throw new EmptyCollectionException("List is empty");
        }

        T result = this.list[0];

        for (int i = 0; i < count - 1; i++){
            this.list[i] = this.list[i + 1];
        }

        this.list[--this.count] = null;
        this.modCount++;

        return result;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (count == 0){
            throw new EmptyCollectionException("List is empty");
        }

        this.count--;
        T result = this.list[count];
        this.list[count] = null;
        this.modCount++;

        return result;
    }

    @Override
    public T remove(T element) {
        if (count == 0){
            throw new EmptyCollectionException("List is empty");
        }

        int pos = find(element);
        if(pos == -1){
            throw new ElementNotFoundException("Element not found");
        }

        T result = this.list[pos];

        for (int i = pos; i < this.count - 1; i++){
            this.list[i] = this.list[i + 1];
        }

        this.list[--this.count] = null;
        this.modCount++;

        return result;
    }

    @Override
    public T first() {
        if(isEmpty()){
            throw new EmptyCollectionException("List is empty");
        }

        return this.list[0];
    }

    @Override
    public T last() {
        if(isEmpty()){
            throw new EmptyCollectionException("List is empty");
        }

        return this.list[count - 1];
    }

    @Override
    public boolean contains(T target) {
        return find(target) != -1;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIiterator<T>();
    }

    protected void expandCapacity() {//protected
        T[] array_tmp = (T[]) (new Object[this.list.length * FACTOR]);
        for (int i = 0; i < this.list.length; i++) {
            array_tmp[i] = this.list[i];
        }
        this.list = array_tmp;
    }

    protected int find(T element){
        for (int i = 0; i < this.count; i++) {
            if (this.list[i].equals(element)){
                return i;
            }
        }
        return -1;
    }


    public class ArrayIiterator<E> implements Iterator<E> {
        protected int cursor = 0;
        private int expectedModCount;
        private boolean okToRemove;

        public ArrayIiterator(){
            this.cursor = 0;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return cursor < count;
        }

        @Override
        public E next() {
            if (modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }

            if (!hasNext()){
                throw new ElementNotFoundException("There's no next element");
            }

            this.okToRemove = true;

            return (E) list[this.cursor++];
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }

            if (!okToRemove){
                throw new IllegalStateException();
            }

            ArrayList.this.remove(list[--this.cursor]);

            this.okToRemove = false;
        }
    }
}


