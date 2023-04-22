package com.aim.ts;

/**
 * This class holds city node and coordinates associated with each node.
 * 
 * @author Theresa Lim
 *
 */
public class Cities {

	int node;
    double x, y;
    
    /**
     * Constructs a cities object with the given node and coordinates.
     * @param node The node of the city.
     * @param x The x-coordinate of the city.
     * @param y The y-coordinate of the city.
     */
    public Cities(int node, double x, double y) {
        this.node = node;
        this.x = x;
        this.y = y;
    }
}


