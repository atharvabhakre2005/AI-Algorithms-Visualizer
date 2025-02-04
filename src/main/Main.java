package main;

import ui.GraphPanel;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Search Algorithm Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);  // Increased frame size for larger graph


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

        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "G", "H"));
        graph.put("B", Arrays.asList("A", "C", "D", "G"));
        graph.put("C", Arrays.asList("B", "D", "E", "H"));
        graph.put("D", Arrays.asList("C", "B", "E", "F"));
        graph.put("E", Arrays.asList("D", "C", "F"));
        graph.put("F", Arrays.asList("E", "D", "C", "I"));
        graph.put("G", Arrays.asList("A", "B", "H", "M"));
        graph.put("H", Arrays.asList("A", "B", "C", "G", "I"));
        graph.put("I", Arrays.asList("H", "F", "J"));
        graph.put("J", Arrays.asList("I", "K"));
        graph.put("K", Arrays.asList("J", "L"));
        graph.put("L", Arrays.asList("K", "P"));
        graph.put("M", Arrays.asList("G", "N", "O"));
        graph.put("N", Arrays.asList("M", "O", "P"));
        graph.put("O", Arrays.asList("M", "N", "Q"));
        graph.put("P", Arrays.asList("L", "N", "Q"));
        graph.put("Q", Arrays.asList("P", "O", "R"));
        graph.put("R", Arrays.asList("Q", "S"));
        graph.put("S", Arrays.asList("R", "T"));
        graph.put("T", Arrays.asList("S", "U"));
        graph.put("U", Arrays.asList("T", "V"));
        graph.put("V", Arrays.asList("U", "W"));
        graph.put("W", Arrays.asList("V", "X"));
        graph.put("X", Arrays.asList("W", "V"));

        GraphPanel panel = new GraphPanel(positions, graph);
        frame.add(panel);
        frame.setVisible(true);

        String algorithm = JOptionPane.showInputDialog("Enter Algorithm: BFS, DFS, A*, MINMAX, ALPHABETA, AO*, DLS, IDDFS");
        panel.search(algorithm, "A", "X");  // You can change start and goal nodes
    }
}
