package BinaryTree;

import Exceptions.EmptyCollectionException;

/**
 * Heap implements a heap.
 *
 */
public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {
    public HeapNode<T> lastNode;

    public LinkedHeap() {
        super();
    }

    public LinkedHeap(T element) {
        super(element);
    }

    /**
     * Returns the node that will be the parent of the new node
     *
     * @return  the node that will be a parent of the new node
     */
    private HeapNode<T> getNextParentAdd() {//retorna uma referência para o nó que será o pai do novo nó que vai ser adicionado
        HeapNode<T> result = this.lastNode;//o último nó que foi adicionado antes do que se quer adicionar agora

        while ((result != root) && (result.parent.left != result)) {//percorre a tree desde  o last node até encontrar o root ou o result ser o filho esquerdo do seu pai
            result = result.parent;
        }

        if (result != root) {//se o result não for o root
            if (result.parent.right == null) {//o parent não tem filho direito
                result = result.parent;//o parent passa a ser o nó onde se ai adicionar o novo elemento
            } else {//se o parent tem filho direito
                result = (HeapNode<T>) result.parent.right;//percorre o caminho mais à esquerda da subárvore direita do parent para encontrar a próxima posição disponível.
                while (result.left != null) {
                    result = (HeapNode<T>) result.left;
                }
            }
        } else {//se o result for o root
            while (result.left != null) {//Se o result atingiu o root, percorre o caminho mais à esquerda da árvore para encontrar a próxima posição disponível.
                result = (HeapNode<T>) result.left;
            }
        }

        return result;//retorna o result que aponta para o nó onde um novo child pode ser adicionado
    }

    /**
     * Reorders this heap after adding a node.
     */
    private void heapifyAdd() {//ordena a heap após adicionar um elemento
        T temp;
        HeapNode<T> next = this.lastNode;//inicialmente node aponta para o último nó adicionado
        temp = next.element;//temp guarda o elemento do último nó adicionado

        while ((next != root) && (((Comparable) temp).compareTo(next.parent.element) < 0)) {//enquanto o next não for o root e o valor do elemento do temp for menor que o valor do seu nó pai
            next.element = next.parent.element;//o valor do elemento do parent é atribuído ao elemento do next
            next = next.parent;//o next passa a ser o parent
        }
        /*com isto dá-se o swap dos valores, ou seja, o valor do elemento pai passa para baixo e o next move-se para cima*/

        next.element = temp;//guarda-se o valor do elemento no nó certo, ou seja, quando não há mais reorganização
    }

    /**
     * Reorders this heap after removing the root element.
     */
    private void heapifyRemove() {//reordenada a heap após remoção de um elemento
        T temp;
        HeapNode<T> node = (HeapNode<T>) root;
        HeapNode<T> left = (HeapNode<T>) node.left;
        HeapNode<T> right = (HeapNode<T>) node.right;
        HeapNode<T> next;

        /*determina qual dos filhos do node o vai substituir*/
        if ((left == null) && (right == null)) {//node sem filhos, por isso o next não aponta para nada
            next = null;
        } else if (left == null) {//se o node não tiver filho esquerdo, o next aponta para o filho direito do node
            next = right;
        } else if (right == null) {//se o node não tiver filho direito, o next aponta para o filho esquerdo do node
            next = left;
        } else if (((Comparable) left.element).compareTo(right.element) < 0) {//se o node tiver os 2 filhos vê se o elemento do filho esquerdo é menor que o elemento do filho direito
            next = left;//se for o next aponta para o filho esquerdo do nó
        } else {
            next = right;//senão for o next aponta para o filho direito do nó
        }

        temp = node.element;//guardar o valor do elemento do root
        while ((next != null) && (((Comparable) next.element).compareTo(temp) < 0)) {//enquanto o next for diferente de null e o elemento do next for menor que o elemento do node
            node.element = next.element;//o valor do elemento do node passa a ser o do elemento do next
            node = next;//node passa a ser o que era o next
            left = (HeapNode<T>) node.left;//left passa a ser o filho esquerdo do nó
            right = (HeapNode<T>) node.right;//right passa a ser o filho direito do nó
            /*depois disto verifica se é necessário reorganizar mais algum nó, fazendo o mesmo processo de cima para encontrar o next*/
            if ((left == null) && (right == null)) {
                next = null;
            } else if (left == null) {
                next = right;
            } else if (right == null) {
                next = left;
            } else if (((Comparable) left.element).compareTo(right.element) < 0) {
                next = left;
            } else {
                next = right;
            }
        }

        node.element = temp;//guarda-se o valor do elemento no nó certo, ou seja, quando não há mais reorganização
    }

    /**
     * Returns the node that will be the new last node after a remove.
     *
     * @return  the node that willbe the new last node after
     * a remove
     */
    private HeapNode<T> getNewLastNode() {// retorna uma referência para o novo último nó, geralmente o nó mais à direita do último nivel da tree
        HeapNode<T> result = this.lastNode;

        while ((result != root) && (result.parent.left == result)) {//percorre a tree de baixo para cima até result ser igual ao root ou o filho esquerdo do pai ser o result
            result = result.parent;//avança na tree de baixo para cima
        }

        if (result != root) {
            result = (HeapNode<T>) result.parent.left;//result passa a ser o filho esquerdo do parent
        }

        /*se o result não tiver filho direito tem que ser ele o novo last node*/
        while (result.right != null) {//Desce a árvore o máximo possível pelo caminho mais à direita até encontrar um nó sem filhos à direita
            result = (HeapNode<T>) result.right;
        }

        return result;
    }

    /**
     * Adds the specified element to this heap in the appropriate position according to its key value. Note that equal elements are added to the right.
     * @param obj  the element to be added to this head
     */
    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<>(obj);

        if (super.root == null) {//se o root for null significa que a tree está vazia portanto o nó a adicionar passa a ser o root
            super.root = node;
        } else {
            HeapNode<T> nextParent = this.getNextParentAdd();//se a tree não estiver vazia temos que ir procurar o parent onde adicionar o novo nó

            if (nextParent.left == null) {//se o parent não tiver flho esquerdo, adiciona-se aí o nó
                nextParent.left = node;
            } else if (nextParent.right == null) {//se tem filho esquerdo, mas não tem filho direito, então adiiciona-o aí o nó
                nextParent.right = node;
            }

            node.parent = nextParent;//o pai do nó passa a ser o nextParent
        }

        this.lastNode = node;//o novo nó adicionado é agora o último nó adicionado na heap
        super.count++;

        if (count > 1) this.heapifyAdd();//se tiver mais do que um nó precisa de verificar se precisa de reorganização após o add
    }

    /**
     * Remove the element with the lowest value in this heap and returns a reference to it.
     * Throws an EmptyCollectionException if the heap is empty.
     *
     * @return    the element with the lowest value in this heap
     * @throws EmptyCollectionException  if an empty collection exception occurs
     */
    public T removeMin() {//remove sempre o root pois é onde está o elemento mínimo
        if (isEmpty()) throw new EmptyCollectionException("Heap is empty");

        T minElement = root.element;

        if (count == 1) {//se o count for 1 a tree só tem o root, tanto o root como lastNode passam a null
            root = null;
            lastNode = null;
        } else {
            HeapNode<T> nextLast = getNewLastNode();//retorna aquele que vai ser o novo último nó da heap após o lastNode ser movido para o lugar do root

            if (lastNode.parent.left == lastNode) {//o lastNode precisa ser removido do seu pai, ou seja, se for o filho esquerdo passa a ser null se for o direito é este que passa a null
                lastNode.parent.left = null;
            } else {
                lastNode.parent.right = null;
            }

            root.element = lastNode.element;//move o lastNode para root, atribuindo o valor do elemento do lastNode ao elemento do root
            lastNode = nextLast;//assim o lastNode agora passa a ser o nextLast
            heapifyRemove();//reorganiza a heap após a remoção do root
        }

        super.count--;

        return minElement;
    }

    public T findMin() throws EmptyCollectionException {
        if (super.isEmpty()) throw new EmptyCollectionException("Heap is empty");

        return root.element;
    }
}
