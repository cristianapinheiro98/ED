package LinkedList;


import Exceptions.ElementNotFoundException;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T>{

    public ArrayUnorderedList(){
        super();
    }

    public ArrayUnorderedList(int initialCapacity){
        super(initialCapacity);
    }

    @Override
    public void addToFront(T element) {
        if (super.count == super.list.length){
            super.expandCapacity();
        }

        for (int i = super.count; i > 0; i--) {
            super.list[i] = super.list[i - 1];
        }

        super.list[0] = element;
        super.count++;
    }

    @Override
    public void addToRear(T element) {
        if(super.count == super.list.length){
            super.expandCapacity();
        }

        super.list[super.count++] = element;
        super.modCount++;
    }

    @Override
    public void addAfter(T element, T target) {
        if (super.count == super.list.length){
            super.expandCapacity();
        }

        int pos = super.find(target);
        if (pos == -1){
            throw new ElementNotFoundException("Couldn't find element");
        }

        for (int i = super.count; i > pos; i--) {
            super.list[i] = super.list[i - 1];
        }

        super.list[pos + 1] = element;
        this.count++;
        super.modCount++;
    }
}

