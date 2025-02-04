package algorithms;
import ui.GraphPanel;
import java.util.*;

public class AlphaBetaPruning {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        alphaBeta(graph, start, Integer.MIN_VALUE, Integer.MAX_VALUE, true, visitedNodes, panel);
    }

    private static int alphaBeta(Map<String, List<String>> graph, String node, int alpha, int beta, boolean isMax, List<String> visitedNodes, GraphPanel panel) {
        visitedNodes.add(node);
        panel.repaint();
        panel.sleep();

        if (!graph.containsKey(node)) {
            return isMax ? 1 : -1;
        }

        int bestValue = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            int value = alphaBeta(graph, neighbor, alpha, beta, !isMax, visitedNodes, panel);

            if (isMax) {
                bestValue = Math.max(bestValue, value);
                alpha = Math.max(alpha, bestValue);
            } else {
                bestValue = Math.min(bestValue, value);
                beta = Math.min(beta, bestValue);
            }

            if (beta <= alpha) break; // Pruning condition
        }
        return bestValue;
    }
}
