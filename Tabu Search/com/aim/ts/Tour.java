package com.aim.ts;
import java.util.List;

/**
 * This class holds tour path and cost.
 */
public class Tour {

	 List<Integer> path;
     double cost;
     
     /**
      * Constructs a Tour object with the given path and cost.
      * @param path The path of the tour as a list of city nodes.
      * @param cost The cost of the tour.
      */
     public Tour(List<Integer> path, double cost) {
         this.path = path;
         this.cost = cost;
     }
}

