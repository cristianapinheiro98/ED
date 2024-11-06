import LinkedList.ArrayUnorderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUnorderedListIteratorTest {

    private ArrayUnorderedList<Integer> unorderedList;

    @BeforeEach
    public void setUp() {
        // Instancia o ArrayUnorderedList
        unorderedList = new ArrayUnorderedList<>();
        unorderedList.addToRear(10);
        unorderedList.addToRear(20);
        unorderedList.addToRear(30);
    }

    // Testando o Iterator sem modCount sendo alterado
    @Test
    public void testIteratorWithoutModCount() {
        Iterator<Integer> iterator = unorderedList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(30, iterator.next());
        assertFalse(iterator.hasNext());
    }

    // Testando o Iterator com modCount sendo alterado (modificação na lista durante iteração)
    @Test
    public void testIteratorWithModCountThrowsConcurrentModificationException() {
        Iterator<Integer> iterator = unorderedList.iterator();
        unorderedList.addToRear(40);  // Modifica a lista, incrementando modCount
        assertThrows(ConcurrentModificationException.class, () -> {
            iterator.next();  // Deve lançar ConcurrentModificationException
        });
    }

    // Testando remoção de um item da lista sem alterar o modCount após a iteração começar
    @Test
    public void testIteratorWithRemoveFirstElement() {
        unorderedList.removeFirst();  // Remove o primeiro elemento da lista antes de criar o iterator
        Iterator<Integer> iterator = unorderedList.iterator();
        assertEquals(20, iterator.next());  // Primeiro elemento agora é 20
        assertEquals(30, iterator.next());  // Próximo elemento é 30
        assertFalse(iterator.hasNext());
    }

    // Testando remoção de um item da lista após a criação do iterator, causando uma exceção de modificação concorrente
    @Test
    public void testIteratorRemoveFirstElementWithModCount() {
        Iterator<Integer> iterator = unorderedList.iterator();
        unorderedList.removeFirst();  // Modifica a lista após a criação do iterator
        assertThrows(ConcurrentModificationException.class, () -> {
            iterator.next();  // Deve lançar ConcurrentModificationException
        });
    }
}
