package com.aim.ts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TabuSearch {

    /**
     * Tabu Search to find the best tour for the given city nodes.
     * @param nodes The city nodes to find the best tour.
     * @param iterations The number of iterations to generate candidate solutions.
     * @param tabuSize The size of the tabu list/tenure.
     * @return The best tour as a Tour object.
     */
    static Tour tabuSearch(List<Cities> nodes, int iterations, int tabuSize) {

        // Get number of cities. TSP_107 has 107 cities.
        int numCities = nodes.size();

        // Initialize tour with a random path using shuffle random permutation.
        List<Integer> path = new ArrayList<Integer>();
        for (int i = 0; i < numCities; i++) {
            path.add(i);
        }
        Collections.shuffle(path);
        double cost = tourCost(nodes, path);
        Tour best = new Tour(path, cost);
        
        // Initialize tabu list and candidate list.
        List<Integer> tabuList = new ArrayList<Integer>();
        List<Tour> candidateList = new ArrayList<Tour>();
        
        // Run tabu search iterations.
        for (int i = 0; i < iterations; i++) {
            // Generate candidate solutions by swapping cities.
            for (int j = 0; j < numCities; j++) {
                for (int k = j + 1; k < numCities; k++) {
                    List<Integer> candidatePath = new ArrayList<Integer>(best.path);
                    Collections.swap(candidatePath, j, k);
                    double candidateCost = tourCost(nodes, candidatePath);
                    Tour candidate = new Tour(candidatePath, candidateCost);
                    candidateList.add(candidate);
                }
            }
            
            // Sort candidate list by cost in ascending order.
            Collections.sort(candidateList, (t1, t2) -> Double.compare(t1.cost, t2.cost));
            
            // Find best candidate not in tabu list.
            Tour bestCandidate = null;
            for (Tour candidate : candidateList) {
                if (!tabuList.contains(candidate.path.get(0))) {
                    bestCandidate = candidate;
                    break;
                }
            }

            // If best candidate has yet to be assigned.
            if (bestCandidate == null) {
                bestCandidate = candidateList.get(0);
            }
            
            // Update best solution.
            if (bestCandidate.cost < best.cost) {
                best = bestCandidate;
            }
            
            // Update tabu list.
            tabuList.add(best.path.get(0));
            if (tabuList.size() > tabuSize) {
                tabuList.remove(0);
            }
            
            // Clear candidate list.
            candidateList.clear();
        }
        
        return best;
    }
    
    /**
     * Calculates the cost of a tour given a list of Cities objects and a path.
     * @param nodes a list of Cities objects representing the cities
     * @param path the path of the tour as a list of city node
     * @return the cost of the tour
     */
    static double tourCost(List<Cities> nodes, List<Integer> path) {
        double cost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            int node1 = path.get(i);
            int node2 = path.get(i + 1);
            cost += distance(nodes.get(node1), nodes.get(node2));
        }
        cost += distance(nodes.get(path.get(path.size() - 1)), nodes.get(0));
        return cost;
    }

    /**
     * Calculates the Euclidean distance between two Cities objects.
     * @param node1 the first city node
     * @param node2 the second city node
     * @return the Euclidean distance between the two city nodes
     */
    static double distance(Cities node1, Cities node2) {
        double dx = node1.x - node2.x;
        double dy = node1.y - node2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Reads a list cities from a text file and returns them as a list of Cities objects.
     * @param filename the name of the text file to read from
     * @return a list of Cities objects representing the cities
     * @throws Exception if there is an error reading the file
     */
    static List<Cities> readNodesFromFile(String filename) throws Exception {
        List<Cities> nodes = new ArrayList<Cities>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null && !line.equals("EOF")) {
            String[] fields = line.split("\\s+");
            int id = Integer.parseInt(fields[0]);
            double x = Double.parseDouble(fields[1]);
            double y = Double.parseDouble(fields[2]);
            nodes.add(new Cities(id, x, y));
        }
        reader.close();
        return nodes;
    }
    
    /**
     * Creates an output file called output.txt which contains all items in TSP_107 but in order of traversal.
     * @param nodes a list of Cities objects representing the cities
     * @param path the path of the tour as a list of city node
     * @param filename source file to copy data from
     * @throws Exception if there is an error reading the file
     */
    static void outputFile(List<Cities> nodes, List<Integer> path, String filename) throws Exception {
        
	    Object[] order = path.toArray();
	    String[] lines = Files.readAllLines(Paths.get(filename)).toArray(new String[0]);
	    StringBuilder sb = new StringBuilder();

        // Reorder the items copied from TSP_107.txt in the order of traversal of path.
	    for (int i = 0; i < order.length; i++) {
	        sb.append(lines[(int) order[i]]);
	        sb.append("\n");
	    }

        // Write into output.txt.
	    Files.write(Paths.get("output.txt"), sb.toString().getBytes());
    }


    public static void main(String[] args) throws Exception {
 
        // Set number of iterations and size of tabu list.
        int iterations = 7500;
        int tabuSize = 100;

        // Get start time.
        long start = System.currentTimeMillis();

        // Read cities and its coordinates from file.
        List<Cities> nodes = readNodesFromFile("TSP_107.txt");
    
        // Run tabu search.
        Tour best = tabuSearch(nodes, iterations, tabuSize);
        
        // Get end time.
        long end = System.currentTimeMillis();
        
        //Calculate the running time.
        long runningTime = end - start;
	    
        // Print results.
        System.out.println("Running time: " + runningTime + " milliseconds");
        System.out.println("Best tour cost: " + best.cost);
        int[] bestPath = best.path.stream().mapToInt(i -> i + 1).toArray(); // change from city node start at 0 to start at 1
        System.out.println("Best tour path: " + Arrays.toString(bestPath));

        // Save city nodes and coordinates in order of path in output.txt.
        outputFile(nodes, best.path, "TSP_107.txt");
        
        // Append total cost of tour to last line of output.txt.
        FileWriter fr = new FileWriter("output.txt", true);
        fr.write("Best:" + best.cost);
        fr.close();

    }
}
