package maze;

import java.util.*;

public class Maze {
    final private int height;
    final private int width;
    final private int[][] maze;
    final private int matrixHeight;
    final private int matrixWidth;
    final private int matrixSize;
    final private ArrayList<Edge> edges = new ArrayList<>();
    final private ArrayList<Edge> result = new ArrayList<>();
    final private Random seed = new Random();

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.maze = new int[height][width];
        // Filled maze with walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 1;
            }
        }

        this.matrixHeight = height % 2 == 0 ? (this.height - 1) / 2 : this.height / 2;
        this.matrixWidth = width % 2 == 0 ? (this.width - 1) / 2 : this.width / 2;
        this.matrixSize = matrixHeight * matrixWidth;
    }

    public Maze(String string) {
        String[] lines = string.split("\n");

        this.height = lines.length;
        this.width = lines[0].split(" ").length;

        this.maze = new int[height][width];
        for (int i = 0; i < height; i++) {
            String[] signs = lines[i].split(" ");
            for (int j = 0; j < width; j++) {
                maze[i][j] = Integer.parseInt(signs[j]);
            }
        }
        this.matrixHeight = height % 2 == 0 ? (this.height - 1) / 2 : this.height / 2;
        this.matrixWidth = width % 2 == 0 ? (this.width - 1) / 2 : this.width / 2;
        this.matrixSize = matrixHeight * matrixWidth;
    }

    public void print() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(maze[i][j] == 1 ? "\u2588\u2588" : "  ");
            }
            System.out.println();
        }
    }

    public void generateMaze() {

        // Generating edges
        for (int i = 0; i < matrixSize; i++) {
            // Horizontal edges
            if ((i + 1) % matrixWidth != 0) {
                edges.add(new Edge(i, i + 1, seed.nextInt(49) + 1));
            }
            // Vertical edges
            if (i + matrixWidth < matrixSize) {
                edges.add(new Edge(i, i + matrixWidth, seed.nextInt(49) + 1));
            }
        }

        edges.sort(Edge::compareTo);

        // Just a tree (adjacency list)
        do {
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                if (result.size() == 0) {
                    result.add(edges.get(i));
                    edges.remove(i);
                    break;
                } else if (result.contains(edge)) {
                    if (edge.check(result)) {
                        result.add(edges.get(i));
                    }
                    edges.remove(i);
                    break;
                }
            }
        } while (result.size() + 1 < matrixSize);


        // Digging tunnels according to the Edges
        for (Edge edge : result) {
            int y1 = edge.point1 / matrixWidth * 2 + 1;
            int x1 = (edge.point1 % matrixWidth) * 2 + 1;
            int y2 = edge.point2 / matrixWidth * 2 + 1;
            int x2 = (edge.point2 % matrixWidth) * 2 + 1;
            int y = y1 == y2 ? y1 : (y1 + y2) / 2;
            int x = x1 == x2 ? x1 : (x1 + x2) / 2;
            maze[y][x] = 0;
            maze[y1][x1] = 0;
            maze[y2][x2] = 0;
        }

        // Creating entrance and exit
        int entrance = seed.nextInt(matrixHeight) * 2 + 1;
        maze[entrance][0] = 0;
        int exit = seed.nextInt(matrixHeight) * 2 + 1;
        maze[exit][width - 1] = 0;
        if (this.width % 2 == 0) {
            maze[exit][width - 2] = 0;
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < height; i++) {
            string.append(maze[i][0]);
            for (int j = 1; j < width; j++) {
                string.append(" ");
                string.append(maze[i][j]);
            }
            string.append("\n");
        }
        return string.toString();
    }
}
