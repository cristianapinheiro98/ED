package BinaryTree;

import Exceptions.EmptyCollectionException;

/**
 * ArrayHeap provides an array implementation of a minheap.
 *
 */
public class ArrayHeap<T> extends ArrayBinaryTree<T> implements HeapADT<T> {
    private static final int FACTOR= 2;
    public ArrayHeap() {
        super();
    }

    /**
     * Adds the specified element to this heap in the appropriate position according to its key value. Note that equal elements are added to the right.
     *
     * @param obj  the element to be added to this heap
     */
    @Override
    public void addElement(T obj) {
        if (count == tree.length){
            expandCapacity();
        }

        tree[count] =obj;//adicionar o novo elemento à 1ª posição vazia do array, ou seja, o count
        count++;

        if (count>1) {//se o array tiver mais do que uma posição ocupada pode ser necessário ordenar
            heapifyAdd();
        }
    }

    /**
     * Remove the element with the lowest value in this heap and returns a reference to it.
     * Throws an EmptyCollectionException if the heap is empty.
     *
     * @return     a reference to the element with the lowest value in this head
     * @throws EmptyCollectionException  if an empty collection exception occurs
     */
    @Override
    public T removeMin()  throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }

        T minElement = tree[0];//o elemento minimo é o root que está na posição 0
        tree[0] = tree[count-1];//substituir o root pela última folha do nivel h
        heapifyRemove();//reordenar
        count--;

        return minElement;
    }

    @Override
    public T findMin() {
        return tree[0];
    }

    /**
     * Reorders this heap to maintain the ordering property after
     * adding a node.
     */
    private void heapifyAdd() {
        T temp;
        int next = count - 1;
        temp = tree[next];//o novo elemento adicionado está na última posição ocupada do array

        while ((next != 0) && (((Comparable)temp).compareTo(tree[(next-1)/2]) < 0)){//enquanto o next não for 0 (root) ou o elemento do temp for menor que o elemento do parent
            tree[next] = tree[(next-1)/2];// o elemento do pai é movido para a posição next
            next = (next-1)/2;//o indice do next é atualizado para o índice do pai
        }

        tree[next] = temp;//o elemento do temp é posicionado na posição correta dentro da tree[next]
    }

    /**
     * Reorders this heap to maintain the ordering property.
     */
    private void heapifyRemove() {
        T temp;
        int node = 0;//índice do nó atual (inicia na raiz, 0)
        int left = 1;//índice do filho esquerdo (2*node + 1)
        int right = 2;//índice do filho direito (2*node + 2)
        int next;//o nó que vai fazer a troca com o outro nó

        if ((tree[left] == null) && (tree[right] == null))//se o node não tiver filhos, significa que node é uma folha
            next = count;//logo não há troca necessário e por isso o next é igual ao contador
        else if (tree[left] == null)//se o node não tiver filho esquerdo, o next é o filho direito
            next = right;
        else if (tree[right] == null)//se o node não tiver filho direito, o next é o filho esquerdo
            next = left;
        else if (((Comparable)tree[left]).compareTo(tree[right]) < 0)//se o node tiver os 2 filhos, escolher o menor filho entre os para atribuir ao next
            next = left;
        else
            next = right;

        temp = tree[node];//O valor atual da raiz ou do nó que está sendo ajustado é armazenado em temp para ser reposicionado no final.

        while ((next < count) && (((Comparable)tree[next]).compareTo(temp) < 0)) {//enquanto o next for menor que o count ou o elemento do next for menor que o elemento do temp
            tree[node] = tree[next];//O nó atual é substituído pelo menor filho (nó que foi escolhido em cima para trocar)
            node = next;//Atualiza o índice para o nó que acabou de ser movido
            left = 2*node+1;//atualiza o indice do seu filho esquerdo
            right = 2*(node+1);//atualiza o índice do seu filho direito
            if ((tree[left] == null) && (tree[right] == null))//volta novamente a encontrar o next, ou seja, o nó para trocar
                next = count;
            else if (tree[left] == null)
                next = right;
            else if (tree[right] == null)
                next = left;
            else if (((Comparable)tree[left]).compareTo(tree[right]) < 0)
                next = left;
            else
                next = right;
        }
        tree[node] = temp;//Quando o valor de temp encontra sua posição correta (não é maior que nenhum filho), ele é colocado no índice atual (node)
    }

    protected void expandCapacity() {//protected
        T[] array_tmp = (T[]) (new Object[this.tree.length * FACTOR]);
        for (int i = 0; i < this.tree.length; i++) {
            array_tmp[i] = this.tree[i];
        }
        this.tree = array_tmp;
    }

}
