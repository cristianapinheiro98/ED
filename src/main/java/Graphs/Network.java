package Graphs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import LinkedList.ArrayUnorderedList;
import Queue.LinkedQueue;
import Stack.LinkedStack;

import java.util.Iterator;

public class Network<T>  extends Graph<T> implements NetworkADT<T>  {
    protected double[][] adjMatrix;

    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if(vertex1 == null || vertex2 == null) {//testar se é necessária esta validaçao
            throw new NullPointerException("vertex is null");
        }

        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }

    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {//super do djisktra, devolve o iterator e usamos esse iterador para percorrer a lista e adicionar os pesos
        return 0;
    }

    @Override
    public void addVertex(T vertex) {
        if(vertex == null){
            throw new NullPointerException("vertex is null");
        }

        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;

        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }

        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph is empty");

        if(vertex == null){
            throw new NullPointerException("vertex is null");
        }

        int pos = this.getIndex(vertex);
        if (pos == -1) throw new ElementNotFoundException("Vertex not found");

        for (int i = 0; i < this.numVertices; i++) {
            this.adjMatrix[pos][i] = Double.POSITIVE_INFINITY;
            this.adjMatrix[i][pos] = Double.POSITIVE_INFINITY;
        }

        for (int i = pos; i < this.numVertices - 1; i++) {
            this.vertices[i] = this.vertices[i + 1];
            for (int j = 0; j < this.numVertices; j++) {
                this.adjMatrix[i][j] = this.adjMatrix[i + 1][j];
                this.adjMatrix[j][i] = this.adjMatrix[j][i + 1];
            }
        }

        this.numVertices--;

    }


    @Override
    public void removeEdge(T vertex1, T vertex2) {
        if (this.isEmpty()) throw new EmptyCollectionException("Graph is empty");

        if(vertex1 == null || vertex2 == null) {
            throw new NullPointerException("vertex is null");
        }

        int pos1 = this.getIndex(vertex1);
        int pos2 = this.getIndex(vertex2);
        if (pos1 == -1 || pos2 == -1) throw new ElementNotFoundException("vertex not found");

        this.adjMatrix[pos1][pos2] = Double.POSITIVE_INFINITY;
        this.adjMatrix[pos2][pos1] = Double.POSITIVE_INFINITY;

    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();//pq não usar a interface?
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int startIndex = this.getIndex(startVertex);

        if (!indexIsValid(startIndex))//perguntar ao oscr! throw??
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] < Double.POSITIVE_INFINITY && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();

    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int startIndex = this.getIndex(startVertex);
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;



            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x][i] < Double.POSITIVE_INFINITY && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }

            if (!found && !traversalStack.isEmpty())
                traversalStack.pop();
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {//djisktra
        return null;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public int size() {
        return super.size();
    }

    private void expandCapacity() {
        T[] vertices_tmp = (T[]) (new Object[this.vertices.length * FACTOR]);
        double [][] AdjMatrix_tmp = new double [this.vertices.length * FACTOR][this.vertices.length * FACTOR];

        for (int i = 0; i < this.vertices.length; i++) {
            System.arraycopy(this.adjMatrix[i], 0, AdjMatrix_tmp[i], 0, this.numVertices);
            vertices_tmp[i] = this.vertices[i];
        }

        this.vertices = vertices_tmp;
        this.adjMatrix = AdjMatrix_tmp;
    }
}
