package com.water.bucket.interpreter.automata;

public class Automata {

    private Vertex startVertex;

    public Automata() {
        this.startVertex = new Vertex();
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex insertEdge(char c, Vertex to){
        return this.startVertex.insertEdge(c, to);
    }
}
