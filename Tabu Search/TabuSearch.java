import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TabuSearch {
    // Class to represent a city node
    static class CityNode {
        int id;
        double x, y;
        
        public CityNode(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
    
    // Class to represent a tour
    static class Tour {
        List<Integer> path;
        double cost;
        
        public Tour(List<Integer> path, double cost) {
            this.path = path;
            this.cost = cost;
        }
    }
    
    // Tabu search algorithm
    static Tour tabuSearch(List<CityNode> nodes, int iterations, int tabuSize) {
        // Initialize tour with a random path
        List<Integer> path = new ArrayList<Integer>();
        for (int i = 0; i < nodes.size(); i++) {
            path.add(i);
        }
        Collections.shuffle(path);
        double cost = tourCost(nodes, path);
        Tour best = new Tour(path, cost);
        
        // Initialize tabu list and candidate list
        List<Integer> tabuList = new ArrayList<Integer>();
        List<Tour> candidateList = new ArrayList<Tour>();
        
        // Run tabu search iterations
        for (int i = 0; i < iterations; i++) {
            // Generate candidate solutions
            for (int j = 0; j < nodes.size(); j++) {
                for (int k = j + 1; k < nodes.size(); k++) {
                    List<Integer> candidatePath = new ArrayList<Integer>(best.path);
                    Collections.swap(candidatePath, j, k);
                    double candidateCost = tourCost(nodes, candidatePath);
                    Tour candidate = new Tour(candidatePath, candidateCost);
                    candidateList.add(candidate);
                }
            }
            
            // Sort candidate list by cost
            Collections.sort(candidateList, (t1, t2) -> Double.compare(t1.cost, t2.cost));
            
            // Find best candidate not in tabu list
            Tour bestCandidate = null;
            for (Tour candidate : candidateList) {
                if (!tabuList.contains(candidate.path.get(0))) {
                    bestCandidate = candidate;
                    break;
                }
            }
            if (bestCandidate == null) {
                bestCandidate = candidateList.get(0);
            }
            
            // Update best solution
            if (bestCandidate.cost < best.cost) {
                best = bestCandidate;
            }
            
            // Update tabu list
            tabuList.add(best.path.get(0));
            if (tabuList.size() > tabuSize) {
                tabuList.remove(0);
            }
            
            // Clear candidate list
            candidateList.clear();
        }
        
        return best;
    }
    
    // Calculate the cost of a tour
    static double tourCost(List<CityNode> nodes, List<Integer> path) {
        double cost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            int node1 = path.get(i);
            int node2 = path.get(i + 1);
            cost += distance(nodes.get(node1), nodes.get(node2));
        }
        cost += distance(nodes.get(path.get(path.size() - 1)), nodes.get(0));
        return cost;
    }

 // Calculate the Euclidean distance between two cities
    static double distance(CityNode node1, CityNode node2) {
        double dx = node1.x - node2.x;
        double dy = node1.y - node2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // Read city nodes from a text file
    static List<CityNode> readNodesFromFile(String filename) throws Exception {
        List<CityNode> nodes = new ArrayList<CityNode>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null && !line.equals("EOF")) {
            String[] fields = line.split("\\s+");
            int id = Integer.parseInt(fields[0]);
            double x = Double.parseDouble(fields[1]);
            double y = Double.parseDouble(fields[2]);
            nodes.add(new CityNode(id, x, y));
        }
        reader.close();
        return nodes;
    }

    public static void main(String[] args) throws Exception {
        // Read city nodes from file
        List<CityNode> nodes = readNodesFromFile("TSP_107.txt");
        
        // Run tabu search algorithm
        int iterations = 75000;
        int tabuSize = 100;
        Tour best = tabuSearch(nodes, iterations, tabuSize);
        
        // Print results
        System.out.println("Best tour cost: " + best.cost);
        System.out.println("Best tour path: " + Arrays.toString(best.path.toArray()));
    }
}

