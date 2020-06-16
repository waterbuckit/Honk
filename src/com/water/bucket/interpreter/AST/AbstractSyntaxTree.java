package com.water.bucket.interpreter.AST;

import com.water.bucket.interpreter.AST.statements.StatementGroup;

public class AbstractSyntaxTree {
    private final StatementGroup rootNode;
    public AbstractSyntaxTree(StatementGroup statementGroup) {
        this.rootNode = statementGroup;
    }

    @Override
    public String toString() {
        return "AbstractSyntaxTree{" +
                "rootNode=" + rootNode.toString() +
                '}';
    }
}
