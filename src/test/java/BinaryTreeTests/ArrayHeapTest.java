
import static org.junit.jupiter.api.Assertions.*;

import BinaryTree.ArrayHeap;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayHeapTest {

    private ArrayHeap<Integer> heap;

    @BeforeEach
    public void setUp() {
        heap = new ArrayHeap<>();
    }

    @Test
    public void testAddElement() {
        heap.addElement(10);
        heap.addElement(20);
        heap.addElement(5);

        // Check if the root is the minimum element
        assertEquals(5, heap.findMin());
    }

    @Test
    public void testRemoveMin() {
        heap.addElement(10);
        heap.addElement(20);
        heap.addElement(5);

        // Remove the minimum element
        int min = heap.removeMin();
        assertEquals(5, min);

        // Check if the next minimum is correct
        assertEquals(10, heap.findMin());
    }

    @Test
    public void testRemoveMinUntilEmpty() {
        heap.addElement(10);
        heap.addElement(20);
        heap.addElement(5);

        assertEquals(5, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(20, heap.removeMin());

        // Check if the heap is empty
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testFindMin() {
        heap.addElement(15);
        heap.addElement(10);
        heap.addElement(20);

        // Find the minimum element
        assertEquals(10, heap.findMin());
    }

    @Test
    public void testExpandCapacity() {
        for (int i = 1; i <= 20; i++) {
            heap.addElement(i);
        }

        // Remove elements and ensure they are in sorted order
        for (int i = 1; i <= 20; i++) {
            assertEquals(i, heap.removeMin());
        }

        // Check if the heap is empty
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testEmptyHeapException() {
        // Check if removing from an empty heap throws an exception
        Exception exception = assertThrows(EmptyCollectionException.class, () -> {
            heap.removeMin();
        });

        assertEquals("Empty Heap", exception.getMessage());
    }
}

