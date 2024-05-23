package main;

import java.util.ArrayList;
import java.util.List;

public class WordNode {
    private String word;

    private List<Edge> Edges = new ArrayList<>();

    WordNode(String word){
        this.word = word;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Edge> getEdges() {
        return Edges;
    }

    public void setEdges(List<Edge> edges) {
        Edges = edges;
    }

    public void addEdge(String s1,String s2){
        Edges.add(new Edge(s1,s2));
    }
}
