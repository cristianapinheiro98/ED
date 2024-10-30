package Queue;

import Exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements QueueADT<T> {
    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int size;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }


    @Override
    public void enqueue(T element) {//adicionar ao fim da fila
        LinearNode<T> newNode = new LinearNode<>(element);

        if (this.front == null) {//se for o 1º elemento a adicionar à fila
            this.front = newNode;
        } else {
            this.rear.setNext(newNode);
        }

        this.rear = newNode;
        this.size++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {//remover ao início da fila
        if (isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        LinearNode<T> current = this.front;//variável auxiliar (current) para guardar o front da lista
        if (this.size == 1) {//significa que a lista só tem um elemento para remover
            this.front = null;
            this.rear = null;
        } else {
            this.front = this.front.getNext();//o novo front passa a ser o next do front que vai ser removido
            current.setNext(null);//e o current passa a apontar para null
        }

        this.size--;

        return current.getElement();
    }

    @Override
    public T first() {
        if(isEmpty()) {
            throw new EmptyCollectionException("Can't remove. The linked list is currently empty.");
        }

        return this.front.getElement();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    public String toString() {//podemos começar a usar o stringbuilder
        String s = "";
        LinearNode<T> current = this.front;

        while(current != null) {
            if (current.getNext() == null) {//significa que a lista só tem 1 elemento
                s += "Element: " + current.getElement() + " Next: " + "null \n";
            } else {
                s += "Element: " + current.getElement() + " Next: " + current.getNext().getElement() + "\n";
            }

            current = current.getNext();
        }

        return s;
    }
}
