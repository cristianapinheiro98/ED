package BinaryTree;

public interface HeapADT<T> extends BinaryTreeADT<T> {
    void addElement(T obj);

    T removeMin();

    T findMin();
}