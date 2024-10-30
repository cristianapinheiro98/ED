package LinkedListTests;


import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import LinkedList.DoubleLinkedOrderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Classe de teste para DoubleLinkedOrderedList
public class DoubleLinkedOrderedListTest {

    private DoubleLinkedOrderedList<Integer> orderedList;

    @BeforeEach
    public void setUp() {
        // Instancia o DoubleLinkedOrderedList
        orderedList = new DoubleLinkedOrderedList<>();
    }

    @Test
    public void testAddElement() {
        orderedList.add(20);
        orderedList.add(10);
        orderedList.add(30);

        assertEquals(10, orderedList.first());
        assertEquals(30, orderedList.last());
        assertEquals(3, orderedList.size());
    }

    @Test
    public void testRemoveFirst() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);

        assertEquals(10, orderedList.removeFirst());
        assertEquals(20, orderedList.first());
        assertEquals(2, orderedList.size());
    }

    @Test
    public void testRemoveLast() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);

        assertEquals(30, orderedList.removeLast());
        assertEquals(20, orderedList.last());
        assertEquals(2, orderedList.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(orderedList.isEmpty());
        orderedList.add(10);
        assertFalse(orderedList.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, orderedList.size());
        orderedList.add(10);
        orderedList.add(20);
        assertEquals(2, orderedList.size());
    }

    @Test
    public void testOrderMaintained() {
        orderedList.add(15);
        orderedList.add(10);
        orderedList.add(25);
        orderedList.add(20);

        assertEquals(10, orderedList.removeFirst());
        assertEquals(15, orderedList.removeFirst());
        assertEquals(20, orderedList.removeFirst());
        assertEquals(25, orderedList.removeFirst());
        assertTrue(orderedList.isEmpty());
    }

    @Test
    public void testRemoveElement() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);

        assertEquals(3, orderedList.size());

        orderedList.remove(20);
        assertEquals(2, orderedList.size());
        assertEquals(10, orderedList.first());
        assertEquals(30, orderedList.last());
    }

    @Test
    public void testFirstOnEmptyList() {
        try {
            orderedList.first();
            fail("Should have thrown EmptyCollectionException");
        } catch (EmptyCollectionException e) {
            // Expected exception
        }
    }

    @Test
    public void testLastOnEmptyList() {
        try {
            orderedList.last();
            fail("Should have thrown EmptyCollectionException");
        } catch (EmptyCollectionException e) {
            // Expected exception
        }
    }

    @Test
    public void testRemoveFromEmptyList() {
        try {
            orderedList.remove(10);
            fail("Should have thrown EmptyCollectionException");
        } catch (EmptyCollectionException e) {
            // Expected exception
        }
    }

    @Test
    public void testAddNullElement() {
        try {
            orderedList.add(null);
            fail("Should have thrown ElementNotFound");
        } catch (ElementNotFoundException e) {
            // Expected exception
        }
    }

}
