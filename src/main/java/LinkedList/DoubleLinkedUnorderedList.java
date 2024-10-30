package LinkedList;


import Exceptions.ElementNotFoundException;

public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {
    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (isEmpty()){
            super.front = newNode;
            super.rear = newNode;
        } else {
            newNode.setNext(super.front);
            super.front.setPrevious(newNode);
            super.front = newNode;

        }
        super.size++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if(isEmpty()){
            super.front = newNode;
            super.rear = newNode;
        } else {
            super.rear.setNext(newNode);
            newNode.setPrevious(super.rear);
            super.rear = newNode;
        }

        super.size++;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) {
        if(element == null){
            throw new ElementNotFoundException("element not found");
        }

        DoubleNode<T> newNode = new DoubleNode<>(element);
        DoubleNode<T> current = super.find(target);

        if(current == null){
            throw new ElementNotFoundException("element not found");
        }

//não faz sentido ter uma verificação (current == super.front) e chamar o addToFront pq neste caso não queremos adicionar à frente, mas, sim a seguir a um target, porém, se esse target for o último elemento da lista já faz sentido adicionar o novo no ao rear
        if (current == super.rear){
            addToRear(element);
        } else {
            newNode.setPrevious(current);
            newNode.setNext(current.getNext());

            current.getNext().setPrevious(newNode);
            current.setNext(newNode);
            super.size++;
            modCount++;

        }

    }
}
