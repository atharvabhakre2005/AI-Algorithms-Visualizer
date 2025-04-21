package main;

import ui.GraphPanel;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Search Algorithm Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLayout(new BorderLayout());

        // Node Positions
        Map<String, Point> positions = new HashMap<>();
        positions.put("A", new Point(100, 100));
        positions.put("B", new Point(250, 100));
        positions.put("C", new Point(400, 100));
        positions.put("D", new Point(550, 100));
        positions.put("E", new Point(700, 100));
        positions.put("F", new Point(850, 100));
        positions.put("G", new Point(100, 250));
        positions.put("H", new Point(250, 250));
        positions.put("I", new Point(400, 250));
        positions.put("J", new Point(550, 250));
        positions.put("K", new Point(700, 250));
        positions.put("L", new Point(850, 250));
        positions.put("M", new Point(100, 400));
        positions.put("N", new Point(250, 400));
        positions.put("O", new Point(400, 400));
        positions.put("P", new Point(550, 400));
        positions.put("Q", new Point(700, 400));
        positions.put("R", new Point(850, 400));
        positions.put("S", new Point(100, 550));
        positions.put("T", new Point(250, 550));
        positions.put("U", new Point(400, 550));
        positions.put("V", new Point(550, 550));
        positions.put("W", new Point(700, 550));
        positions.put("X", new Point(850, 550));

        // Graph Structure
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "G"));
        graph.put("B", Arrays.asList("A", "C", "H"));
        graph.put("C", Arrays.asList("B", "D", "I"));
        graph.put("D", Arrays.asList("C", "E", "J"));
        graph.put("E", Arrays.asList("D", "F", "K"));
        graph.put("F", Arrays.asList("E", "L"));
        graph.put("G", Arrays.asList("A", "H", "M"));
        graph.put("H", Arrays.asList("B", "G", "I", "N"));
        graph.put("I", Arrays.asList("C", "H", "J", "O"));
        graph.put("J", Arrays.asList("D", "I", "K", "P"));
        graph.put("K", Arrays.asList("E", "J", "L", "Q"));
        graph.put("L", Arrays.asList("F", "K", "R"));
        graph.put("M", Arrays.asList("G", "N", "S"));
        graph.put("N", Arrays.asList("H", "M", "O", "T"));
        graph.put("O", Arrays.asList("I", "N", "P", "U"));
        graph.put("P", Arrays.asList("J", "O", "Q", "V"));
        graph.put("Q", Arrays.asList("K", "P", "R", "W"));
        graph.put("R", Arrays.asList("L", "Q", "X"));
        graph.put("S", Arrays.asList("M", "T"));
        graph.put("T", Arrays.asList("N", "S", "U"));
        graph.put("U", Arrays.asList("O", "T", "V"));
        graph.put("V", Arrays.asList("P", "U", "W"));
        graph.put("W", Arrays.asList("Q", "V", "X"));
        graph.put("X", Arrays.asList("R", "W"));

        // Heuristic Values
        Map<String, Integer> heuristics = new HashMap<>();
        heuristics.put("A", 12); heuristics.put("B", 11); heuristics.put("C", 10);
        heuristics.put("D", 9); heuristics.put("E", 8); heuristics.put("F", 7);
        heuristics.put("G", 11); heuristics.put("H", 10); heuristics.put("I", 9);
        heuristics.put("J", 8); heuristics.put("K", 7); heuristics.put("L", 6);
        heuristics.put("M", 10); heuristics.put("N", 9); heuristics.put("O", 8);
        heuristics.put("P", 7); heuristics.put("Q", 6); heuristics.put("R", 5);
        heuristics.put("S", 9); heuristics.put("T", 8); heuristics.put("U", 7);
        heuristics.put("V", 6); heuristics.put("W", 5); heuristics.put("X", 0);

        // GraphPanel Setup
        GraphPanel panel = new GraphPanel(positions, graph, heuristics);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // 🔁 Loop for multiple visualizations
        while (true) {
            String[] algorithms = {"BFS", "DFS", "A*", "AO*", "DLS", "IDDFS", "Exit"};
            String algorithm = (String) JOptionPane.showInputDialog(
                frame,
                "Choose an algorithm to visualize:",
                "Algorithm Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                algorithms,
                algorithms[0]
            );

            if (algorithm == null || algorithm.equalsIgnoreCase("Exit")) {
                JOptionPane.showMessageDialog(frame, "Exiting Visualizer. Goodbye!");
                System.exit(0);
            }

            // 🌟 Ask for Start and Goal nodes
            String start = JOptionPane.showInputDialog(frame, "Enter START node (e.g., A):");
            if (start == null || !graph.containsKey(start)) {
                JOptionPane.showMessageDialog(frame, "Invalid start node!");
                continue;
            }

            String goal = JOptionPane.showInputDialog(frame, "Enter GOAL node (e.g., X):");
            if (goal == null || !graph.containsKey(goal)) {
                JOptionPane.showMessageDialog(frame, "Invalid goal node!");
                continue;
            }

            panel.clearGraph();
            panel.search(algorithm, start, goal);
        }
    }
}
