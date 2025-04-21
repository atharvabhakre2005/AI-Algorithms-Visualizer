package algorithms;
import ui.GraphPanel;
import java.util.*;

public class BFS {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);  // ✅ Mark start as visited immediately

        while (!queue.isEmpty()) {
            String node = queue.poll();
            visitedNodes.add(node);
            panel.repaint();
            panel.sleep();

            // ✅ Stop BFS if we found the goal
            if (node.equals(goal)) return;

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);  // ✅ Mark visited before adding to queue
                    queue.add(neighbor);
                    panel.markEdgeVisited(node, neighbor);  // ✅ Highlight edges
                }
            }
        }
    }
}
