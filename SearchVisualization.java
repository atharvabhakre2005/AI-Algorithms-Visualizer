import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SearchVisualization extends JPanel {
    private final Map<String, Point> nodePositions;
    private final Map<String, List<String>> graph;
    private final List<String> visitedNodes = new ArrayList<>();

    public SearchVisualization(Map<String, Point> positions, Map<String, List<String>> graph) {
        this.nodePositions = positions;
        this.graph = graph;
    }

    public void search(String algorithm, String start, String goal) {
        visitedNodes.clear();

        if (algorithm.equalsIgnoreCase("BFS")) bfs(start, goal);
        else if (algorithm.equalsIgnoreCase("DFS")) dfs(start, goal, new HashSet<>());
        else if (algorithm.equalsIgnoreCase("A*")) aStarSearch(start, goal);
        else JOptionPane.showMessageDialog(null, "Invalid Algorithm!");

        repaint();
    }

    private void bfs(String start, String goal) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            visitedNodes.add(node);
            repaint();
            sleep();

            if (node.equals(goal)) return;

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    private void dfs(String node, String goal, Set<String> visited) {
        visited.add(node);
        visitedNodes.add(node);
        repaint();
        sleep();

        if (node.equals(goal)) return;

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, goal, visited);
            }
        }
    }

    private void aStarSearch(String start, String goal) {
        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> heuristic(node, goal)));
        Set<String> visited = new HashSet<>();
        openSet.add(start);

        while (!openSet.isEmpty()) {
            String node = openSet.poll();
            visitedNodes.add(node);
            repaint();
            sleep();

            if (node.equals(goal)) return;

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    openSet.add(neighbor);
                }
            }
        }
    }

    private int heuristic(String node, String goal) {
        Point p1 = nodePositions.get(node);
        Point p2 = nodePositions.get(goal);
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private void sleep() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        for (String node : graph.keySet()) {
            for (String neighbor : graph.get(node)) {
                Point p1 = nodePositions.get(node);
                Point p2 = nodePositions.get(neighbor);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        for (String node : nodePositions.keySet()) {
            Point p = nodePositions.get(node);
            g.setColor(visitedNodes.contains(node) ? Color.GREEN : Color.GRAY);
            g.fillOval(p.x - 15, p.y - 15, 30, 30);
            g.setColor(Color.BLACK);
            g.drawString(node, p.x - 5, p.y + 5);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Search Algorithm Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        Map<String, Point> positions = new HashMap<>();
        positions.put("A", new Point(100, 100));
        positions.put("B", new Point(200, 200));
        positions.put("C", new Point(300, 100));
        positions.put("D", new Point(400, 200));
        positions.put("E", new Point(250, 300));

        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", new ArrayList<>(Arrays.asList("B", "C")));
        graph.put("B", new ArrayList<>(Arrays.asList("D", "E")));
        graph.put("C", new ArrayList<>(Arrays.asList("D")));
        graph.put("D", new ArrayList<>(Arrays.asList("E")));

        SearchVisualization panel = new SearchVisualization(positions, graph);
        frame.add(panel);
        frame.setVisible(true);

        String algorithm = JOptionPane.showInputDialog("Enter Algorithm: BFS, DFS, A*");
        panel.search(algorithm, "A", "E");
    }
}
