package algorithms;
import ui.GraphPanel;
import java.util.*;

public class DFS {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        dfs(graph, start, goal, new HashSet<>(), visitedNodes, panel);
    }

    private static boolean dfs(Map<String, List<String>> graph, String node, String goal, Set<String> visited, List<String> visitedNodes, GraphPanel panel) {
        visited.add(node);
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        // ✅ Stop recursion if we reached the goal
        if (node.equals(goal)) return true;

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                panel.markEdgeVisited(node, neighbor);  // ✅ Highlight edge

                if (dfs(graph, neighbor, goal, visited, visitedNodes, panel)) {
                    return true; // ✅ Stop further recursion if goal is found
                }
            }
        }

        return false; // ✅ Return false if the goal was not found
    }
}
