import static org.junit.jupiter.api.Assertions.*;

import BinaryTree.AVLBinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exceptions.ElementNotFoundException;

public class AVLBinarySearchTreeTest {

    private AVLBinarySearchTree<Integer> avlTree;

    @BeforeEach
    public void setUp() {
        avlTree = new AVLBinarySearchTree<>();
    }

    @Test
    public void testAddElementTriggersBalance() {
        avlTree.addElement(10);
        avlTree.addElement(20);
        avlTree.addElement(30); // Deve causar uma rotação para balancear a árvore

        assertEquals(20, avlTree.getRoot(), "Após rotação, o elemento 20 deve ser a nova raiz");
    }

    @Test
    public void testRemoveElementTriggersBalance() throws ElementNotFoundException {
        avlTree.addElement(10);
        avlTree.addElement(5);
        avlTree.addElement(15);
        avlTree.addElement(3);

        avlTree.removeElement(15); // Remoção deve disparar um reequilíbrio
        assertEquals(10, avlTree.getRoot(), "A raiz deve continuar sendo 10 após remoção e rebalanceamento");
    }

    @Test
    public void testAddCausesRightRotation() {
        avlTree.addElement(30);
        avlTree.addElement(20);
        avlTree.addElement(10); // Inserção deve causar rotação à direita

        assertEquals(20, avlTree.getRoot(), "Após rotação à direita, o elemento 20 deve ser a nova raiz");
    }

    @Test
    public void testAddCausesLeftRotation() {
        avlTree.addElement(10);
        avlTree.addElement(20);
        avlTree.addElement(30); // Inserção deve causar rotação à esquerda

        assertEquals(20, avlTree.getRoot(), "Após rotação à esquerda, o elemento 20 deve ser a nova raiz");
    }

    @Test
    public void testAddCausesLeftRightRotation() {
        avlTree.addElement(30);
        avlTree.addElement(10);
        avlTree.addElement(20); // Inserção deve causar rotação dupla (esquerda-direita)

        assertEquals(20, avlTree.getRoot(), "Após rotação esquerda-direita, o elemento 20 deve ser a nova raiz");
    }

    @Test
    public void testAddCausesRightLeftRotation() {
        avlTree.addElement(10);
        avlTree.addElement(30);
        avlTree.addElement(20); // Inserção deve causar rotação dupla (direita-esquerda)

        assertEquals(20, avlTree.getRoot(), "Após rotação direita-esquerda, o elemento 20 deve ser a nova raiz");
    }

    @Test
    public void testRemoveAndUpdateRootAfterRotation() {
        avlTree.addElement(20);
        avlTree.addElement(10);
        avlTree.addElement(30);
        avlTree.addElement(25);
        avlTree.addElement(40);

        avlTree.removeElement(10); // Remover 10 deve manter a árvore balanceada
        assertEquals(20, avlTree.getRoot(), "A raiz deve continuar sendo 20 após remoção e balanceamento");
    }

    @Test
    public void testFindMinAndMax() {
        avlTree.addElement(30);
        avlTree.addElement(20);
        avlTree.addElement(40);
        avlTree.addElement(10);

        assertEquals(10, avlTree.findMin());
        assertEquals(40, avlTree.findMax());
    }

    @Test
    public void testRemoveNonExistentElement() {
        avlTree.addElement(10);
        avlTree.addElement(20);

        assertThrows(ElementNotFoundException.class, () -> {
            avlTree.removeElement(30); // Elemento 30 não existe na árvore
        });
    }
}
