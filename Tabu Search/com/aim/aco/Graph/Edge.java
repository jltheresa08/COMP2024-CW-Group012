package com.aim.aco.Graph;


public class Edge extends Node {

    private double pheromone;

    /**
     * edge constructor
     * @param name      The name of the Edge.
     * @param x         The x coordinate of the Edge
     * @param y         The y coordinate of the Edge
     */
    public Edge (String name, int x, int y) {
        super(name, x, y);
        pheromone = 0.01;
    }

    public void setPheromone (double pheromone) {
        this.pheromone = pheromone;
    }

    public double getPheromone () {
        return pheromone;
    }

}

