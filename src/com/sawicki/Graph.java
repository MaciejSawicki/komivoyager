package com.sawicki;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {

    private Map<Node, List<Edge>> nodes = new HashMap<>();

    private double temp = 500.;
    private final double criteria = 0.2;
    private double alpha = 0.995;

    public double getTemp() {
        return temp;
    }

    void addNode(int id, String label) {
        nodes.putIfAbsent(new Node(id,label), new ArrayList<>());
    }

    void addEdge(int id1, int id2, int weight) {
        Node n1 = nodes.entrySet()
                        .stream().filter(e -> e.getKey()
                        .getId() == id1).map(Map.Entry::getKey)
                        .findFirst().orElse(null);
        Node n2 = nodes.entrySet()
                        .stream().filter(e -> e.getKey()
                        .getId() == id2).map(Map.Entry::getKey)
                        .findFirst().orElse(null);
        Edge edge1 = new Edge(n2,weight);
        Edge edge2 = new Edge(n1,weight);
        nodes.get(n1).add(edge1);
        nodes.get(n2).add(edge2);
    }

    List<Node> randomTraversal(){
        List keys = new ArrayList(nodes.keySet());
        Collections.shuffle(keys);
        return keys;
    }

    int calculateTraversalWeight(List<Node> traversal){
        int weightSum = 0;
        for(int i = 0; i < traversal.size()-1; i++){
            List<Edge> nodeEdges = nodes.get(traversal.get(i));
            Node nextNode = traversal.get(i+1);
            weightSum += nodeEdges.stream()
                    .filter(edge -> edge.getDestination().equals(nextNode))
                    .findFirst()
                    .orElse(new Edge(null,1000))
                    .getWeight();
        }
        List<Edge> lastEgde = nodes.get(traversal.get(traversal.size()-1));
        weightSum += lastEgde.stream()
                .filter(edge -> edge.getDestination().equals(traversal.get(0)))
                .findFirst()
                .orElse(new Edge(null,1000))
                .getWeight();

        return weightSum;
    }

    List<Node> randomPermutation(List<Node> list){
        int previousScore = calculateTraversalWeight(list);
        int fistElement = ThreadLocalRandom.current().nextInt(0, list.size());
        int secondElement = ThreadLocalRandom.current().nextInt(0, list.size());

        while (secondElement == fistElement) {
            secondElement = ThreadLocalRandom.current().nextInt(0, list.size());
        }

        Collections.swap(list, fistElement, secondElement);
        int score = calculateTraversalWeight(list);

        temp = alpha * temp;
        double pz = Math.exp(-(score - previousScore)/temp);
        if( pz < criteria) {
            Collections.swap(list, fistElement, secondElement);
        }
        return list;
    }
}
