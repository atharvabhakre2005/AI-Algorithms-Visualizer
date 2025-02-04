package algorithms;
import ui.GraphPanel;
import java.util.*;

public class MinMax {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        minmax(graph, start, true, visitedNodes, panel);
    }

    private static int minmax(Map<String, List<String>> graph, String node, boolean isMax, List<String> visitedNodes, GraphPanel panel) {
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        if (!graph.containsKey(node)) { // Leaf node condition
            return isMax ? 1 : -1;
        }

        int bestValue = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            int value = minmax(graph, neighbor, !isMax, visitedNodes, panel);
            bestValue = isMax ? Math.max(bestValue, value) : Math.min(bestValue, value);
        }
        return bestValue;
    }
}
