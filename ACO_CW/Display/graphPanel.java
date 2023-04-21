package Display;

import Graph.*;

import javax.swing.*;
import java.awt.*;

/**
 * In charge of the display of the graph using swing
 */
public class graphPanel extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000 / 16*9;
    private static final int OFFSET = 40;
    private static final int NODE_SIZE = 5;

    private Panel panel;
    private Node[] cityNodes;
    private double xScale, yScale;

    /**
     * Construct graph panel and nodes
     * @param cityNodes    nodes representing city
     */
    public graphPanel(Node[] cityNodes) {
        this.cityNodes = cityNodes;
        setScale();
        panel = createPanel();
        setPanelConfigurations();
    }

    /**
     * connect the city by constructing a path
     * @param chromosome    candidate solution/path to the problem represented as an array of nodes
     */
    public void plot(Node[] chromosome) {
        this.cityNodes = chromosome;
        panel.repaint();
    }

    /**
    * construct general panel
    * @return panel
    */
    private Panel createPanel () {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return panel;
    }

    private void setPanelConfigurations() {
        int panelWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
        int panelHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
        int x = panelWidth - (WIDTH / 2);
        int y = panelHeight - (HEIGHT / 2);
        setLocation(x, y);
        setResizable(false);
        pack();
        setTitle("ACO for TSP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Sets the scale for display panels
     */
    private void setScale() {
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Node node : cityNodes) {
            maxX = Math.max(maxX, node.getX());
            maxY = Math.max(maxY, node.getY());
        }

        xScale = maxX / (WIDTH - OFFSET);
        yScale = maxY / (HEIGHT - OFFSET);
    }
    /**
     * graphic work below
     */
    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            drawTSP((Graphics2D)graphics);
        }

        private void drawTSP(Graphics2D graphics) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawSolution(graphics);
        }

        /**
         * handle the drawing of city and path
         * @param graphics display the path and city
         */
        private void drawSolution(Graphics2D graphics) {

            // Draw the TSP solution path
            graphics.setColor(Color.darkGray);
            int[] pathX = new int[cityNodes.length];
            int[] pathY = new int[cityNodes.length];
            for (int i = 0; i < cityNodes.length; i++) {
                pathX[i] = (int)(cityNodes[i].getX() / xScale + OFFSET / 2);
                pathY[i] = (int)(cityNodes[i].getY() / yScale + OFFSET / 2);
            }
            graphics.drawPolyline(pathX, pathY, cityNodes.length);

            // Draw the city nodes
            graphics.setColor(Color.red);
            for (Node node : cityNodes) {
                int x = (int)(node.getX() / xScale + OFFSET / 2);
                int y = (int)(node.getY() / yScale + OFFSET / 2);
                graphics.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            }

        }
    }

}
