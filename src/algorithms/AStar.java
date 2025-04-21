package algorithms;

import ui.GraphPanel;
import java.util.*;
import java.awt.Point;

public class AStar {
    public static void run(
        Map<String, List<String>> graph, 
        Map<String, Point> positions, 
        Map<String, Integer> heuristics, 
        String start, 
        String goal, 
        List<String> visitedNodes, 
        GraphPanel panel
    ) {
        Map<String, Integer> gScore = new HashMap<>();
        Map<String, Integer> fScore = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        
        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingInt(fScore::get));
        
        for (String node : graph.keySet()) {
            gScore.put(node, Integer.MAX_VALUE);
            fScore.put(node, Integer.MAX_VALUE);
        }
        
        gScore.put(start, 0);
        fScore.put(start, heuristics.getOrDefault(start, Integer.MAX_VALUE));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            String node = openSet.poll();
            visitedNodes.add(node);
            if (parent.containsKey(node)) panel.markEdgeVisited(parent.get(node), node);
            panel.repaint();
            panel.sleep();

            if (node.equals(goal)) {
                reconstructPath(parent, start, goal, panel);
                return;
            }

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                int tentativeGScore = gScore.get(node) + 1; // Assuming cost of 1, change if weighted
                
                if (tentativeGScore < gScore.get(neighbor)) {
                    parent.put(neighbor, node);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristics.getOrDefault(neighbor, Integer.MAX_VALUE));
                    
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
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
}
