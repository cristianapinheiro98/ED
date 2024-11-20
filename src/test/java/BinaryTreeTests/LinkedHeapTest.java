import BinaryTree.LinkedHeap;
import Exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedHeapTest {

    private LinkedHeap<Integer> heap;

    @BeforeEach
    public void setUp() {
        heap = new LinkedHeap<>();
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
    public void testRemoveMin() throws EmptyCollectionException {
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
    public void testRemoveMinUntilEmpty() throws EmptyCollectionException {
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
    public void testFindMin() throws EmptyCollectionException {
        heap.addElement(15);
        heap.addElement(10);
        heap.addElement(20);

        // Find the minimum element
        assertEquals(10, heap.findMin());
    }

    @Test
    public void testAddDuplicateElements() throws EmptyCollectionException {
        heap.addElement(10);
        heap.addElement(10);
        heap.addElement(10);

        // Remove elements and check order
        assertEquals(10, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(10, heap.removeMin());
    }

    @Test
    public void testRemoveFromEmptyHeap() {
        // Ensure removing from an empty heap throws an exception
        Exception exception = assertThrows(EmptyCollectionException.class, () -> {
            heap.removeMin();
        });
        assertEquals("Heap is empty", exception.getMessage());
    }

    @Test
    public void testFindMinInEmptyHeap() {
        // Ensure finding the minimum in an empty heap throws an exception
        Exception exception = assertThrows(EmptyCollectionException.class, () -> {
            heap.findMin();
        });
        assertEquals("Heap is empty", exception.getMessage());
    }

    @Test
    public void testComplexHeapOperations() throws EmptyCollectionException {
        heap.addElement(10);
        heap.addElement(15);
        heap.addElement(5);
        heap.addElement(20);
        heap.addElement(1);

        // Check minimum element
        assertEquals(1, heap.findMin());

        // Remove elements and check order
        assertEquals(1, heap.removeMin());
        assertEquals(5, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(15, heap.removeMin());
        assertEquals(20, heap.removeMin());

        // Heap should now be empty
        assertTrue(heap.isEmpty());
    }
}

