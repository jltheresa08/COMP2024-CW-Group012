import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {

	    String filename = "TSP_107.txt";
	    String outputFilename = "output.txt";
	    int[][] distances = readDistancesFromFile(filename);

	    // Create a TSPSolver object and solve the TSP problem
	    TSPSolver solver = new TSPSolver(distances);
	    int max = 100000;
	    int[] solution = solver.solve(max);

//	    // Write solution to output file
//	    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
//	    writer.write("Order of traversal:\n");
//	    for (int i = 0; i < solution.length; i++) {
//	        writer.write(solution[i] + " ");
//	    }
//	    writer.write("\n\nTotal cost of travel:\n\n");
//	    writer.close();

	    // Reorder the items in the input file
	    int[] order = solution;
	    String[] lines = Files.readAllLines(Paths.get(filename)).toArray(new String[0]);
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < order.length; i++) {
	        sb.append(lines[order[i]]);
	        sb.append("\n");
	    }
	    Files.write(Paths.get(outputFilename), sb.toString().getBytes());
//	    // Print the order of traversal and the total cost of travelling
//	    System.out.println("Order of traversal: " + Arrays.toString(solution));
//	    System.out.println("Total cost of travelling: " + solver.getTotalDistance());
	}

	/**
	 * Reads the coordinates of the cities from a file and computes the Euclidean distances between them.
	 * 
	 * @param filename the name of the file containing the city coordinates
	 * @return a two-dimensional array of integers representing the distances between the cities
	 * @throws IOException if there is an error reading the file
	 */
	private static int[][] readDistancesFromFile(String filename) throws IOException {
	    int[][] distances = new int[107][107];
	    File file = new File(filename);
	    BufferedReader br = new BufferedReader(new FileReader(file));

	    // Read the coordinates of the cities
	    int[][] coords = new int[107][2];
	    String line;
	    int i = 0;
	    while ((line = br.readLine()) != null) {
	        String[] values = line.trim().split("\\s+");
	        int node = Integer.parseInt(values[0]);
	        int x = Integer.parseInt(values[1]);
	        int y = Integer.parseInt(values[2]);
	        coords[node-1][0] = x;
	        coords[node-1][1] = y;
	        i++;
	    }


	    // Compute the distances between the cities
	    for (int j = 0; j < 107; j++) {
	        for (int k = 0; k < 107; k++) {
	            double dx = coords[j][0] - coords[k][0];
	            double dy = coords[j][1] - coords[k][1];
	            distances[j][k] = (int) Math.round(Math.sqrt(dx*dx + dy*dy));
	        }
	    }

	    br.close();
	    return distances;
	}


}

