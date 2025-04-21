package ui;

import algorithms.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class GraphPanel extends JPanel {
    private final Map<String, Point> nodePositions;
    private final Map<String, List<String>> graph;
    private final Map<String, Integer> heuristics;
    private final List<String> visitedNodes = new ArrayList<>();
    private final Set<String> visitedEdges = new HashSet<>();
    private final Set<String> pathEdges = new HashSet<>();

    public GraphPanel(Map<String, Point> positions, Map<String, List<String>> graph, Map<String, Integer> heuristics) {
        this.nodePositions = positions;
        this.graph = graph;
        this.heuristics = heuristics;
    }

    public void search(String algorithm, String start, String goal) {
        clearGraph();
        
        switch (algorithm.toUpperCase()) {
            case "BFS" -> BFS.run(graph, start, goal, visitedNodes, this);
            case "DFS" -> DFS.run(graph, start, goal, visitedNodes, this);
            case "A*" -> AStar.run(graph, nodePositions, heuristics, start, goal, visitedNodes, this);
            case "AO*" -> AOStar.run(graph, nodePositions, heuristics, start, goal, visitedNodes, this);
            case "DLS" -> DLS.run(graph, start, goal, visitedNodes, this, 2);
            case "IDDFS" -> IDDFS.run(graph, start, goal, visitedNodes, this);
            default -> JOptionPane.showMessageDialog(null, "Invalid Algorithm!");
        }

        markNodeVisited(goal);  
        repaint();
    }

    public void markNodeVisited(String node) {
        if (!visitedNodes.contains(node)) {
            visitedNodes.add(node);
            repaint();
        }
    }

    public void clearGraph() {
        visitedNodes.clear();
        visitedEdges.clear();
        pathEdges.clear();
        repaint();
    }

    public void markEdgeVisited(String from, String to) {
        String edge = from.compareTo(to) < 0 ? from + "-" + to : to + "-" + from;
        visitedEdges.add(edge);
        repaint();
    }

    public void markEdgePath(String from, String to) {
        String edge = from.compareTo(to) < 0 ? from + "-" + to : to + "-" + from;
        pathEdges.add(edge);
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

        GradientPaint gradient = new GradientPaint(0, 0, new Color(60, 63, 65), getWidth(), getHeight(), new Color(25, 25, 112));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Point p : nodePositions.values()) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
            maxX = Math.max(maxX, p.x);
            maxY = Math.max(maxY, p.y);
        }

        int graphWidth = maxX - minX;
        int graphHeight = maxY - minY;

        int xOffset = (getWidth() - graphWidth) / 2 - minX;
        int yOffset = (getHeight() - graphHeight) / 2 - minY;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 26));
        g2.drawString("AI Algorithms Visualizer", getWidth() / 2 - 130, 40);

        g2.setStroke(new BasicStroke(3));

        for (String node : graph.keySet()) {
            for (String neighbor : graph.get(node)) {
                Point p1 = new Point(nodePositions.get(node).x + xOffset, nodePositions.get(node).y + yOffset);
                Point p2 = new Point(nodePositions.get(neighbor).x + xOffset, nodePositions.get(neighbor).y + yOffset);

                String edge = node.compareTo(neighbor) < 0 ? node + "-" + neighbor : neighbor + "-" + node;

                if (pathEdges.contains(edge)) {
                    g2.setColor(Color.YELLOW); // Shortest path
                } else if (visitedEdges.contains(edge)) {
                    g2.setColor(Color.RED); // Explored edges
                } else {
                    g2.setColor(new Color(180, 180, 180, 150)); // Default
                }

                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        for (String node : nodePositions.keySet()) {
            Point p = new Point(nodePositions.get(node).x + xOffset, nodePositions.get(node).y + yOffset);

            g2.setColor(visitedNodes.contains(node) ? new Color(50, 205, 50) : new Color(100, 100, 100));
            g2.fillOval(p.x - 20, p.y - 20, 40, 40);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(node, p.x - 7, p.y + 5);

            if (visitedNodes.contains(node)) {
                int visitOrder = visitedNodes.indexOf(node) + 1;
                g2.setColor(Color.RED);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString(String.valueOf(visitOrder), p.x - 20, p.y - 25);
            }

            if (heuristics.containsKey(node)) {
                g2.setColor(Color.CYAN);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString("h=" + heuristics.get(node), p.x + 25, p.y);
            }
        }
    }
}
