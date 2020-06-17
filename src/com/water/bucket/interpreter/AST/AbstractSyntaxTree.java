package com.water.bucket.interpreter.AST;

import com.water.bucket.interpreter.AST.statements.CompoundStatement;

public class AbstractSyntaxTree {

    private final CompoundStatement rootNode;
    public AbstractSyntaxTree(CompoundStatement statementGroup) {
        this.rootNode = statementGroup;
    }

    public CompoundStatement getRootNode() {
        return rootNode;
    }

    @Override
    public String toString() {
        return "AbstractSyntaxTree{" +
                "rootNode=" + rootNode.toString() +
                '}';
    }
}
