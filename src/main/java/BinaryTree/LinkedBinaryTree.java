package BinaryTree;

import Exceptions.ElementNotFoundException;
import LinkedList.ArrayUnorderedList;
import Queue.LinkedQueue;
import Queue.QueueADT;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T>{
    protected int count;
    protected BinaryTreeNode<T> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the
     * new binary tree
     */
    public LinkedBinaryTree (T element) {
        count = 1;
        root = new BinaryTreeNode<T> (element);
    }

    @Override
    public T getRoot() {
        return this.root.element;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
        return find(targetElement) != null;
    }

    @Override
    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree. Throws a NoSuchElementException if
     * the specified target element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if an element not found
     * exception occurs
     */
    public T find(T targetElement) throws ElementNotFoundException
    {
        BinaryTreeNode<T> current = findAgain( targetElement, root );//guarda o nó

        if( current == null ) //se o nó for null significa que não encontrou
            throw new ElementNotFoundException("Couldn't find the element");

        return (current.element);//senão for retorna o elemento desse nó
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null)
            return null;

        if (next.element.equals(targetElement))//verifica se o elemento do nó que recebe por parâmetro é igual ao targetElement
            return next;

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);//senão for, volta a chamar o método recursivamente para comparar o filho esquerdo do nó

        if (temp == null)//se o nó não tiver filho esquerdo (ou seja, está null), chama o método recursivamente para comparar o filho direito
            temp = findAgain(targetElement, next.right);

        return temp;//devolve o nó
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with
     * the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder (root, tempList);

        return tempList.iterator();

    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root
     * for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder (node.left, tempList);
            tempList.addToRear(node.element);
            inorder (node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder (root, tempList);

        return tempList.iterator();
    }

    protected void preorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preorder(node.left, tempList);
            preorder(node.right, tempList);

        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postorder (root, tempList);

        return tempList.iterator();
    }

    protected void postorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postorder(node.left, tempList);
            postorder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }


    @Override
    public Iterator<T> iteratorLevelOrder() {
        QueueADT<BinaryTreeNode<T>> nodes = new LinkedQueue<BinaryTreeNode<T>>();//é uma queue de nós do tipo T
        ArrayUnorderedList<T> results = new ArrayUnorderedList<T>();

        nodes.enqueue(this.root);//adiciona o root à queue para iniciar a pesquisa level-order a partir do root
        while(!nodes.isEmpty()) {
            BinaryTreeNode<T> node = nodes.dequeue();//devolve T, mas, como neste caso os elementos da queue são nós, podemos armazenar o resultado em BinaryTreeNode
            if(node != null) {
                results.addToRear(node.element);
                if(node.left != null){
                    nodes.enqueue(node.left);
                }
                if(node.right != null){
                    nodes.enqueue(node.right);
                }
            } else {
                results.addToRear(null);
            }
        }

        return results.iterator();
    }
}
