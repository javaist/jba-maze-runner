package maze;

import java.util.ArrayList;
import java.util.Objects;

public class DFS {
    final static private ArrayList<Node> path = new ArrayList<>();

    static public ArrayList<Node> getPath(Node start, Node end) {
        path.clear();
        doSearch(start, end);
        path.add(start);
        return path;
    }

    static private boolean doSearch(Node start, Node end) {
        if (Objects.equals(start, end)) {
            return true;
        }
        if (start.visited) {
            return false;
        }

        start.visited = true;

        for (Node neighbour : start.getConnected()) {
            if (!neighbour.visited) {
                if (doSearch(neighbour, end)) {
                    path.add(neighbour);
                    return true;
                }
            }
        }
        return false;
    }
}
