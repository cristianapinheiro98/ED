package LinkedListTests;


import Exceptions.EmptyCollectionException;
import LinkedList.ArrayOrderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayOrderedListTest {
    private ArrayOrderedList<Integer> orderedList;

    @BeforeEach
    public void setUp() {
        orderedList = new ArrayOrderedList<>(5);
    }

    @Test
    public void testAddSingleElement() {
        orderedList.add(10);
        assertEquals(1, orderedList.size());
        assertEquals((Integer)10, orderedList.first());
    }

    @Test
    public void testAddElementsInOrder() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);

        assertEquals(3, orderedList.size());
        assertEquals((Integer)10, orderedList.first());
        assertEquals((Integer)30, orderedList.last());
    }

    @Test
    public void testAddElementsOutOfOrder() {
        orderedList.add(30);
        orderedList.add(10);
        orderedList.add(20);

        assertEquals(3, orderedList.size());
        assertEquals((Integer) 10, orderedList.first());
        assertEquals((Integer) 30, orderedList.last());

        Iterator<Integer> iterator = orderedList.iterator();

        assertTrue(iterator.hasNext());
        assertEquals((Integer) 10, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 30, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddDuplicateElements() {
        orderedList.add(20);
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);
        assertEquals(4, orderedList.size());

        Iterator<Integer> iterator = orderedList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 10, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 30, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testExpandCapacity() {
        orderedList.add(50);
        orderedList.add(20);
        orderedList.add(70);
        orderedList.add(10);
        orderedList.add(30);
        orderedList.add(60); // Deve acionar expandCapacity()

        assertEquals(6, orderedList.size());
        Iterator<Integer> iterator = orderedList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 10, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 30, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 50, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 60, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 70, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddNullElement() {
        try {
            orderedList.add(null);
            fail("Deveria ter lançado NullPointerException");
        } catch (NullPointerException e) {
            // Exceção esperada
        }
    }

    @Test
    public void testRemoveElement() {
        orderedList.add(10);
        orderedList.add(20);
        orderedList.add(30);
        assertEquals(3, orderedList.size());

        orderedList.remove(20);
        assertEquals(2, orderedList.size());
        assertEquals((Integer) 10, orderedList.first());
        assertEquals((Integer) 30, orderedList.last());
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
    public void testIsEmpty() {
        assertTrue(orderedList.isEmpty());

        orderedList.add(10);
        assertFalse(orderedList.isEmpty());
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
}
