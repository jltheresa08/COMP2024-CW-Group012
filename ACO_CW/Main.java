import Ants.TSP;

public class Main {

    public static void main(String[] args) {
        System.out.println("........................ANT COLONY OPTIMIZATION..........................");
        System.out.println("number of Ants:            100");
        System.out.println("number of runs:            100");
        System.out.println("Evaporation Rate:          0.1");
        System.out.println("Alpha (pheromone impact):  1");
        System.out.println("Beta (distance impact):    5");

        int ants    = 100;
        int gen     = 100;
        double evap = 0.1;
        int alpha   = 1;
        int beta    = 5;

        TSP TSP = new TSP(ants, gen, evap, alpha, beta);
        TSP.run();

        System.out.println("...........................FINISH..........................");
    }
}
