package algorithms;
import ui.GraphPanel;
import java.util.*;

public class BFS {
    public static void run(Map<String, List<String>> graph, String start, String goal, List<String> visitedNodes, GraphPanel panel) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            visitedNodes.add(node);
            panel.repaint();
            panel.sleep();

            if (node.equals(goal)) return;

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }
}
