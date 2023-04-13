package Graph;

/**
 * This abstract class is the parent of both Vertex and Edge
 */
public abstract class Node {

    String name;
    int x, y;

    /**
     * node constructor
     * @param name      The name of the node.
     * @param x         The x coordinate of the node
     * @param y         The y coordinate of the node
     */
    public Node (String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName () {
        return name;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return y == node.y && x == node.x;
    }

    @Override
    public int hashCode () {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
