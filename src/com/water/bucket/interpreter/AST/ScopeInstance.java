package com.water.bucket.interpreter.AST;

import com.water.bucket.interpreter.AST.statements.FunctionDefinitionStatement;

import java.util.HashMap;

public class ScopeInstance {
    private final HashMap<String, Object> variables;
    private final HashMap<String, FunctionDefinitionStatement> functions;
    private boolean isReturned;
    private Object returnVal;

    public ScopeInstance() {
        isReturned = false;
        variables = new HashMap<>();
        functions = new HashMap<>();
    }

    public ScopeInstance(HashMap<String, Object> variables) {
        this.variables = variables;
        this.functions = new HashMap<>();
    }

    public Object getVariable(String identifier){
        return variables.get(identifier);
    }

    public void addVariable(String identifier, Object literal){
        variables.put(identifier, literal);
    }

    public void addFunction(FunctionDefinitionStatement fun){
        functions.put(fun.getFunctionIdentifier().getLexeme(), fun);
    }

    public boolean isReturned() {
        return isReturned;
    }

    public FunctionDefinitionStatement getFunction(String identifier){
        return functions.get(identifier);
    }

    public Object getReturnVal() {
        return this.returnVal;
    }

    public void ret(Object evaluated) {
        this.isReturned = true;
        this.returnVal = evaluated;
    }

    @Override
    public String toString() {
        return "ScopeInstance{" +
                "variables=" + variables +
                ", functions=" + functions +
                ", isReturned=" + isReturned +
                ", returnVal=" + returnVal +
                '}';
    }
}
