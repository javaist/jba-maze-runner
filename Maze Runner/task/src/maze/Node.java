package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Node {
    int x;
    int y;
    int name;
    boolean visited = false;
    List<Node> neighbours;

    public Node(int name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.neighbours = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Point: %d (%d, %d)", this.name, this.x, this.y);
    }

    public void addNode(Node neighbour) {
        this.neighbours.add(neighbour);
    }

    public List<Node> getConnected() {
        return this.neighbours;
    }
}
