package Exercises;

import Queue.LinkedQueue;
import Queue.QueueADT;

public class OrdenatedQueue {


    public <T extends Comparable<T>> QueueADT<T> ordenateQueue(QueueADT<T> queue1, QueueADT<T> queue2) {
        QueueADT<T> newQueue = new LinkedQueue<T>();

        // Intercalar as filas enquanto ambas não estiverem vazias
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            if (queue1.first().compareTo(queue2.first()) <= 0) {//o elemento mais pequeno é adicionado à nova queue
                newQueue.enqueue(queue1.dequeue());
            } else {
                newQueue.enqueue(queue2.dequeue());
            }
        }

        // Adicionar o restante dos elementos de queue1, se houver
        while (!queue1.isEmpty()) {
            newQueue.enqueue(queue1.dequeue());
        }

        // Adicionar o restante dos elementos de queue2, se houver
        while (!queue2.isEmpty()) {
            newQueue.enqueue(queue2.dequeue());
        }

        return newQueue;
    }
}

