package com.sawicki;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class Main {

    static Graph graph = new Graph();
    static String file ="src/com/sawicki/pl_wojewodztwa.in";

    public static void main(String[] args) {
        createGraph();
        List<Node> randomTraversal = graph.randomTraversal();
        randomTraversal.forEach(node -> System.out.print(node.getLabel() + " "));
        System.out.print("\nStart Score: " + graph.calculateTraversalWeight(randomTraversal));
        System.out.println("");


        for(int i=0;i< 1000;i++){
            graph.randomPermutation(randomTraversal);
        }
        List<Node> finalPremutation = graph.randomPermutation(randomTraversal);

        finalPremutation.forEach(node -> System.out.print(node.getLabel() + " "));
        System.out.println("\nFinal Score: " + graph.calculateTraversalWeight(finalPremutation));
        System.out.println("Final temperature = " + graph.getTemp());
    }

    public static void createGraph(){
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int nodes = Integer.parseInt(reader.readLine());
            for(int i=0; i<nodes;i++){
                String[] line = reader.readLine().split("\\s+");
                graph.addNode(Integer.parseInt(line[0]),line[1]);
            }
            int edges = Integer.parseInt(reader.readLine());
            for(int i=0; i<edges;i++){
                String[] line = reader.readLine().split("\\s+");
                graph.addEdge(Integer.parseInt(line[0]),Integer.parseInt(line[1]),Integer.parseInt(line[2]));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
