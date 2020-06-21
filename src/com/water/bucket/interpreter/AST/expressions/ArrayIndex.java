package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.Lexeme;

import java.util.ArrayList;

public class ArrayIndex implements Expression {
    private Lexeme originalLexeme;
    private final ArrayList<Expression> index;

    public ArrayIndex(Lexeme originalLexeme, ArrayList<Expression> index) {
        this.originalLexeme = originalLexeme;
        this.index = index;
    }

    public ArrayList<Expression> getIndexPath() {
        return index;
    }

    public Lexeme getOriginalLexeme() {
        return originalLexeme;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
