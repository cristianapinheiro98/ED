package QueueTests;

import Exceptions.EmptyCollectionException;
import Queue.ArrayQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayQueueTest {

    private ArrayQueue<Integer> queue;

    @BeforeEach
    public void setUp() {
        queue = new ArrayQueue<>(4); // Começamos com uma capacidade pequena para testar expansão
    }

    @Test
    public void testEnqueueAndSize() {
        assertEquals(0, queue.size(), "O tamanho inicial da fila deve ser 0.");

        queue.enqueue(1);
        assertEquals(1, queue.size(), "O tamanho da fila após adicionar um elemento deve ser 1.");

        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size(), "O tamanho da fila após adicionar três elementos deve ser 3.");
    }

    @Test
    public void testDequeueAndFIFOOrder() throws EmptyCollectionException {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue(), "O primeiro elemento removido deve ser 1.");
        assertEquals(2, queue.dequeue(), "O segundo elemento removido deve ser 2.");
        assertEquals(3, queue.dequeue(), "O terceiro elemento removido deve ser 3.");
    }

    @Test
    public void testFirst() throws EmptyCollectionException {
        queue.enqueue(1);
        queue.enqueue(2);

        assertEquals(1, queue.first(), "O primeiro elemento da fila deve ser 1.");
        assertEquals(2, queue.size(), "O tamanho da fila após chamar first() deve permanecer o mesmo.");
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty(), "A fila deve estar vazia inicialmente.");

        queue.enqueue(1);
        assertFalse(queue.isEmpty(), "A fila não deve estar vazia após adicionar um elemento.");

        queue.dequeue();
        assertTrue(queue.isEmpty(), "A fila deve estar vazia após remover o único elemento.");
    }

    @Test
    public void testDequeueOnEmptyQueueThrowsException() {
        assertThrows(EmptyCollectionException.class, () -> {
            queue.dequeue();
        }, "Remover de uma fila vazia deve lançar EmptyCollectionException.");
    }

    @Test
    public void testFirstOnEmptyQueueThrowsException() {
        assertThrows(EmptyCollectionException.class, () -> {
            queue.first();
        }, "Chamar first() em uma fila vazia deve lançar EmptyCollectionException.");
    }

    @Test
    public void testExpandCapacity() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        // A próxima chamada deve disparar o método expandCapacity, dobrando o tamanho da fila
        queue.enqueue(4);
        assertEquals(4, queue.size(), "O tamanho da fila deve ser 4 após expansão e adição de um quarto elemento.");

        // Confirma que a fila ainda mantém a ordem correta
        assertEquals(1, queue.dequeue(), "Após expansão, o primeiro elemento removido deve ser 1.");
        assertEquals(2, queue.dequeue(), "Após expansão, o segundo elemento removido deve ser 2.");
        assertEquals(3, queue.dequeue(), "Após expansão, o terceiro elemento removido deve ser 3.");
        assertEquals(4, queue.dequeue(), "Após expansão, o quarto elemento removido deve ser 4.");
    }

    @Test
    public void testToStringFilaVazia() {
        // Testa o toString em uma fila vazia
        String esperado = "";
        assertEquals(esperado, queue.toString());
    }

    @Test
    public void testToStringAposEnqueue() {
        // Adiciona elementos à fila
        queue.enqueue(1);
        String esperado1 = "1";
        assertEquals(esperado1, queue.toString());

        queue.enqueue(2);
        String esperado2 = "12";
        assertEquals(esperado2, queue.toString());

        queue.enqueue(3);
        String esperado3 = "123";
        assertEquals(esperado3, queue.toString());
    }

    @Test
    public void testToStringAposDequeue() throws EmptyCollectionException {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        queue.dequeue();
        assertEquals("23", queue.toString());

        queue.dequeue();
        assertEquals("3", queue.toString());

        queue.dequeue();
        assertEquals("", queue.toString());
    }

    @Test
    public void testToStringComportamentoCircular() throws EmptyCollectionException {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        queue.dequeue();
        queue.dequeue();

        queue.enqueue(6);
        queue.enqueue(7);

        assertEquals("34567", queue.toString());
    }

    @Test
    public void testToStringAposExpandCapacity() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6); // Deve acionar o expandCapacity()

        assertEquals("123456", queue.toString());
    }

    @Test
    public void testToStringComElementosNulos() {
        // Testa o comportamento ao enfileirar um elemento nulo (se permitido)
        queue.enqueue(null);
        queue.enqueue(1);

        assertEquals("null1", queue.toString());
    }

    @Test
    public void testFirstOnEmptyQueue() {
        assertThrows(EmptyCollectionException.class, () -> queue.first(),
                "Deve lançar EmptyCollectionException ao chamar first() em uma fila vazia.");
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

