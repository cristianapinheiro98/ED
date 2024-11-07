
import static org.junit.jupiter.api.Assertions.*;

import BinaryTree.LinkedBinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exceptions.ElementNotFoundException;

public class LinkedBinarySearchTreeTest {

    private LinkedBinarySearchTree<Integer> tree;

    @BeforeEach
    public void setUp() {
        tree = new LinkedBinarySearchTree<>();
    }

    @Test
    public void testAddElement() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(10, tree.getRoot());
        assertEquals(3, tree.size());
    }

    @Test
    public void testRemoveElement() throws ElementNotFoundException {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(10, tree.getRoot());
        Integer removed = tree.removeElement(10);
        assertEquals(10, removed);
        assertEquals(15, tree.getRoot());
        assertEquals(2, tree.size());

        assertThrows(ElementNotFoundException.class, () -> {tree.removeElement(20);});
    }

    @Test
    public void testRemoveAllOccurrences() {
        tree.addElement(10);
        tree.addElement(10);
        tree.addElement(15);
        tree.addElement(10);

        assertEquals(10, tree.getRoot());
        tree.removeAllOccurrences(10);
        assertEquals(1, tree.size());
        assertEquals(15, tree.getRoot());
    }

    @Test
    public void testRemoveMin() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        Integer min = tree.removeMin();
        assertEquals(5, min);
        assertEquals(2, tree.size());

        assertThrows(ElementNotFoundException.class, () -> {
            LinkedBinarySearchTree<Integer> emptyTree = new LinkedBinarySearchTree<>();
            emptyTree.removeMin();
        });
    }

    @Test
    public void testRemoveMax() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        Integer max = tree.removeMax();
        assertEquals(15, max);
        assertEquals(2, tree.size());

        assertThrows(ElementNotFoundException.class, () -> {
            LinkedBinarySearchTree<Integer> emptyTree = new LinkedBinarySearchTree<>();
            emptyTree.removeMax();
        });
    }

    @Test
    public void testFindMin() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(5, tree.findMin());

        assertThrows(ElementNotFoundException.class, () -> {
            LinkedBinarySearchTree<Integer> emptyTree = new LinkedBinarySearchTree<>();
            emptyTree.findMin();
        });
    }

    @Test
    public void testFindMax() {
        tree.addElement(10);
        tree.addElement(5);
        tree.addElement(15);

        assertEquals(15, tree.findMax());

        assertThrows(ElementNotFoundException.class, () -> {
            LinkedBinarySearchTree<Integer> emptyTree = new LinkedBinarySearchTree<>();
            emptyTree.findMax();
        });
    }
}
