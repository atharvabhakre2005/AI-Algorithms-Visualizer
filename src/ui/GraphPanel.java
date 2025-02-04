package ui;

import algorithms.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.awt.Point;
import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {
    private final Map<String, Point> nodePositions;
    private final Map<String, List<String>> graph;
    private final List<String> visitedNodes = new ArrayList<>();
    private final List<String[]> visitedEdges = new ArrayList<>();

    public GraphPanel(Map<String, Point> positions, Map<String, List<String>> graph) {
        this.nodePositions = positions;
        this.graph = graph;
    }

    public void search(String algorithm, String start, String goal) {
        visitedNodes.clear();
        visitedEdges.clear();

        switch (algorithm.toUpperCase()) {
            case "BFS" -> BFS.run(graph, start, goal, visitedNodes, this);
            case "DFS" -> DFS.run(graph, start, goal, visitedNodes, this);
            case "A*" -> AStar.run(graph, nodePositions, start, goal, visitedNodes, this);
            case "MINMAX" -> MinMax.run(graph, start, goal, visitedNodes, this);
            case "ALPHABETA" -> AlphaBetaPruning.run(graph, start, goal, visitedNodes, this);
            case "AO*" -> AOStar.run(graph, nodePositions, start, goal, visitedNodes, this);
            case "DLS" -> DLS.run(graph, start, goal, visitedNodes, this, 3);
            case "IDDFS" -> IDDFS.run(graph, start, goal, visitedNodes, this);
            default -> JOptionPane.showMessageDialog(null, "Invalid Algorithm!");
        }

        repaint();
    }

    public void markEdgeVisited(String from, String to) {
        visitedEdges.add(new String[]{from, to});
        repaint();
    }

    public void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("AI Algorithms Visualizer", getWidth() / 2 - 120, 30);

        g2.setStroke(new BasicStroke(2));

        for (String node : graph.keySet()) {
            for (String neighbor : graph.get(node)) {
                Point p1 = nodePositions.get(node);
                Point p2 = nodePositions.get(neighbor);

                g2.setColor(visitedEdges.contains(new String[]{node, neighbor}) ? Color.RED : Color.LIGHT_GRAY);
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        for (int i = 0; i < nodePositions.keySet().size(); i++) {
            String node = (String) nodePositions.keySet().toArray()[i];
            Point p = nodePositions.get(node);
            
            // Color nodes based on visit status
            g2.setColor(visitedNodes.contains(node) ? Color.GREEN : Color.GRAY);
            g2.fillOval(p.x - 20, p.y - 20, 40, 40);

            // Draw node name
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(node, p.x - 7, p.y + 5);

            // Draw visit order number
            if (visitedNodes.contains(node)) {
                int visitOrder = visitedNodes.indexOf(node) + 1;
                g2.setColor(Color.RED);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString(String.valueOf(visitOrder), p.x - 20, p.y - 25);
            }
        }
    }
}