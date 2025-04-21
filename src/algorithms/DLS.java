package algorithms;

import ui.GraphPanel;
import java.util.*;

public class DLS {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel, int depthLimit) {
        Set<String> pathVisited = new HashSet<>();  // Track visited nodes in the current path
        dls(graph, start, goal, pathVisited, visitedNodes, panel, depthLimit);
    }

    private static boolean dls(Map<String, List<String>> graph, String node, String goal, Set<String> pathVisited, List<String> visitedNodes, GraphPanel panel, int depth) {
        if (depth < 0) return false; // ✅ Base case: depth limit reached

        pathVisited.add(node);
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        if (node.equals(goal)) return true; // ✅ Goal found, stop search

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!pathVisited.contains(neighbor)) {  // ✅ Avoid cycles in the current recursion path
                panel.markEdgeVisited(node, neighbor); // ✅ Highlight edge
                if (dls(graph, neighbor, goal, pathVisited, visitedNodes, panel, depth - 1)) {
                    return true; // ✅ Stop search if goal is found
                }
            }
        }

        pathVisited.remove(node);  // ✅ Backtracking: Allow node to be visited again in different paths
        return false; // ✅ Goal not found within depth limit
    }
}
