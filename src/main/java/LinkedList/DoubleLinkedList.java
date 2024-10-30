package LinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class DoubleLinkedList<T> implements ListADT<T> {
    protected DoubleNode<T> rear;
    protected DoubleNode<T> front;
    protected int size;
    protected int modCount;

    public DoubleLinkedList() {
        this.rear = this.front = null;
        this.size = this.modCount = 0;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()){
            throw new EmptyCollectionException("List is empty");
        }

        DoubleNode<T> current = this.front;

        this.front = this.front.getNext();

        if (this.front == null) { //verificar se após a remoção do nó a lista fica vazia
            this.rear = null;
        } else {
            this.front.setPrevious(null);
        }

        this.size--;
        modCount++;

        return current.getElement();
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        DoubleNode<T> current = this.rear;

        this.rear = this.rear.getPrevious();

        if (this.rear == null) {
            this.front = null;
        } else {
            this.rear.setNext(null);
        }

        this.size --;
        modCount++;

        return current.getElement();
    }

    @Override
    public T remove(T element) {
        if(isEmpty()){
            throw new EmptyCollectionException("List is empty");
        }

        DoubleNode<T> current = this.find(element);

        if (current == null) {
            throw new ElementNotFoundException("Element not found");
        }

        if (current.getPrevious() == null) {
            this.removeFirst();
        } else if (current.getNext() == null) {
            this.removeLast();
        } else {
            current.getNext().setPrevious(current.getPrevious());
            current.getPrevious().setNext(current.getNext());
            this.size--;
        }

        modCount++;

        return current.getElement();
    }

    @Override
    public T first() {
        if(this.size == 0) {
            throw new EmptyCollectionException("list is empty");
        }
        return this.front.getElement();

    }

    @Override
    public T last() {
        if(this.size == 0) {
            throw new EmptyCollectionException("list is empty");
        }

        return this.rear.getElement();
    }

    @Override
    public boolean contains(T target) {
        return this.find(target) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new listIterator<T>();//instancia e devolve o objeto
    }

    protected DoubleNode<T> find(T element){
        DoubleNode<T> current = this.front;

        while (current != null){
            if (current.getElement().equals(element)){
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    private class listIterator <E> implements Iterator<E> {
        private int expectedModCount;
        private boolean okToRemove;
        private DoubleNode<T> current;

        public listIterator(){
            this.expectedModCount = modCount;
            this.okToRemove = false;
            this.current = front;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public E next() {
            if (modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }

            if (!hasNext()){
                throw new ElementNotFoundException("There's no next element");
            }

            T result = current.getElement();//atual
            this.current = current.getNext();//avança para o próximo
            this.okToRemove = true;

            return (E) result;
        }

        @Override
        public void remove() {
            if(modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }

            if(!okToRemove){
                throw new IllegalStateException();
            }

            E result = next();
            DoubleLinkedList.this.remove((T) result);
            this.okToRemove = false;
        }
    }
}
