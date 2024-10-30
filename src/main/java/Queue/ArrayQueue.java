package Queue;

import Exceptions.EmptyCollectionException;

public class ArrayQueue<T> implements QueueADT<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private static final int FACTOR = 2;

    private int size;
    private T[] queue;
    private int front;
    private int rear;

    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }
    public ArrayQueue(int initialCapacity) {
        this.size = 0;
        this.queue = (T[]) (new Object[initialCapacity]);
        this.front = 0;
        this.rear = 0;
    }

    private void expandCapacity() {
        T[] newQueue = (T[]) (new Object[this.queue.length * FACTOR]);
        int positionCopy = this.front;

        for (int i = 0; i < this.size; i++) {
            newQueue[i] = this.queue[positionCopy];
            positionCopy = (positionCopy + 1) % this.queue.length; // Usar o módulo para circular no array
        }

        this.queue = newQueue;
        this.front = 0;//no array copiado front passa a ser a posição 0
        this.rear = this.size;//no array copiado o rear passa a ser o máximo de posições ocupadas
    }

    @Override
    public void enqueue(T element) {
       if (size == queue.length) {
            expandCapacity();
        }

        this.queue[this.rear] = element;
        this.rear = (this.rear + 1) % this.queue.length;
        this.size++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack is empty");
        }

        T result = this.queue[this.front];
        this.queue[this.front] = null;
        this.front = (this.front + 1) % this.queue.length;
        this.size--;

        return result;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack is empty");
        }

        return this.queue[this.front];
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    public String toString() {//ver!!!
        String s = "";
        int positionPrint = this.front;

        for(int i = 0; i < this.size; i++) {
            s += this.queue[positionPrint];
            positionPrint = (positionPrint + 1) % this.queue.length; // Usando módulo para circular
        }
        return s;
    }
}
