package com.aim.sa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimulatedAnnealing {
    private static final double INITIAL_TEMPERATURE = 1000;
    private static final double FINAL_TEMPERATURE = 0;
    private static final double COOLING_FACTOR = 0.7;
    private static final int MAX_ITERATIONS = 60000;

    private static List<City> cities;
    private static int numCities;
    
    public static void main(String[] args) throws IOException{

    	//Get start time
    	long startTime = System.currentTimeMillis();
        readInputFile("TSP_107.txt");

        //Create initial tour and swap cities
        Tour currentTour = new Tour(cities);
        int iteration = 5;
        for(int a=0;a<iteration;a++) {
        	int city1 = (int) (Math.random() * numCities);
            int city2 = (int) (Math.random() * numCities);
            currentTour.swapCities(city1, city2);
       }
        
        Tour bestTour = new Tour(currentTour);        
        double temp = INITIAL_TEMPERATURE;

        //Run simulated annealing algorithm
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            Tour newTour = new Tour(currentTour);
            
            //Swap cities in new tour
            iteration = 5000;
            for(int a=0;a<iteration;a++) {
            	int  city1 = (int) (Math.random() * numCities);
                int  city2 = (int) (Math.random() * numCities);
                newTour.swapCities(city1, city2);
            }
            
            //Calculate energy
            double currentEnergy = currentTour.getTourDistance();
            double newEnergy = newTour.getTourDistance();

            //Determine if the tour is accepted
            if (temp > FINAL_TEMPERATURE) 
            {
	            if (acceptNewSolution(currentEnergy, newEnergy, temp)) {
	                currentTour = new Tour(newTour);
	            }
	            bestTour = new Tour(currentTour);
	            temp *= COOLING_FACTOR;
            }
            else
            {
            	currentTour = new Tour(newTour);
            	if (currentTour.getTourDistance() < bestTour.getTourDistance()) {
                    bestTour = new Tour(currentTour);
                }
            }
            
        }
        
	    //Get end time
	    long endTime = System.currentTimeMillis();
	    
	    //Calculate the running time
	    long runningTime = endTime - startTime;

    	System.out.println("Running time: " + runningTime + " milliseconds");
        System.out.println("Best tour cost: " + bestTour.getTourDistance());
        System.out.println("Best tour order: " + bestTour.toString());
        
        //Save order of traversal and best cost of tour into output.txt
        writeOutputFile("TSP_107.txt", "output.txt", bestTour);
                
        // Append total cost of tour to last line of output.txt
        FileWriter fr = new FileWriter("output.txt", true);
        fr.write("Best:" + bestTour.getTourDistance());
        fr.close();
    }
    
    /**
     * Write output file with the order of cities in the given tour
     * @param inputFile the input file to read the city coordinates from
     * @param outputFile the file to write the ordered city coordinates to
     * @param tour the tour containing the ordered cities
     */
    private static void writeOutputFile(String inputFile, String outputFile, Tour tour) {
    	//Copy lines from input file
        List<String> outputLines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("EOF")) {
                    break;
                }
                outputLines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        int[] order = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            order[i] = tour.getCities().get(i).getId();
        }
        
        //Sorting lines based on order of path
        List<String> orderedLines = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            for (String line : outputLines) {
                int id = Integer.parseInt(line.split(" ")[0]);
                if (id == order[i]) {
                    orderedLines.add(line);
                    break;
                }
            }
        }
        
        try {
            Files.write(Paths.get(outputFile), orderedLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read input file containing a list of cities with id, x and y coordinates
     * Create a list of City objects
     * @param filename the name of the input file to read
     */
    private static void readInputFile(String filename) {
        cities = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
            	
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                if (line.equals("EOF")) {
                    break;
                }
                
                int id = Integer.parseInt(tokens[0]);
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                City city = new City(id, x, y);
                cities.add(city);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        numCities = cities.size();
    }

    /**
     * Determine whether or not to accept a new solution based on the current energy, new energy and the temperature
     * @param currentEnergy current energy of the solution
     * @param newEnergy new energy of the solution
     * @param temperature current temperature of the system
     * @return true if new solution is accepted, false otherwise
     */
    private static boolean acceptNewSolution(double currentEnergy, double newEnergy, double temperature) {
        if (newEnergy < currentEnergy) {
            return true;
        }
        double acceptanceProbability = Math.exp((currentEnergy - newEnergy) / temperature);
        return Math.random() < acceptanceProbability;
    }
    
    /**
     * Represent city in 2d
     */
    private static class City {
        private int id;
        private int x;
        private int y;
      
        
        /**
         * Construct a new city with given ID and coordinates
         * @param id identifier for the city
         * @param x x-coordinate of the city
         * @param y y-coordinate of the city
         */
        public City(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public int getId() {
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        /**
         * Computes and returns the Euclidean distance between 2 cities
         * @param other the other city to compute the distance to
         * @return the Euclidean distance between 2 cities
         */
        public double distanceTo(City other) {
            int dx = x - other.getX();
            int dy = y - other.getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    /**
     * An ordered list of cities to visit
     */
    private static class Tour {
        private List<City> cities;
        
        /**
         * Construct a new tour from the given list of cities 
         * @param cities the list of cities to include in this tour
         */
        public Tour(List<City> cities) {
            this.cities = new ArrayList<>(cities);
        }
        
        /**
         * Construct a new tour that is a copy of the given tour
         * @param tour the tour to copy
         */
        public Tour(Tour tour) {
        	this.cities = new ArrayList<>(tour.cities);
        }

        /**
         * Return the list of cities in this tour
         * @return the list of cities in this tour
         */
        public List<City> getCities() {
            return cities;
        }

        /**
         * Return a string representation of this tour, showing the order in which the cities are visited
         * @return a string representation of this tour
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (City city : cities) {
                sb.append(city.getId()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()); // remove trailing comma and space
            sb.append("]");
            return sb.toString();
        }

        /**
         * Computes the total distance traveled in this tour
         * @return the total distance traveled
         */
        public double getTourDistance() {
            double distance = 0;
            for (int i = 0; i < numCities - 1; i++) {
                City city1 = cities.get(i);
                City city2 = cities.get(i + 1);
                distance += city1.distanceTo(city2);
            }
            City lastCity = cities.get(numCities - 1);
            City firstCity = cities.get(0);
            distance += lastCity.distanceTo(firstCity);
            return distance;
        }

        /**
         * Swap the positions of two cities in this tour
         * @param i index of the first city to swap
         * @param j index of the second city to swap
         */
        public void swapCities(int i, int j) {
            if (i != j) {
                City temp = cities.get(i);
                cities.set(i, cities.get(j));
                cities.set(j, temp);
            }
        }
    }
}