package BinaryTree;

import Exceptions.ElementNotFoundException;

public class AVLBinarySearchTree<T> extends LinkedBinarySearchTree<T> {

    public AVLBinarySearchTree() {
        super();
    }

    public void addElement(T element) {
        super.addElement(element);
        //updateHeight(root);
        //balanceTree(root);
        root = balanceTreeAfterAddElement(root, element);
    }

    private BinaryTreeNode<T> balanceTreeAfterAddElement(BinaryTreeNode<T> node, T element) {
        if (node == null) {
            return null;
        }

        int compareResult = ((Comparable<T>) element).compareTo(node.element);

        // Percorre a árvore para localizar o novo nó e aplicar o balanceamento
        if (compareResult < 0) {
            node.left = balanceTreeAfterAddElement(node.left, element);
        } else if (compareResult > 0) {
            node.right = balanceTreeAfterAddElement(node.right, element);
        }


       updateHeight(node);

        return balanceTree(node);
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        BinaryTreeNode<T> node = balanceTreeAfterRemoveElement(root, targetElement);

        return node.element;
    }

    private BinaryTreeNode<T> balanceTreeAfterRemoveElement(BinaryTreeNode<T> node, T targetElement) throws ElementNotFoundException {
        super.removeElement(targetElement);

        if (node != null) {
            node.height = Math.max(calculatehHeight(node.left), calculatehHeight(node.right)) + 1;

            balanceTree(node);
        }
        return node;
    }

    private BinaryTreeNode<T> balanceTree(BinaryTreeNode<T> node) {
        if (getBalanceFactor(node) == 2) {// Verifica se o fator de balanceamento indica desbalanceamento para a esquerda (alturas calculadas a partir da raiz)
            if (getBalanceFactor(node.left) > 0) {//verifica o fator de balanceamento da sub-árvore esquerda, a partir do filho esquerdo da raiz
                node = rotateRight(node); // basta fazer apenas uma rotação simples à direita
            }
            else {
                node = rotateDoubleRight(node); //senão é necessário fazer uma rotação dupla à direita
            }
        }
        else if (getBalanceFactor(node) == -2) {// Verifica se o fator de balanceamento indica desbalanceamento para a direita (alturas calculadas a partir da raiz)
            if (getBalanceFactor(node.right) < 0) {//verifica o fator de balanceamento da sub-árvore direita, a partir do filho direito da raiz
                node = rotateLeft(node); // basta fazer apenas uma rotação simples à esquerda
            }
            else {
                node = rotateDoubleLeft(node); //senão é necessário fazer uma rotação dupla à esquerda
            }
        }

        // Atualiza a altura do nó após a rotação caso tenha sido feita
        node.height = Math.max(calculatehHeight(node.left), calculatehHeight(node.right)) + 1;
        return node; //Retorna o nó (possivelmente atualizado) para garantir que a árvore AVL permaneça consistente
    }

    private int getBalanceFactor(BinaryTreeNode<T> node) {
        return calculatehHeight(node.left) - calculatehHeight(node.right);
    }

    private int calculatehHeight(BinaryTreeNode<T> node) {
        return node == null ? -1 : node.height;
    }

    private void updateHeight(BinaryTreeNode<T> node) {
        /*o método calculateHeight calcula a altura da sobárvore esquerda e da subárvore direita, esses valores são
        enviados para o método Math.max que retorna o valor maior dos 2. Posteriormente, é adicionado 1 a esse valor, o
        que representa a altura do nó recebido por parâmetro no updateHeight*/

        node.height = Math.max(calculatehHeight(node.left), calculatehHeight(node.right)) + 1;
    }


    private BinaryTreeNode<T> rotateRight(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> pivot = node.left; // pivot é o filho esquerdo do nó
        node.left = pivot.right;// o filho direito do pivot passa a ser o filho esquerdo do nó
        pivot.right = node;// o nó passa a ser o filho direito do pivot

        updateHeight(node);
        updateHeight(pivot);

        // o pivot é o novo root, ficando assim a árvore balanceada
        return pivot;
    }

    private BinaryTreeNode<T> rotateLeft(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> pivot = node.right;// pivot é o filho direito de node
        node.right = pivot.left;// A subárvore esquerda de pivot passa a ser a subárvore direita de node
        pivot.left = node;//node passa a ser o filho esquerdo de pivot

        updateHeight(node);
        updateHeight(pivot);

        //o pivot agora é a nova raiz da subárvore balanceada
        return pivot;
    }

    private BinaryTreeNode<T> rotateDoubleLeft(BinaryTreeNode<T> node) {
        node.right = rotateRight(node.right); // Primeira rotação simples à direita no filho direito de nó
        return rotateLeft(node); // Segundo rotação simples à esquerda em node (do que resulta da primeira rotação)
    }

    private BinaryTreeNode<T> rotateDoubleRight(BinaryTreeNode<T> node) {
        node.left = rotateLeft(node.left); // Primeiro rotação simples à esquerda no filho esquerdo
        return rotateRight(node);// Depois, rotação simples à direita no próprio node (do que resulta da primeira rotação)
    }
}
