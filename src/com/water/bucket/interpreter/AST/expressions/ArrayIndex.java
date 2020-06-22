package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.Lexeme;

import java.util.ArrayList;

public class ArrayIndex implements Expression {
    private Expression source;
    private final ArrayList<Expression> index;

    public ArrayIndex(Expression source, ArrayList<Expression> index) {
        this.source = source;
        this.index = index;
    }


    public ArrayList<Expression> getIndexPath() {
        return index;
    }

    public Expression getSource() {
        return source;
    }

    public void setSource(Expression source) {
        this.source = source;
    }

    public ArrayList<Expression> getIndex() {
        return index;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
