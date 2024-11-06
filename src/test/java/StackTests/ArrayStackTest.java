
import Exceptions.EmptyCollectionException;
import Stack.ArrayStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayStackTest {

    private ArrayStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new ArrayStack<>();
    }

    @Test
    public void testPushAndSize() {
        assertEquals(0, stack.size(), "Stack should be empty initially");
        stack.push(1);
        assertEquals(1, stack.size(), "Stack size should be 1 after one push");
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size(), "Stack size should be 3 after three pushes");
    }

    @Test
    public void testPop() throws EmptyCollectionException {
        stack.push(10);
        stack.push(20);
        int topElement = stack.pop();
        assertEquals(20, topElement, "Popped element should be the last pushed element");
        assertEquals(1, stack.size(), "Stack size should decrease by 1 after pop");
    }

    @Test
    public void testPopOnEmptyStack() {
        assertThrows(EmptyCollectionException.class, () -> stack.pop(), "Pop on empty stack should throw EmptyCollectionException");
    }

    @Test
    public void testPeek() throws EmptyCollectionException {
        stack.push(100);
        stack.push(200);
        int topElement = stack.peek();
        assertEquals(200, topElement, "Peek should return the last pushed element without removing it");
        assertEquals(2, stack.size(), "Stack size should not change after peek");
    }

    @Test
    public void testPeekOnEmptyStack() {
        assertThrows(EmptyCollectionException.class, () -> stack.peek(), "Peek on empty stack should throw EmptyCollectionException");
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty(), "New stack should be empty");
        stack.push(5);
        assertFalse(stack.isEmpty(), "Stack should not be empty after push");
        stack.pop();
        assertTrue(stack.isEmpty(), "Stack should be empty after popping the only element");
    }

    @Test
    public void testExpandCapacity() {
        ArrayStack<Integer> smallStack = new ArrayStack<>(2);
        smallStack.push(1);
        smallStack.push(2);
        assertEquals(2, smallStack.size(), "Stack should have two elements");
        smallStack.push(3); // Should trigger expandCapacity
        assertEquals(3, smallStack.size(), "Stack should have three elements after expansion");
    }

    @Test
    public void testToString() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals("321", stack.toString(), "toString should concatenate elements in LIFO order");
    }

    @Test
    public void testPushNullElement() {
        try {
            stack.push(null);
            assertEquals(1, stack.size(), "Stack should accept null elements");
            assertEquals(null, stack.peek(), "The top element should be null");
        } catch (NullPointerException e) {
            fail("Stack should not throw NullPointerException when pushing a null element");
        }
    }

    @Test
    public void testSizeAfterPop() throws EmptyCollectionException {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        stack.pop();
        assertEquals(2, stack.size(), "Stack size should be 2 after one pop");

        stack.pop();
        assertEquals(1, stack.size(), "Stack size should be 1 after two pops");

        stack.pop();
        assertEquals(0, stack.size(), "Stack size should be 0 after three pops");
    }
}
