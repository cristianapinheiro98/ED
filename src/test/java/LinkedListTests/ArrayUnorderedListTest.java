package LinkedListTests;


import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import LinkedList.ArrayUnorderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUnorderedListTest {

    private ArrayUnorderedList<Integer> unorderedList;

    @BeforeEach
    public void setUp() {
        // Instancia o ArrayUnorderedList
        unorderedList = new ArrayUnorderedList<>();
    }

    @Test
    public void testAddToFront() {
        unorderedList.addToFront(10);
        unorderedList.addToFront(20);

        assertEquals(20, unorderedList.first());
        assertEquals(2, unorderedList.size());
    }

    @Test
    public void testAddToRear() {
        unorderedList.addToRear(10);
        unorderedList.addToRear(20);

        assertEquals(20, unorderedList.last());
        assertEquals(2, unorderedList.size());
    }

    @Test
    public void testAddAfter() {
        unorderedList.addToFront(10);
        unorderedList.addToRear(30);
        unorderedList.addAfter(20, 10);  // Adiciona 20 após 10

        assertEquals(10, unorderedList.first());
        assertEquals(30, unorderedList.last());
        assertEquals(3, unorderedList.size());
    }

    @Test
    public void testRemoveFirst() {
        unorderedList.addToFront(10);
        unorderedList.addToRear(20);

        assertEquals(10, unorderedList.removeFirst());
        assertEquals(1, unorderedList.size());
    }

    @Test
    public void testRemoveLast() {
        unorderedList.addToFront(10);
        unorderedList.addToRear(20);

        assertEquals(20, unorderedList.removeLast());
        assertEquals(1, unorderedList.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(unorderedList.isEmpty());
        unorderedList.addToFront(10);
        assertFalse(unorderedList.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, unorderedList.size());
        unorderedList.addToFront(10);
        assertEquals(1, unorderedList.size());
    }

    @Test
    public void testRemoveElement() {
        unorderedList.addToFront(10);
        unorderedList.addToRear(20);
        unorderedList.addToRear(30);

        assertEquals(3, unorderedList.size());

        unorderedList.remove(20);
        assertEquals(2, unorderedList.size());
        assertEquals(10, unorderedList.first());
        assertEquals(30, unorderedList.last());
    }

    @Test
    public void testFirstOnEmptyList() {
        try {
            unorderedList.first();
            fail("Should have thrown EmptyCollectionException");
        } catch (EmptyCollectionException e) {
            // Expected exception
        }
    }

    @Test
    public void testLastOnEmptyList() {
        try {
            unorderedList.last();
            fail("Should have thrown EmptyCollectionException");
        } catch (EmptyCollectionException e) {
            // Expected exception
        }
    }

    @Test
    public void testRemoveFromEmptyList() {
        try {
            unorderedList.remove(10);
            fail("Should have thrown EmptyCollectionException");
        } catch (EmptyCollectionException e) {
            // Expected exception
        }
    }

    @Test
    public void testAddNullElement() {
        try {
            unorderedList.addAfter(null, 10);
            fail("Should have thrown ElementNotFound");
        } catch (ElementNotFoundException e) {
            // Expected exception
        }
    }


}
