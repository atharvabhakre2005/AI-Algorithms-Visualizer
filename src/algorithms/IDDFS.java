package algorithms;

import ui.GraphPanel;
import java.util.*;

public class IDDFS {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            visitedNodes.clear(); // Clear visited nodes for each depth level
            Set<String> visited = new HashSet<>(); // Track visited nodes for current depth level
            
            if (dls(graph, start, goal, visited, visitedNodes, panel, depth)) {
                return; // Stop when goal is found
            }
        }
    }

    private static boolean dls(Map<String, List<String>> graph, String node, String goal, Set<String> visited, List<String> visitedNodes, GraphPanel panel, int depth) {
        if (depth < 0) return false; // Base case: limit reached

        visited.add(node);
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        if (node.equals(goal)) return true; // Goal found

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) { // Prevent revisits in this depth iteration
                panel.markEdgeVisited(node, neighbor);
                if (dls(graph, neighbor, goal, visited, visitedNodes, panel, depth - 1)) {
                    return true; // Stop searching once goal is found
                }
            }
        }

        return false; // Goal not found within depth limit
    }
}