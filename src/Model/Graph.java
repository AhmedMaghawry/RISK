package Model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    private  ArrayList <Integer> partitionBonus;
    private  LinkedList<Vertex> adjListArray[];
    private Integer verticesNum ;
    public Graph( Integer verticesNum ){
        this.verticesNum = verticesNum;

        // define the size of array as
        // number of vertices
        adjListArray = new LinkedList[verticesNum];

        // Create a new list for each vertex
        // such that adjacent nodes can be stored
        for(int i = 0; i < verticesNum; i++){
            adjListArray[i] = new LinkedList<>();
        }
    }
   // Adds an edge to an undirected graphvr
    public void addEdge(Graph graph, int src, int dest,Vertex vertex)
    {
        // Add an edge from src to dest.
        graph.adjListArray[src].add(vertex);

        // Since graph is undirected, add an edge from dest
        // to src also
        graph.adjListArray[dest].add(vertex);
    }
}
