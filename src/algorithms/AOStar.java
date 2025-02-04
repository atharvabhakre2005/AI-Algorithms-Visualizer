package algorithms;
import ui.GraphPanel;
import java.util.List;
import java.util.ArrayList;

import java.util.Map;

import java.awt.Point;

public class AOStar {
    public static void run(Map<String, List<String>> graph, Map<String, Point> positions, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        aoStar(graph, positions, start, goal, visitedNodes, panel);
    }

    private static int aoStar(Map<String, List<String>> graph, Map<String, Point> positions, String node, String goal, List<String> visitedNodes, GraphPanel panel) {
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        if (node.equals(goal)) return 0;
        if (!graph.containsKey(node)) return Integer.MAX_VALUE; // Dead-end penalty

        int minCost = Integer.MAX_VALUE;

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            int cost = heuristic(positions, node, neighbor) + aoStar(graph, positions, neighbor, goal, visitedNodes, panel);
            minCost = Math.min(minCost, cost);
        }
        return minCost;
    }

    private static int heuristic(Map<String, Point> positions, String node, String goal) {
        Point p1 = positions.get(node);
        Point p2 = positions.get(goal);
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}
