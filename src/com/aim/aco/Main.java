package com.aim.aco;
import com.aim.aco.Ants.TSP;

public class Main {

    public static void main(String[] args) {
        final long startTime = System.nanoTime();
        System.out.println("........................ANT COLONY OPTIMIZATION..........................");
        System.out.println("number of Ants:            450");
        System.out.println("Evaporation Rate:          0.1");
        System.out.println("Alpha (pheromone impact):  1");
        System.out.println("Beta (distance impact):    5");

        int ants    = 450;
        double evapRate = 0.1;
        int alpha   = 1;
        int beta    = 5;

        TSP TSP = new TSP(ants, evapRate, alpha, beta);
        TSP.run();
        final long duration = System.nanoTime() - startTime;
        System.out.println(duration + " nanoseconds");
        System.out.println("...........................FINISH..........................");
    }
}
