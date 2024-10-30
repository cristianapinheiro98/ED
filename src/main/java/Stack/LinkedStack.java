package Stack;

import Exceptions.EmptyCollectionException;

public class LinkedStack<T> implements StackADT<T> {
    private LinearNode<T> top;
    private int size;

    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(T element) {//adicionamos sempre ao topo da pilha
        LinearNode<T> newNode = new LinearNode<>(element);
        newNode.setNext(this.top);//novo nó passa a apontar para o nó que estava no topo
        this.top = newNode;//e agora o topo passa a ser o novo nó

        this.size++;
    }

    @Override

    public T pop() throws EmptyCollectionException {//remove sempre ao topo da stack;como só temos a var top, mesmo que o nó removido seja o único na pilha não precisamos fazer validação extra do empty pq o nó fica sempre a apontar para null(linha 34)
        if (isEmpty()) {
            throw new EmptyCollectionException("stack is empty.");
        }

        LinearNode<T> current = this.top;//variável auxiliar (current) para guardar o topo

        this.top = this.top.getNext();//o novo topo passa a ser o next do topo que vai ser removido

        current.setNext(null);//e o current passa a apontar para null

        this.size--;

        return current.getElement();
    }

    @Override
    public T peek() throws EmptyCollectionException {
        return this.top.getElement();
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
    public String toString() {
        LinearNode<T> current = this.top;
        String s = "";

        while (current != null) {
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
