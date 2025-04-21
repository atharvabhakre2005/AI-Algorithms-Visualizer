package algorithms;

import ui.GraphPanel;
import java.util.*;

import java.awt.Point;

public class AOStar {
    public static void run(
        Map<String, List<String>> graph, 
        Map<String, Point> positions, 
        Map<String, Integer> heuristics, 
        String start, 
        String goal, 
        List<String> visitedNodes, 
        GraphPanel panel
    ) {
        Map<String, Integer> cost = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        for (String node : graph.keySet()) {
            cost.put(node, Integer.MAX_VALUE);
        }
        cost.put(start, 0);

        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(node -> cost.get(node) + heuristics.getOrDefault(node, Integer.MAX_VALUE)));
        queue.add(start);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            visitedNodes.add(node);
            panel.repaint();
            panel.sleep();

            if (node.equals(goal)) {
                reconstructPath(parent, start, goal, panel);
                return;
            }

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                int newCost = cost.get(node) + heuristic(positions, node, neighbor);

                if (newCost < cost.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cost.put(neighbor, newCost);
                    parent.put(neighbor, node);
                    queue.add(neighbor);
                }
            }
        }
    }

    private static void reconstructPath(Map<String, String> parent, String start, String goal, GraphPanel panel) {
        String current = goal;
        while (parent.containsKey(current) && !current.equals(start)) {
            panel.markEdgeVisited(parent.get(current), current);
            current = parent.get(current);
        }
    }

    private static int heuristic(Map<String, Point> positions, String node, String goal) {
        Point p1 = positions.get(node);
        Point p2 = positions.get(goal);
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}
