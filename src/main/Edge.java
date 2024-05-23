package main;

public class Edge {
    String from;
    String to;
    int weight = 1;

    Edge(String from,String to){
        this.from = from;
        this.to = to;
    }

    Edge(String from,String to,int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
