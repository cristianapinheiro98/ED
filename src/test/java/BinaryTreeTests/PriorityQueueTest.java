import BinaryTree.PriorityQueue;
import BinaryTree.PriorityQueueNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueTest {

    private PriorityQueue<String> priorityQueue;

    @BeforeEach
    public void setUp() {
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void testAddElement() {
        priorityQueue.addElement("Task A", 5);
        priorityQueue.addElement("Task B", 3);
        priorityQueue.addElement("Task C", 8);

        // Check if the element with the highest priority is at the root
        assertEquals("Task B", priorityQueue.removeNext());
    }

    @Test
    public void testRemoveNext() {
        priorityQueue.addElement("Task A", 5);
        priorityQueue.addElement("Task B", 3);
        priorityQueue.addElement("Task C", 8);

        // Remove elements and ensure they are in the correct order
        assertEquals("Task B", priorityQueue.removeNext());
        assertEquals("Task A", priorityQueue.removeNext());
        assertEquals("Task C", priorityQueue.removeNext());

        // Ensure the queue is now empty
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    public void testPriorityOrderWithSamePriority() {
        priorityQueue.addElement("Task A", 5);
        priorityQueue.addElement("Task B", 5);
        priorityQueue.addElement("Task C", 5);

        // Check the order based on insertion (Task A -> Task B -> Task C)
        assertEquals("Task A", priorityQueue.removeNext());
        assertEquals("Task B", priorityQueue.removeNext());
        assertEquals("Task C", priorityQueue.removeNext());
    }

    @Test
    public void testFindMin() {
        priorityQueue.addElement("Task A", 10);
        priorityQueue.addElement("Task B", 1);
        priorityQueue.addElement("Task C", 15);

        // Check if the element with the minimum priority is found
        assertEquals("Task B", priorityQueue.removeNext());
    }

    @Test
    public void testAddAndRemoveLargeNumberOfElements() {
        for (int i = 10; i >= 1; i--) {
            priorityQueue.addElement("Task " + i, i);
        }

        // Ensure elements are removed in ascending order of priority
        for (int i = 1; i <= 10; i++) {
            assertEquals("Task " + i, priorityQueue.removeNext());
        }

        // Ensure the queue is empty
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    public void testRemoveFromEmptyQueue() {
        // Ensure removing from an empty queue throws an exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            priorityQueue.removeNext();
        });

        assertEquals("Empty Heap", exception.getMessage());
    }

    @Test
    public void testPriorityQueueNode() {
        PriorityQueueNode<String> node1 = new PriorityQueueNode<>("Task A", 5);
        PriorityQueueNode<String> node2 = new PriorityQueueNode<>("Task B", 3);

        // Test priorities
        assertTrue(node1.compareTo(node2) > 0);
        assertTrue(node2.compareTo(node1) < 0);

        // Test same priority with order
        PriorityQueueNode<String> node3 = new PriorityQueueNode<>("Task C", 5);
        assertTrue(node1.compareTo(node3) < 0); // Node1 comes earlier
    }
}
