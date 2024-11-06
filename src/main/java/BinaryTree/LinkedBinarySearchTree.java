package BinaryTree;

import Exceptions.ElementNotFoundException;

public class LinkedBinarySearchTree<T>  extends LinkedBinaryTree<T>
        implements BinarySearchTreeADT<T> {
    /**
     * Creates an empty binary search tree.
     */
    public LinkedBinarySearchTree() {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will be the root of the new
     *                binary search tree
     */
    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Adds the specified object to the binary search tree in the
     * appropriate position according to its key value.  Note that
     * equal elements are added to the right.
     *
     * @param element the element to be added to the binary search
     *                tree
     */
    public void addElement(T element) {
        BinaryTreeNode<T> temp = new BinaryTreeNode<T>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;

        if (isEmpty())
            root = temp;
        else {
            BinaryTreeNode<T> current = root;
            boolean added = false;
            while (!added) {
                if (comparableElement.compareTo(current.element) < 0) {//o elemento recebido por parametro é menor que o elemento do nó atual
                    if (current.left == null) {//logo avança para a esquerda do nó atual e se a esquerda estiver vazia adiciona aí
                        current.left = temp;
                        added = true;
                    } else
                        current = current.left;//senão o current passa a ser o nó da esquerda do nó atual anterior
                } else {//se for maior ou igual
                    if (current.right == null) {//avança para a direita do nó atual e se esta tiver vazia adiciona aí
                        current.right = temp;
                        added = true;
                    } else
                        current = current.right;//senão o current passa a ser o nó da direita do nó atual anterior
                }
            }
        }
        count++;
    }

    /**
     * Removes the first element that matches the specified target
     * element from the binary search tree and returns a reference to
     * it.  Throws a ElementNotFoundException if the specified target
     * element is not found in the binary search tree.
     *
     * @param targetElement the element being sought in the binary <search tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    public T removeElement(T targetElement) throws ElementNotFoundException {
        T result = null;

        if (!isEmpty()) {
            if (((Comparable) targetElement).equals(root.element)) {//verifica se o elemento do nó a remover é igual ao elemento do root, se for é esse que elimina
                result = root.element;
                root = replacement(root);
                count--;
            } else {//o elemento a ser elimado não é o do nó da raiz
                BinaryTreeNode<T> current, parent = root;
                boolean found = false;

                if (((Comparable) targetElement).compareTo(root.element) < 0)//verifica se o elemento do nó a remover é inferior ao elemento do root
                    current = root.left;//se for current passa a ser o filho esquerdo do root
                else
                    current = root.right;//senão for current passa a ser o filho direito do root

                while (current != null && !found) {
                    if (targetElement.equals(current.element)) {//verifica se o elemento do nó a remover é igual ao elemento do current (filho dto ou esq)
                        found = true;
                        count--;
                        result = current.element;

                        if (current == parent.left) {//Se current é nó filho esquerdo de parent, substituímos o nó parent.left pelo nó de substituição de current
                            parent.left = replacement(current);
                        } else {//Se current é nó o filho direito de parent, substituímos o nó parent.right pelo nó de substituição de current.
                            parent.right = replacement(current);
                        }
                    } else {//o elemento do nó a remover não é igual ao elemento do current
                        parent = current;//o pai passa a ser o que era o current

                        if (((Comparable) targetElement).compareTo(current.element) < 0)//verifica se o elemento do nó a remover é inferior ao elemento do current
                            current = current.left;//se for current passa a ser o filho esquerdo do current anterior
                        else
                            current = current.right;//senão for current passa a ser o filho direito do current anterior
                    }
                }

                if (!found)
                    throw new ElementNotFoundException("binary search tree is empty");
            }
        }
        return result;
    }

    @Override
    public void removeAllOccurrences(T targetElement) {
        this.removeElement(targetElement);

        while (super.contains(targetElement)) {
            removeElement(targetElement);
        }
    }

    @Override
    public T removeMin() {
        T result = null;

        if(!isEmpty()) {
            if (root.left == null) {
                result = root.element;
                root = root.right;
            } else {
                BinaryTreeNode<T> parent = root;
                BinaryTreeNode<T> current = root.left;

                while (current.left != null) {
                    parent = current;
                    current = current.left;
                }
                result = current.element;//current agora é o nó mais à esquerda

                if (current.right == null) {//o nó é uma folha
                    parent.left = null; //colocar a referência do filho esquerdo do pai a null;
                } else {//o nó interno tem um filho direito
                    parent.left = current.right; // colocar a referência do filho esquerdo do pai a apontar para o filho direito do nó a ser removido
                }
            }
        } else {
            throw new ElementNotFoundException("binary search tree is empty");}

        return result;
    }

    @Override
    public T removeMax() {
        T result = null;

        if(!isEmpty()) {
            if (root.right == null) {
                result = root.element;
                root = root.left;
            } else {
                BinaryTreeNode<T> parent = root;
                BinaryTreeNode<T> current = root.right;

                while (current.right != null) {
                    parent = current;
                    current = current.right;
                }
                result = current.element;//current agora é o nó mais à direita

                if (current.left == null) {//o nó é uma folha
                    parent.right = null; //colocar a referência do filho direito do pai a null;
                } else {//o nó interno tem um filho direito
                    parent.right = current.left; // colocar a referência do filho direito do pai a apontar para o filho esquerdo do nó a ser removido
                }
            }
        } else {
            throw new ElementNotFoundException("binary search tree is empty");}

        return result;
    }

    @Override
    public T findMin() {
        T result = null;

        if(!isEmpty()) {
            if (root.left == null) {
                result = root.element;
            } else {
                BinaryTreeNode<T> current = root.left;

                while (current.left != null) {
                    current = current.left;
                }

                result = current.element;//current agora é o nó mais à esquerda, ou seja, o elemento mínimo da tree

            }
        } else {
            throw new ElementNotFoundException("binary search tree is empty");
        }

        return result;
    }

    @Override
    public T findMax() {
        T result = null;

        if(!isEmpty()) {
            if (root.right == null) {
                result = root.element;
            } else {
                BinaryTreeNode<T> current = root.right;

                while (current.right != null) {
                    current = current.right;
                }
                result = current.element;//current agora é o nó mais à direita, ou seja, o elemento máximo da tree
            }
        } else {
            throw new ElementNotFoundException("binary search tree is empty");}

        return result;
    }

    /**
     * Returns a reference to a node that will replace the one
     * specified for removal.  In the case where the removed node has
     * two children, the inorder successor is used as its replacement.
     *
     * @param node  the node to be removeed
     * @return a referenceto the replacing node
     */

    protected BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result = null;

        if ((node.left == null) && (node.right == null))//o nó não tem nós filhos
            result = null;//substituição a null pq ao remover esse nó fica sem mais nenhum
        else if ((node.left != null) && (node.right == null))//o nó tem um filho apenas à esquerda
            result = node.left;//o nó a eliminar é substituido pelo próprio filho esquerdo
        else if ((node.left == null) && (node.right != null))//o nó só tem um filho à direita
            result = node.right;//o nó a eliminar é substituido pelo próprio filho direito
        else {//o nó tem um filho à esquerda e um filho à direita
            BinaryTreeNode<T> current = node.right;//inicialmente current é o nó da direita do nó a remover
            BinaryTreeNode<T> parent = node;//o pai é o nó a remover

            while (current.left != null) {//Descer pela subárvore esquerda de current até encontrarmos o nó mais à esquerda que é o que tem o menor valor
                parent = current;//o pai passa a ser o current
                current = current.left;//e o novo current passa a ser o nó da esquerda do current anterior
            }

            if (node.right == current)//Se o nó substituto (current) é o filho direito imediato de node, o filho esquerdo de node passa a ser o filho esquerdo de current. Isso garante que current ocupe o lugar de node sem perder a referência à subárvore esquerda de node.
                current.left = node.left;

            else {//caso o current não seja o filho direito imediato de node:
                parent.left = current.right;//o pai de current aponta para o filho direito de current, removendo current da posição original.
                current.right = node.right;//liga current à subárvore direita do nó a remover
                current.left = node.left;//liga current à subárvore esquerda do nó a remover
                /*Assim garante-se que current ocupa o lugar do nó a remover corretamente, preservando a estrutura e a ordem da árvore binária.*/
            }
            result = current;
        }
        return result;//retorna o substituto do nó a remover
    }
}
