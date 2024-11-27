package Graphs;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import LinkedList.ArrayUnorderedList;
import Queue.LinkedQueue;
import Stack.LinkedStack;

import java.util.Iterator;

/**
 * Graph represents an adjacency matrix implementation of a graph.
 *
 */
public class Graph<T> implements GraphADT<T> {
    protected final int FACTOR = 2;
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices;   // number of vertices in the graph
    protected boolean[][] adjMatrix;   // adjacency matrix
    protected T[] vertices;   // values of vertices

    /**
     * Creates an empty graph.
     */
    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1  the first vertex
     * @param vertex2  the second vertex
     */
    @Override
    public void addEdge (T vertex1, T vertex2) {
        if(vertex1 == null || vertex2 == null) {//testar se é necessária esta validaçao
            throw new NullPointerException("vertex is null");
        }

        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
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

        this.adjMatrix[pos1][pos2] = false;
        this.adjMatrix[pos2][pos1] = false;
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
                if (adjMatrix[x][i] && !visited[i]) {
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
                if (adjMatrix[x][i] && !visited[i]) {
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
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {//tirar da interface dos grafos e meter na interface do network
       return null;
    }//acrescentar algoritmo de djistra e iterador


    @Override
    public boolean isEmpty() {
        return this.numVertices == 0;
    }

    @Override
    public boolean isConnected() {
        if (isEmpty()) {
          throw new EmptyCollectionException("Graph is empty");
        }

        T startVertex = vertices[0];

        Iterator<T> it = iteratorBFS(startVertex);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }

        return (count == numVertices);
    }

    @Override
    public int size() {
        return this.numVertices;
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph
     * if necessary.  It also associates an object with the vertex.
     *
     * @param vertex  the vertex to add to the graph
     */
    @Override
    public void addVertex (T vertex) {
        if(vertex == null){
            throw new NullPointerException("vertex is null");
        }

        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;

        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
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
            this.adjMatrix[pos][i] = false;
            this.adjMatrix[i][pos] = false;
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

    protected int getIndex(T vertex) {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }

        return -1;
    }

    protected boolean indexIsValid(int index) {
        return index >= 0 && index <= this.numVertices;
    }

    private void expandCapacity() {
        T[] vertices_tmp = (T[]) (new Object[this.vertices.length * FACTOR]);
        boolean[][] AdjMatrix_tmp = new boolean[this.vertices.length * FACTOR][this.vertices.length * FACTOR];

        for (int i = 0; i < this.vertices.length; i++) {
            System.arraycopy(this.adjMatrix[i], 0, AdjMatrix_tmp[i], 0, this.numVertices);
            vertices_tmp[i] = this.vertices[i];
        }

        this.vertices = vertices_tmp;
        this.adjMatrix = AdjMatrix_tmp;
    }
}