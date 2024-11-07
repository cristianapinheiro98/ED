package BinaryTree;

import Exceptions.ElementNotFoundException;
import LinkedList.ArrayUnorderedList;
import Queue.ArrayQueue;
import Queue.QueueADT;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {
    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        count = 0;
        tree = (T[]) new Object[CAPACITY];
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element which will become the root of the new tree
     */
    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[CAPACITY];
        tree[0] = element;
    }

    @Override
    public T getRoot() {
        return this.tree[0];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
        return find(targetElement) != null;
    }


    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree. Throws a NoSuchElementException if
     * the specified target element is not found in the binary tree.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the element is in the tree
     * @throws ElementNotFoundException if an element not found
     * exception occurs
     */
    @Override
    public T find (T targetElement) throws ElementNotFoundException {
        T temp=null;
        boolean found = false;

        for (int ct=0; ct<count && !found; ct++)
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        if (!found)
            throw new ElementNotFoundException("binary tree");
        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by
     * calling an overloaded, recursive inorder method
     * that starts with the root.
     *
     * @return an iterator over the binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder (0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node used in the traversal
     * @param templist the temporary list used in the traversal
     */
    protected void inorder (int node, ArrayUnorderedList<T> templist){
        if (node < tree.length)//inicialmente o node é 0, ou seja, primeira posição do array
            if (tree[node] != null){
                inorder ((node * 2) + 1, templist);//left child
                templist.addToRear(tree[node]);//visita o nó
                inorder ((node + 1) * 2, templist);//right child
            }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preoroder (0, templist);
        return templist.iterator();
    }

    protected void preoroder (int node, ArrayUnorderedList<T> templist){
        if (node < tree.length)//inicialmente o node é 0, ou seja, primeira posição do array
            if (tree[node] != null){
                templist.addToRear(tree[node]);//visita o nó
                preoroder ((node * 2) + 1, templist);//left child
                preoroder ((node + 1) * 2, templist);//right child
            }
    }


    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        postorder (0, templist);
        return templist.iterator();
    }

    protected void postorder (int node, ArrayUnorderedList<T> templist){
        if (node < tree.length)//inicialmente o node é 0, ou seja, primeira posição do array
            if (tree[node] != null){
                postorder ((node * 2) + 1, templist);//left child
                postorder ((node + 1) * 2, templist);//right child
                templist.addToRear(tree[node]);//visita o nó
            }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        QueueADT<Integer> indexQueue = new ArrayQueue<>();//queue para guardar os indices do array
        ArrayUnorderedList<T> results = new ArrayUnorderedList<T>();

        indexQueue.enqueue(0);//adiciona o root à queue para iniciar a pesquisa level-order a partir do 1º posição do array

        while(!indexQueue.isEmpty()) {
            int index = indexQueue.dequeue();
            if(index < tree.length && tree[index] != null) {
                results.addToRear(tree[index]);
                int leftChild = (index * 2) + 1;
                if(leftChild < tree.length && tree[leftChild] != null){
                    indexQueue.enqueue(leftChild);
                }
                int rightChild = (index + 1) * 2;
                if(rightChild < tree.length && tree[rightChild] != null){
                    indexQueue.enqueue(rightChild);
                }
            } else {
                results.addToRear(null);
            }
        }

        return results.iterator();
    }

}


