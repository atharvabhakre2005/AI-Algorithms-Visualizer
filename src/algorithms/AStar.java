package algorithms;
import ui.GraphPanel;
import java.util.List;
import java.util.ArrayList;

import java.util.Map;

import java.awt.Point;


import java.util.*;

public class AStar {
    public static void run(Map<String, List<String>> graph, Map<String, Point> positions, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> heuristic(positions, node, goal)));
        Set<String> visited = new HashSet<>();
        openSet.add(start);

        while (!openSet.isEmpty()) {
            String node = openSet.poll();
            visitedNodes.add(node);
            panel.repaint();
            panel.sleep();

            if (node.equals(goal)) return;

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    openSet.add(neighbor);
                }
            }
        }
    }

    private static int heuristic(Map<String, Point> positions, String node, String goal) {
        Point p1 = positions.get(node);
        Point p2 = positions.get(goal);
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}
