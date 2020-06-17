package com.water.bucket.interpreter;

import com.water.bucket.interpreter.AST.SIASTVisitor;
import com.water.bucket.interpreter.AST.AbstractSyntaxTree;

public class Evaluator {
    private AbstractSyntaxTree abstractSyntaxTree;

    public Evaluator(AbstractSyntaxTree abstractSyntaxTree) {
        this.abstractSyntaxTree = abstractSyntaxTree;

    }

    public void evaluate() {
        SIASTVisitor visitor = new SIASTVisitor();
        this.abstractSyntaxTree.getRootNode().accept(visitor);
    }
}
