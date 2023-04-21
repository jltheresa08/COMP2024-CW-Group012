package Input;

import Graph.*;

import java.io.InputStream;

/**
 * to read the data in txt files for graph
 */
public class   Import {

    /**
     * convert txt files to graph
     * @return     graph
     */
    public static Graph convertGraph(double evapRate, int alpha, int beta) {

        String dataSetName;
        dataSetName = "TSP_107.txt";
        String[] lines = read(dataSetName).split("\n");
        int numCity = 107;

        Vertex[] totalVertex = new Vertex[numCity];

        // Read each line and turn it into a Vertex.
        for (int i = 0; i < numCity; i++) {
            String[] line = lines[i].trim().split(" ");
            int x = (int)Double.parseDouble(line[1].trim());
            int y = (int)Double.parseDouble(line[2].trim());
            Vertex currentVertex = new Vertex(line[0], x, y);
            totalVertex[i] = currentVertex;
        }

        Graph graph = new Graph(evapRate, alpha, beta);

        // add vertex
        for (int i = 0; i < numCity; i++) {
            graph.addVertex(totalVertex[i]);
        }

        // add edge for every vertex
        for (Vertex v : graph) {
            for (int i = 0; i < numCity; i++) {
                if (totalVertex[i] != v) {
                    graph.addEdge(v, totalVertex[i]);
                }
            }
        }

        return graph;
    }

    /**
     * turn the txt file into string format
     * @param fileName  name of the file
     * @return          contents of file as string
     */
    private static String read (String fileName) {
        InputStream stream = Import.class.getResourceAsStream(fileName);
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
