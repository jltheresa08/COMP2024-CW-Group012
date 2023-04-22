package com.aim.aco.Ants;

import com.aim.aco.Graph.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * This class mimics an ant in the ACO algorithm
 */
public class Ant {

    private Graph graph;
    private Node currentNode;
    private HashSet<Node> locationVisited;
    private ArrayList<Node> tour;

    /**
     * Ant constructor.
     * @param graph    trails that the ants will traverse
     */
    public Ant (Graph graph) {
        this.graph = graph;
        Reset();
    }

    /**
     * Reset the Ant's current position, tour, and history.
     */
    public void Reset () {
        currentNode = getRandomVertex();
        locationVisited = new HashSet<>();
        tour = new ArrayList<>();
    }

    /**
     * Choose a vertex at random from the graph.
     * @return      a random vertex
     */
    private Vertex getRandomVertex () {
        int r = new Random().nextInt(graph.getNumVertices());
        Iterator<Vertex> it = graph.iterator();
        for (int i = 0; i < r; i++) {
            it.next();
        }
        return it.next();
    }

    /**
     * Enable the Ant to go to the following Vertex.
     */
    public void travel () {
        //throw statement to prevent ant travel after tour is completed
        if (!notEndJourney()) {
            throw new IllegalStateException("Tour is complete, no more travel is allowed.");
        }

        // no more edges = complete tour, need to add starting point
        if (graph.getNumVertices() == tour.size()) {
            tour.add(tour.get(0));
            return;
        }

        Edge e = getNextEdge();
        locationVisited.add(e);
        tour.add(e);
        currentNode = e;
    }

    /**
     * Check if the Ant has made a complete tour around the graph.
     * Complete tour = total vertices + starting point
     * @return      true if ant completed the tour
     */
    public boolean notEndJourney() {
        return graph.getNumVertices() + 1 != tour.size();
    }

    /**
     * @return   the complete route that the ant took
     */
    public Node[] getTour () {
        if (notEndJourney()) {
            throw new IllegalStateException("Cannot return an incomplete tour.");
        }
        //create an array of nodes to store location
        Node[] nodes = new Node[tour.size()];
        for (int i = 0; i < tour.size(); i++) {
            nodes[i] = tour.get(i);
        }
        return nodes;
    }

    /**
     * @return      the sum of the total distances
     */
    public int sumDistance() {
        int sumDistance = 0;

        for (int i = 1; i < tour.size(); i++) {
            sumDistance += Graph.getDistance(tour.get(i), tour.get(i-1));
        }

        return sumDistance;
    }

    /**
     * Determine which Edge the ant should visit next. Accounts for both
     * the pheromones and distances.
     * @return  next edge to be traveled to
     */
    public Edge getNextEdge() {
        ArrayList<Pair<Edge, Double>> probabilities = probabilities();
        double r = new Random().nextDouble();

        for (Pair<Edge, Double> pair : probabilities) {
            if (r <= pair.item2) {
                return pair.item1;
            }
        }

        throw new AssertionError("No Edge could be selected.");
    }

    /**
     * Return the probabilities of each edge in form of arrays
     * Example: if there are 4 edges, each with a probability of 0.25, then
     * the array list will contain [[edge1,0.25], [edge2,0.50], [edge3,0.75], [edge4,1.00]].
     * @return      the probabilities of each edge
     */
    private ArrayList<Pair<Edge, Double>> probabilities () {
        double denominator = denominator();
        ArrayList<Pair<Edge, Double>> probabilities = new ArrayList<>(getNumValidEdges());

        for (Edge e : graph.getVertex(currentNode)) {
            if (locationVisited.contains(e)) continue;
            Pair<Edge, Double> pair = new Pair<>();
            //probability =  numerator/denominator
            if (probabilities.size() == 0) {
                pair.item2 = numerator(e) / denominator;
            } else {
                int i = probabilities.size() - 1;
                pair.item2 = probabilities.get(i).item2 + numerator(e) / denominator;
            }

            pair.item1 = e;
            probabilities.add(pair);
        }

        return probabilities;
    }

    /**
     * Check whether edges are valid for travel
     * Edges that are traversed are invalid
     * @return      the number of valid edges
     */
    private int getNumValidEdges() {
        int i = 0;
        for (Edge e : graph.getVertex(currentNode)) {
            if (!locationVisited.contains(e)) {
                i++;
            }
        }
        return i;
    }

    /**
     * denominator = summation(numerator)
     * @return      the sum of all the probabilities of each edge
     */
    private double denominator () {
        double denominator = 0.0;
        for (Edge e : graph.getVertex(currentNode)) {
            if (locationVisited.contains(e)) continue;
            denominator += numerator(e);
        }
        return denominator;
    }

    /**
     * pheromone = e ^ alpha
     * Heuristic information/distance value = (1/distance)^beta
     * @param e     the Edge to perform the calculations on
     * @return      the numerator of the Edge which is product of two results
     */
    private double numerator(Edge e) {
        double pheromone = Math.pow(e.getPheromone(), graph.getAlpha());
        double distance = Graph.getDistance(currentNode, e);
        double distanceValue = Math.pow(1/distance, graph.getBeta());
        return pheromone * distanceValue;
    }

    /**
     * Holds a pair of items.
     * @param <X>   the first item type
     * @param <Y>   the second item type
     */
    private static class Pair<X, Y> {
        X item1;
        Y item2;
    }

    /**
    override the toString function to print the location
     traveled by ants
     Example: 1 -> 2 -> 3 -> 4
     */
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (Node node : tour) {
            if (flag) sb.append(" -> ");
            flag = true;
            sb.append(node.getName());
        }
        return new String(sb);
    }

}
