package QueueTests;

import Exceptions.EmptyCollectionException;
import Queue.LinkedQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedQueueTest {

    private LinkedQueue<Integer> queue;

    @BeforeEach
    public void setUp() {
        queue = new LinkedQueue<>();
    }

    @Test
    public void testEnqueue() {
        assertEquals(0, queue.size());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(3, queue.size(), "O tamanho da fila após adicionar três elementos deve ser 3.");
    }

    @Test
    public void testDequeue() throws EmptyCollectionException {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        int firstElement = queue.dequeue();
        assertEquals(1, firstElement, "O primeiro elemento removido deve ser 1.");
        assertEquals(2, queue.size(), "O tamanho da fila após remover um elemento deve ser 2.");

        int secondElement = queue.dequeue();
        assertEquals(2, secondElement, "O segundo elemento removido deve ser 2.");
        assertEquals(1, queue.size(), "O tamanho da fila após remover mais um elemento deve ser 1.");

        int thirdElement = queue.dequeue();
        assertEquals(3, thirdElement, "O terceiro elemento removido deve ser 3.");
        assertEquals(0, queue.size(), "O tamanho da fila após remover todos os elementos deve ser 0.");
    }

    @Test
    public void testDequeueAllElementsAndQueueIsEmpty() throws EmptyCollectionException {
        queue.enqueue(1);
        queue.enqueue(2);

        queue.dequeue();
        queue.dequeue();

        assertEquals(0, queue.size(), "O tamanho da fila deve ser 0 após remover todos os elementos.");

        // Verifica se `front` e `rear` são null após a fila ficar vazia
        assertThrows(EmptyCollectionException.class, () -> {
            queue.dequeue();
        }, "Dequeue de uma fila vazia deve lançar EmptyCollectionException.");
    }

    @Test
    public void testDequeueOnEmptyQueueThrowsException() {
        assertThrows(EmptyCollectionException.class, () -> {
            queue.dequeue();
        }, "Dequeue de uma fila vazia deve lançar EmptyCollectionException.");
    }

    @Test
    public void testToStringEmptyQueue() {
        // Testa o toString em uma fila vazia
        String expected = "";
        assertEquals(expected, queue.toString());
    }

    @Test
    public void testToStringOneElement() {
        // Adiciona um elemento à fila
        queue.enqueue(10);
        String expected = "Element: 10 Next: null \n";
        assertEquals(expected, queue.toString());
    }

    @Test
    public void testToStringMultipleElements() {
        // Adiciona múltiplos elementos
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        String expected =
                "Element: 10 Next: 20\n" +
                        "Element: 20 Next: 30\n" +
                        "Element: 30 Next: null \n";

        assertEquals(expected, queue.toString());
    }

    @Test
    public void testToStringAfterDequeue() throws EmptyCollectionException {
        // Adiciona elementos e remove um
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.dequeue(); // Remove o 10

        String expected =
                "Element: 20 Next: 30\n" +
                        "Element: 30 Next: null \n";

        assertEquals(expected, queue.toString());
    }

    @Test
    public void testToStringAfterEnqueueAndDequeue() throws EmptyCollectionException {
        // Testa após várias operações de enqueue e dequeue
        queue.enqueue(10);
        queue.enqueue(20);
        queue.dequeue(); // Remove o 10
        queue.enqueue(30);
        queue.enqueue(40);
        queue.dequeue(); // Remove o 20
        queue.enqueue(50);

        String expected =
                "Element: 30 Next: 40\n" +
                        "Element: 40 Next: 50\n" +
                        "Element: 50 Next: null \n";

        assertEquals(expected, queue.toString());
    }

    @Test
    public void testToStringAfterClearingQueue() throws EmptyCollectionException {
        // Adiciona elementos e remove todos
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        String expected = "";
        assertEquals(expected, queue.toString());
    }

    @Test
    public void testFirstOnEmptyQueue() {
        assertThrows(EmptyCollectionException.class, () -> queue.first(),
                "Deve lançar EmptyCollectionException ao chamar first() em uma fila vazia.");
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty(), "A fila deve estar vazia inicialmente.");

        queue.enqueue(1);
        assertFalse(queue.isEmpty(), "A fila não deve estar vazia após adicionar um elemento.");

        queue.dequeue();
        assertTrue(queue.isEmpty(), "A fila deve estar vazia após remover todos os elementos.");
    }

    @Test
    public void testEnqueueNullElement() {
        try {
            queue.enqueue(null);
            assertEquals(1, queue.size(), "A fila deve aceitar elementos nulos.");
            assertEquals(null, queue.first(), "O primeiro elemento da fila deve ser nulo.");
        } catch (NullPointerException e) {
            fail("A fila não deve lançar NullPointerException ao adicionar um elemento nulo.");
        }
    }

}


