package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    static Maze maze;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        while (true) {
            choice = menu();
            switch (choice) {
                case 1:
                    int size = askSize();
                    if (size == 0) {
                        break;
                    }
                    maze = new Maze(size, size);
                    maze.generateMaze();
                    maze.print();
                    break;
                case 2:
                    String string = loadMaze();
                    if (string == null) {
                        break;
                    }
                    try {
                        maze = new Maze(string);
                    } catch (NumberFormatException e) {
                        System.out.println("Cannot load the maze. It has an invalid format");
                    }
                    break;
                case 3:
                    saveMaze(maze.toString());
                    break;
                case 4:
                    maze.print();
                    break;
                case 5:
                    maze.escapeRoute();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect option. Please try again");
            }
        }
    }

    private static void saveMaze(String mazeData) {
        String fileName = scanner.nextLine();
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(new String(Base64.getEncoder().encode(mazeData.getBytes())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String loadMaze() {
        String fileName = scanner.nextLine();
        try (Scanner fileReader = new Scanner(new File(fileName))) {
            return new String(Base64.getDecoder().decode(fileReader.nextLine().getBytes()));
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist", fileName);
            System.out.println();
        }
        return null;
    }

    private static int askSize() {
        int size;
        do {
            System.out.println("Please, enter the size of a maze");
            try {
                size = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                size = -1;
            }
            if (size == 0) {
                return 0;
            } else if (size < 3) {
                System.out.println("It should be an integer bigger than 2");
            } else {
                return size;
            }
        } while (true);
    }

    public static int menu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Generate a new maze");
        System.out.println("2. Load a maze");
        if (maze != null) {
            System.out.println("3. Save the maze");
            System.out.println("4. Display the maze");
            System.out.println("5. Find the escape");
        }
        System.out.println("0. Exit");
        int choice = Integer.parseInt(scanner.nextLine());
        if (maze == null && choice > 2 && choice < 6) {
            return -1;
        }
        return choice;
    }

}
