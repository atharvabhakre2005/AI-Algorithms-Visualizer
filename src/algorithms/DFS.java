package algorithms;
import ui.GraphPanel;
import java.util.*;

public class DFS {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        dfs(graph, start, goal, new HashSet<>(), visitedNodes, panel);
    }

    private static void dfs(Map<String, List<String>> graph, String node, String goal, Set<String> visited, List<String> visitedNodes, GraphPanel panel) {
        visited.add(node);
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        if (node.equals(goal)) return;

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(graph, neighbor, goal, visited, visitedNodes, panel);
            }
        }
    }
}
