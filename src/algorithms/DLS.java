package algorithms;
import ui.GraphPanel;
import java.util.*;

public class DLS {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel, int depthLimit) {
        dls(graph, start, goal, new HashSet<>(), visitedNodes, panel, depthLimit);
    }

    private static boolean dls(Map<String, List<String>> graph, String node, String goal, Set<String> visited, List<String> visitedNodes, GraphPanel panel, int depth) {
        if (depth == 0) return false;

        visited.add(node);
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        if (node.equals(goal)) return true;

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor) && dls(graph, neighbor, goal, visited, visitedNodes, panel, depth - 1)) {
                return true;
            }
        }
        return false;
    }
}
