package LinkedList;

import Exceptions.ElementNotFoundException;

public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {
    public DoubleLinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) {
        if(element == null){
            throw new ElementNotFoundException("element not found");
        }
        Comparable<T> tmp_element = (Comparable<T>) element;

        DoubleNode<T> newNode = new DoubleNode<>(element);

        DoubleNode<T> current = super.front;//assumir que o current é o front

        if(isEmpty()){
            super.front = newNode;
            super.rear = newNode;
        } else if (tmp_element.compareTo(super.rear.getElement()) >= 0){//adiciona ao rear
            newNode.setPrevious(super.rear);
            newNode.setNext(null);
            super.rear.setNext(newNode);
            super.rear = newNode;
        } else if( tmp_element.compareTo(super.front.getElement()) <= 0){//adiciona ao front
            newNode.setNext(super.front);
            newNode.setPrevious(null);
            super.front.setPrevious(newNode);
            super.front = newNode;
        } else {//adiciona em qualquer lugar
            while(tmp_element.compareTo(current.getElement()) > 0){
                current = current.getNext();
            }

            newNode.setNext(current);//o next do novo nó passa a apontar para current
            newNode.setPrevious(current.getPrevious());//o previous do novo nó passa a apontar para o nó anterior guardado em current.getPrevious
            current.getPrevious().setNext(newNode);//o next nó anterior passa a apontar para o novo nó
            current.setPrevious(newNode);//o previous current passa a apontar para o novo nó
        }
        super.size++;
        modCount++;
    }
}
