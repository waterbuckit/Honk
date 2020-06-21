package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitor;

import java.util.List;

public class ArrayDefinition implements Expression {
    private List<Expression> elements;

    public ArrayDefinition(List<Expression> elements) {
        this.elements = elements;
    }

    public List<Expression> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "ArrayDefinition{" +
                "elements=" + elements +
                '}';
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
