package com.water.bucket.interpreter.automata;

import com.water.bucket.interpreter.LexemeType;

import java.util.HashMap;

public class Vertex {

    private HashMap<Character, Edge> edgeMap;
    private LexemeType lexemeType;
    private Runnable action;

    public Vertex(LexemeType type) {
        this.edgeMap = new HashMap<>();
        this.lexemeType = type;
    }
    public Vertex() {
        this.edgeMap = new HashMap<>();
    }

    public Vertex(Runnable f) {
        this.edgeMap = new HashMap<>();
        this.action = f;
    }

    public Vertex insertEdge(char c, Vertex to){
        this.edgeMap.put(c, new Edge(this, to));
        return to;
    }

    public boolean doAction(){
        if(this.action == null) return false;

        this.action.run();
        return true;
    }

    public Vertex matchEdge(char c){
        if(this.edgeMap.get(c) == null){
            return null;
        } else{
            return this.edgeMap.get(c).getTo();
        }
    }

    public LexemeType getLexeme(){
        return this.lexemeType;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "edgeMap=" + edgeMap +
                ", lexemeType=" + lexemeType +
                ", action=" + action +
                '}';
    }

    public boolean hasEdges() {
        return !this.edgeMap.isEmpty();
    }

}
