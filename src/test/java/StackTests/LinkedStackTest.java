
import Exceptions.EmptyCollectionException;
import Stack.LinkedStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedStackTest {
    private LinkedStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new LinkedStack<>();
    }

    @Test
    void testPushAndSize() {
        assertEquals(0, stack.size(), "Initially, the stack should be empty.");

        stack.push(10);
        assertEquals(1, stack.size(), "Stack size should be 1 after one push.");

        stack.push(20);
        stack.push(30);
        assertEquals(3, stack.size(), "Stack size should be 3 after three pushes.");
    }

    @Test
    void testPop() throws EmptyCollectionException {
        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.pop(), "The last pushed element (20) should be popped first.");
        assertEquals(1, stack.size(), "Stack size should be 1 after one pop.");

        assertEquals(10, stack.pop(), "The next element to be popped should be 10.");
        assertEquals(0, stack.size(), "Stack should be empty after popping all elements.");
    }

    @Test
    public void testPopOnEmptyStack() {
        LinkedStack<Integer> stack = new LinkedStack<>();

        // Verifica se EmptyCollectionException é lançada
        assertThrows(EmptyCollectionException.class, () -> {
            stack.pop();
        });
    }

    @Test
    void testPeek() throws EmptyCollectionException {
        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.peek(), "Peek should return the last pushed element (20) without removing it.");
        assertEquals(2, stack.size(), "Stack size should remain 2 after a peek.");
    }

    @Test
    void testPeekOnEmptyStack() {
        assertThrows(NullPointerException.class, () -> stack.peek(), "Peeking an empty stack should throw a NullPointerException.");
    }

    @Test
    void testIsEmpty() {
        assertTrue(stack.isEmpty(), "A newly created stack should be empty.");

        stack.push(10);
        assertFalse(stack.isEmpty(), "Stack should not be empty after a push.");

        stack.pop();
        assertTrue(stack.isEmpty(), "Stack should be empty after popping all elements.");
    }

    @Test
    public void testToStringEmptyStack() {
        // Testa o toString em uma pilha vazia
        String expected = "";
        assertEquals(expected, stack.toString());
    }

    @Test
    public void testToStringOneElement() {
        // Adiciona um elemento à pilha
        stack.push(10);
        String expected = "Element: 10 Next: null \n";
        assertEquals(expected, stack.toString());
    }

    @Test
    public void testToStringMultipleElements() {
        // Adiciona múltiplos elementos
        stack.push(10);
        stack.push(20);
        stack.push(30);

        String expected =
                "Element: 30 Next: 20\n" +
                        "Element: 20 Next: 10\n" +
                        "Element: 10 Next: null \n";

        assertEquals(expected, stack.toString());
    }

    @Test
    public void testToStringAfterPop() throws EmptyCollectionException {
        // Adiciona elementos e remove um
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.pop(); // Remove o 30

        String expected =
                "Element: 20 Next: 10\n" +
                        "Element: 10 Next: null \n";

        assertEquals(expected, stack.toString());
    }

    @Test
    public void testToStringAfterPushAndPop() throws EmptyCollectionException {
        // Testa após várias operações de push e pop
        stack.push(10);
        stack.push(20);
        stack.pop(); // Remove o 20
        stack.push(30);
        stack.push(40);
        stack.pop(); // Remove o 40
        stack.push(50);

        String expected =
                "Element: 50 Next: 30\n" +
                        "Element: 30 Next: 10\n" +
                        "Element: 10 Next: null \n";

        assertEquals(expected, stack.toString());
    }

    @Test
    public void testToStringAfterClearingStack() throws EmptyCollectionException {
        // Adiciona elementos e remove todos
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.pop();
        stack.pop();
        stack.pop();

        String expected = "";
        assertEquals(expected, stack.toString());
    }

    @Test
    public void testPushNullElement() {
        try {
            stack.push(null);
            assertEquals(1, stack.size(), "Stack should accept null elements.");
            assertEquals(null, stack.peek(), "The top element should be null.");
        } catch (NullPointerException e) {
            fail("Stack should not throw NullPointerException when pushing a null element.");
        }
    }

    @Test
    public void testSizeAfterPop() throws EmptyCollectionException {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        stack.pop();
        assertEquals(2, stack.size(), "Stack size should be 2 after one pop.");

        stack.pop();
        assertEquals(1, stack.size(), "Stack size should be 1 after two pops.");

        stack.pop();
        assertEquals(0, stack.size(), "Stack size should be 0 after three pops.");
    }
}


