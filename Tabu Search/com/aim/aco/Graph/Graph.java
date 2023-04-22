package com.aim.aco.Graph;

import com.aim.aco.Ants.Ant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Graph implements Iterable<Vertex> {

    /**
     * hashmap is used to return the same vertex based on edge
     */
    private HashMap<Integer, Vertex> hashMap;

    private ArrayList<Vertex> list;
    private int alpha, beta;
    private double evaporationRate;

    /**
     * constructor for graph
     */
    public Graph (double evaporationRate, int alpha, int beta) {
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        reset();
    }

    public int getAlpha () {
        return alpha;
    }

    public int getBeta () {
        return beta;
    }

    /**
     *  @return      the total number of vertices
     */
    public int getNumVertices() {
        return hashMap.size();
    }

    /**
     * reset the graph
     */
    public void reset() {
        hashMap = new HashMap<>();
        list = new ArrayList<>();
    }

    /**
     * @param vertex    vertex to be added to the graph
     */
    public void addVertex (Vertex vertex) {
        hashMap.put(vertex.hashCode(), vertex);
        list.add(vertex);
    }

    public Vertex getVertex (Node node) {
        return hashMap.get(node.hashCode());
    }

    public void addEdge (Vertex vertex, Node node) {
        vertex.addEdge(node);
    }

    public Node[] getVertices () {
        Node[] nodes = new Node[getNumVertices()];
        int i = 0;
        for (Vertex v : this) {
            nodes[i++] = v;
        }
        return nodes;
    }

    /**
     * apply pheromone to the edges
     * @param ant get the ant tour
     */
    public void applyPheromone(Ant ant) {

        double tourDistance = ant.sumDistance();

        double evaporationFactor = (1 - evaporationRate);

        Node[] edges = ant.getTour();

        HashSet<Edge> hashSet = new HashSet<>();

        for (int i = 1; i < edges.length; i++) {
            Edge firstEdge = getVertex(edges[i-1]).getEdge(edges[i]);
            Edge secondEdge = getVertex(edges[i]).getEdge(edges[i-1]);

            // The pheromones.
            double p1 = firstEdge.getPheromone();
            double p2 = secondEdge.getPheromone();

            hashSet.add(firstEdge);
            hashSet.add(secondEdge);

            firstEdge.setPheromone(evaporationFactor*p1 + 1.0/tourDistance);
            secondEdge.setPheromone(evaporationFactor*p2 + 1.0/tourDistance);
        }

        // apply evaporation
        for (Vertex v : this) {
            for (Edge e : v) {
                if (!hashSet.contains(e)) {
                    double p = e.getPheromone();
                    e.setPheromone(evaporationFactor*p);
                }
            }
        }

    }
    //euclidean formula for distance
    public static double getDistance (Node node1, Node node2) {
        double xDiff = node1.getX() - node2.getX();
        double yDiff = node1.getY() - node2.getY();

        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }

    @Override
    public Iterator<Vertex> iterator() {
        return list.iterator();
    }

}

