package LinkedListTests;


import LinkedList.ArrayOrderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

    public class ArrayOrderedListIteratorTest {

        private ArrayOrderedList<Integer> orderedList;

        @BeforeEach
        public void setUp() {
            orderedList = new ArrayOrderedList<>(5); // Capacidade inicial de 5
            orderedList.add(10);
            orderedList.add(20);
            orderedList.add(30);
        }

        // Testando o iterator sem modCount sendo alterado
        @Test
        public void testIteratorWithoutModCount() {
            Iterator<Integer> iterator = orderedList.iterator();
            assertTrue(iterator.hasNext());
            assertEquals((Integer)10, iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals((Integer)20, iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals((Integer)30, iterator.next());
            assertFalse(iterator.hasNext());
        }

        // Testando o iterator com modCount sendo alterado (exceção esperada)
        @Test
        public void testIteratorWithConcurrentModificationException() {
            Iterator<Integer> iterator = orderedList.iterator();
            orderedList.add(40);  // Modifica a lista após o iterator ser criado, alterando o modCount
            assertThrows(ConcurrentModificationException.class, () -> {
                iterator.next();  // Deve lançar ConcurrentModificationException
            });
        }

        // Testando remoção de um item da lista sem alterar o modCount após a iteração começar
        @Test
        public void testIteratorWithRemoveFirstElement() {
            orderedList.removeFirst();  // Remove o primeiro elemento da lista antes de criar o iterator
            Iterator<Integer> iterator = orderedList.iterator();
            assertEquals((Integer)20, iterator.next());  // Primeiro elemento agora é 20
            assertEquals((Integer)30, iterator.next());  // Próximo elemento é 30
            assertFalse(iterator.hasNext());
        }

        // Testando remover um item da lista após a criação do iterator, causando uma exceção de modificação concorrente
        @Test
        public void testIteratorRemoveFirstElementWithModCount() {
            Iterator<Integer> iterator = orderedList.iterator();
            orderedList.removeFirst();  // Modifica a lista após a criação do iterator
            assertThrows(ConcurrentModificationException.class, () -> {
                iterator.next();  // Deve lançar ConcurrentModificationException
            });
        }
    }


