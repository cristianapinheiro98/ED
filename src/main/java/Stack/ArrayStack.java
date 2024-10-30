package Stack;

import Exceptions.EmptyCollectionException;

public class ArrayStack<T> implements StackADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    private final int FACTOR = 2;

    private int top;
    private T[] stack;

    public ArrayStack() {
        this.top = 0;
        this.stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }


    public ArrayStack(int initialCapacity) {
        this.top = 0;
        this.stack = (T[]) (new Object[initialCapacity]);
    }


    @Override

    public void push(T element) {
        if (size() == stack.length) {
            expandCapacity();
        }

        this.stack[top] = element;
        this.top++;
    }

    private void expandCapacity() {
        T[] stack_tmp = (T[]) (new Object[this.stack.length * FACTOR]);

        for (int i = 0; i < this.stack.length; i++) {
            stack_tmp[i] = this.stack[i];
        }

        this.stack = stack_tmp;
    }


    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        this.top--;
        T result = this.stack[top];
        this.stack[this.top] = null;


        /*T result = this.stack[this.top - 1];
        this.stack[--this.top] = null;
        //outra forma que pensei de fazer a mesma coisa que as linhas de cima*/

        return result;
    }


    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack is empty");
        }
        return this.stack[this.top - 1];

    }

    @Override
    public boolean isEmpty() {

        return size() == 0;
    }

    @Override
    public int size() {

        return this.top;
    }

    public String toString() {
        if(this.top == 0){
            throw new EmptyCollectionException("Can´t print because the stack is empty");
        }
        String s = "";
        for (int i = this.top - 1; i > -1 ; i--) {
            s += stack[i];
        }
        return s;
    }
}


