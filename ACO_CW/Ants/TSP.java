package Ants;

import Display.*;
import Graph.*;

public class TSP {

    private Graph graph;
    private int numAnts;

    /**
     * constructor for tsp
     * @param numAnts         number of ants to run per generation
     * @param evapRate   frequency of evaporation
     * @param alpha         power of pheromone over probability
     * @param beta          power of distance  over probability
     */
    public TSP(int numAnts, double evapRate, int alpha, int beta) {
        this.numAnts = numAnts;
        graph = Input.Import.convertGraph(evapRate, alpha, beta);
    }

    /**
     * Run the Ant.java in display.
     */
    public void run () {
        graphPanel graphPanel = new graphPanel(graph.getVertices());

        Ant currentBestAnt = null;
        int curentBestEval = 0;
        //wait time for panel to load
        delay(1000);

        Ant[] ants = generateAnts(numAnts);
        Ant ant = travel(ants);
        updatePheromones(ants);

        if (currentBestAnt == null) {
            currentBestAnt = ant;
            curentBestEval = ant.sumDistance();
        } else if (ant.sumDistance() < curentBestEval) {
            currentBestAnt = ant;
            curentBestEval = ant.sumDistance();
        }
        graphPanel.plot(currentBestAnt.getTour());

        System.out.print("Best Tour: ");
        System.out.println(currentBestAnt);
        System.out.println("Evaluation: " + curentBestEval);
    }

    /**
     * Generate ants and place them at random starting points
     * @param numAnts  the number of ants
     * @return          an array of the ants
     */
    private Ant[] generateAnts(int numAnts) {
        Ant[] ants = new Ant[numAnts];
        for (int i = 0; i < numAnts; i++) {
            ants[i] = new Ant(graph);
        }
        return ants;
    }

    /**
     * Allow every single ants to start travelling
     * @param ants      ants that have permission to travel(not finished tour)
     * @return          ant with best result/the shortest route
     */
    private Ant travel (Ant[] ants) {

        Ant currentBestAnt = null;
        int currentBestResult = 0;

        for (Ant ant : ants) {
            while (ant.notEndJourney()) {
                ant.travel();
            }

            if (currentBestAnt == null) {
                currentBestAnt = ant;
                currentBestResult = ant.sumDistance();
            } else if (ant.sumDistance() < currentBestResult) {
                currentBestAnt = ant;
                currentBestResult = ant.sumDistance();
            }
        }

        return currentBestAnt;
    }

    /**
     * update the pheromones based on ants that completed the tour
     * @param ants  the ants that completed tour
     */
    private void updatePheromones (Ant[] ants) {
        for (Ant ant : ants) {
            graph.applyPheromone(ant);
        }
    }

    /**
     * used to buy time for panel to generate
     * @param ms    milliseconds
     */
    private static void delay (int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
