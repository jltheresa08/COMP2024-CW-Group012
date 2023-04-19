import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TSPSolver {
    private static Random random = new Random();
    
    private int[][] distances;
    private int numCities;
    
    /**
     * Constructs a new TSPSolver with the given distance matrix.
     * @param distances the distance matrix between cities, where distances[i][j] is the distance from city i to city j.
     */
    public TSPSolver(int[][] distances) {
        this.distances = distances;
        this.numCities = distances.length;
    }
    
    /**
     * Solves the TSP using the Tabu Search algorithm.
     * @param maxIterations the maximum number of iterations to run the algorithm for
     * @return an array representing the order in which the cities should be visited to obtain the optimal solution
     */
    public int[] solve(int maxIterations) {
        int[] current = generateRandomSolution();
        int currentCost = calculateCost(current);
        int[] best = Arrays.copyOf(current, current.length);
        int bestCost = currentCost;
        int tabuTenure = 50;
        int[] tabuList = new int[numCities];
        Arrays.fill(tabuList, -tabuTenure);
        
        for (int i = 0; i < maxIterations; i++) {
            List<int[]> neighbors = generateNeighbors(current);
            int[] next = null;
            int nextCost = Integer.MAX_VALUE;
            for (int[] neighbor : neighbors) {
                int neighborCost = calculateCost(neighbor);
                if (neighborCost < nextCost && tabuList[neighbor[0]] <= i) {
                    next = neighbor;
                    nextCost = neighborCost;
                }
            }
            if (next == null) {
                break;
            }
            current = next;
            currentCost = nextCost;
            if (currentCost < bestCost) {
                best = Arrays.copyOf(current, current.length);
                bestCost = currentCost;
            }
            tabuList[current[0]] = i + tabuTenure;
        }
        
        System.out.println("Order of traversal: " + Arrays.toString(best));
        System.out.println("Total cost of travelling: " + bestCost);
        return best;
    }
    
    /**
     * Generates a random solution to the TSP, where each city is visited exactly once.
     * @return an array representing the order in which the cities should be visited
     */
    private int[] generateRandomSolution() {
        int[] solution = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            solution[i] = i;
        }
        shuffleArray(solution);
        return solution;
    }
    
    /**
     * Shuffles an array randomly.
     * @param array the array to be shuffled
     */
    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    
    /**
     * Calculates the total cost of a given solution to the TSP.
     * @param solution an array representing the order in which the cities should be visited
     * @return the total cost of the solution
     */
    private int calculateCost(int[] solution) {
        int cost = 0;
        for (int i = 0; i < numCities - 1; i++) {
            cost += distances[solution[i]][solution[i+1]];
        }
//        cost += distances[solution[numCities-1]][solution[0]];
        return cost;
    }
    
    /**
     * Generates a list of neighboring solutions to the given solution by randomly swapping two cities in the solution.
     * @param solution the solution for which to generate neighbors
     * @return a list of neighboring solutions
     */
    private List<int[]> generateNeighbors(int[] solution) {
        List<int[]> neighbors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int[] neighbor = Arrays.copyOf(solution, solution.length);
            int index1 = random.nextInt(numCities);
            int index2 = random.nextInt(numCities);
            while (index1 == index2) {
                index2 = random.nextInt(numCities);
            }
            int temp = neighbor[index1];
            neighbor[index1] = neighbor[index2];
            neighbor[index2] = temp;
            neighbors.add(neighbor);
        }
        return neighbors;
    }
}
