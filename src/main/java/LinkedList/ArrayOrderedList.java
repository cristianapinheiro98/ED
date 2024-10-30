package LinkedList;


import Exceptions.NotComparableElementExcepetion;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T>{

    public ArrayOrderedList() {
        super();
    }

    public ArrayOrderedList( int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void add(T element) {
        if (element == null){
            throw new NullPointerException();
        }

        if (!(element instanceof Comparable)) {
            throw new NotComparableElementExcepetion("this element is not Comparable");
        }

        if (super.count == super.list.length){
            super.expandCapacity();
        }

        Comparable<T> element_tmp = (Comparable<T>) element;

        int pos = 0;
        while (pos < super.count && element_tmp.compareTo(super.list[pos]) > 0) {
            pos++;
        }

        for (int i = count; i > pos; i--) {
            super.list[i] = super.list[i - 1];
        }

        super.list[pos] = element;

        super.count++;
        super.modCount++;
    }
}
