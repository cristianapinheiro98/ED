package ExercisesTests;

import Exceptions.EmptyCollectionException;
import Exercises.PostFix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostFixTest {
    private PostFix<Integer> postFix;

    @BeforeEach
    void setUp() {
        postFix = new PostFix<>();
    }

    @Test
    void testCalculateAddition() {
        int result = postFix.Calculate('+', 3, 5);
        assertEquals(8, result, "3 + 5 should equal 8");
    }

    @Test
    void testCalculateSubtraction() {
        int result = postFix.Calculate('-', 10, 4);
        assertEquals(6, result, "10 - 4 should equal 6");
    }

    @Test
    void testCalculateMultiplication() {
        int result = postFix.Calculate('*', 7, 6);
        assertEquals(42, result, "7 * 6 should equal 42");
    }

    @Test
    void testCalculateDivision() {
        int result = postFix.Calculate('/', 20, 4);
        assertEquals(5, result, "20 / 4 should equal 5");
    }

    @Test
    void testCalculateDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            postFix.Calculate('/', 10, 0);
        }, "Division by zero should throw an ArithmeticException.");
    }

    @Test
    void testReadMathExpressionSimpleAddition() throws EmptyCollectionException {
        postFix.ReadMathExpression("3 4 +");
        int result = postFix.calculator.pop();
        assertEquals(7, result, "3 + 4 should equal 7");
    }

    @Test
    void testReadMathExpressionComplexExpression() throws EmptyCollectionException {
        postFix.ReadMathExpression("5 1 2 + 4 * + 3 -");
        int result = postFix.calculator.pop();
        assertEquals(14, result, "5 + ((1 + 2) * 4) - 3 should equal 14");
    }

    @Test
    void testReadMathExpressionDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            postFix.ReadMathExpression("10 0 /");
        }, "Division by zero in ReadMathExpression should throw an ArithmeticException.");
    }

    @Test
    void testReadMathExpressionEmptyStackException() {
        assertThrows(EmptyCollectionException.class, () -> {
            postFix.ReadMathExpression("+");
        }, "Attempting to pop from an empty stack should throw an EmptyCollectionException.");
    }
}
