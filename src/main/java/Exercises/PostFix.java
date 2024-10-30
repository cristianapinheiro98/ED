package Exercises;

import Exceptions.EmptyCollectionException;
import Stack.LinkedStack;
import Stack.StackADT;

public class PostFix<T> {
    public StackADT<Integer> calculator;

    public PostFix() {
        this.calculator = new LinkedStack<>();//ao ser do tipo da interface, basta mudar ArrayStack para
        // LinkedStack no construtor e funciona na mesma pq ambas as classes implementam a mesma interface
    }

    public int Calculate(char operator, int value1, int value2) {
        switch (operator) {
            case '+':
                return value1 + value2;
            case '-':
                return value1 - value2;
            case '*':
                return value1 * value2;
            case '/':
                if (value2 == 0) {
                    throw new ArithmeticException("Division by zero");
                } else {
                    return value1 / value2;
                }
        }
        return -1;

    }

    public void ReadMathExpression(String s) throws EmptyCollectionException {
        String[] elements = s.split(" ");

        /*if(calculator.isEmpty()){
            throw new EmptyCollectionException("empty collection");
        }*/

        for (int i = 0; i < elements.length; i++){
            if(elements[i].matches("[0-9]+")){
                int number = Integer.parseInt(elements[i]);
                calculator.push(number);
            }
            if(elements[i].matches("[+*/-]")){
                int value2 = calculator.pop();
                int value1 = calculator.pop();
                int result = Calculate(elements[i].charAt(0), value1, value2);
                calculator.push(result);

/*Faço pop dos dois últimos valores guardados na stack, depois envio para o método calculate o operador,
e dos dois valores. Este método devolve o resultado da operação. O resultado é enviado para o método do
push para guardar no topo da pilha*/
            }
        }
    }

    //há erros nos testes! necessário retificar a classe
}
