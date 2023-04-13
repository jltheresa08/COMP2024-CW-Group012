package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Vertex extends Node implements Iterable<Edge> {

    private HashMap<Integer, Edge> hashMap;
    private ArrayList<Edge> list;

    /**
     * vertex constructor
     * @param name      The name of the vertex
     * @param x         The x coordinate of the vertex
     * @param y         The y coordinate of the vertex
     */
    public Vertex (String name, int x, int y) {
        super(name, x, y);
        hashMap = new HashMap<>();
        list = new ArrayList<>();
    }

    /**
     * Add a Node to the hashMap. If the Node is as Vertex, creates an Edge equivalent,
     * then adds the Edge to the hashMap, otherwise adds it in directly.
     * @param n         The Node that will be added to the hashMap
     */
    public void addEdge (Node n) {
        if (n instanceof Vertex) {
            Edge e = new Edge(n.getName(), n.getX(), n.getY());
            hashMap.put(e.hashCode(), e);
            list.add(e);
        }
        else {
            hashMap.put(n.hashCode(), (Edge)n);
            list.add((Edge)n);
        }
    }

    public Edge getEdge (Node n) {
        return hashMap.get(n.hashCode());
    }

    @Override
    public Iterator<Edge> iterator () {
        return list.iterator();
    }


}

