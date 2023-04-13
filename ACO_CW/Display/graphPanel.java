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
    private int maxWidth, maxHeight;
    private double scaleX, scaleY;

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
    private void setScale () {
        for (Node node : cityNodes) {
            if (node.getX() > maxWidth) {
                maxWidth = node.getX();
            }
            if (node.getY() > maxHeight) {
                maxHeight = node.getY();
            }
        }
        scaleX = ((double) maxWidth) / ((double)WIDTH- OFFSET);
        scaleY = ((double) maxHeight) / ((double)HEIGHT- OFFSET);
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
            drawChromosome(graphics);
            drawCity(graphics);
        }
        //draw line
        private void drawChromosome(Graphics2D graphics) {

            graphics.setColor(Color.darkGray);
            Node[] array = cityNodes;

            for (int i = 1; i < array.length; i++) {
                int x1 = (int)(array[i-1].getX() / scaleX + OFFSET / 2);
                int y1 = (int)(array[i-1].getY() / scaleY + OFFSET / 2);
                int x2 = (int)(array[i].getX() / scaleX + OFFSET / 2);
                int y2 = (int)(array[i].getY() / scaleY + OFFSET / 2);
                graphics.drawLine(x1, y1, x2, y2);
            }
        }

        private void drawCity(Graphics2D graphics) {
            graphics.setColor(Color.darkGray);
            for (Node node : cityNodes) {
                int x = (int)((node.getX()) / scaleX - NODE_SIZE /2 + OFFSET / 2);
                int y = (int)((node.getY()) / scaleY - NODE_SIZE /2 + OFFSET / 2);
                graphics.fillOval(x, y, NODE_SIZE, NODE_SIZE);
            }
        }
    }

}
