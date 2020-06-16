package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitorInterface;
import com.water.bucket.interpreter.AST.expressions.Expression;
import com.water.bucket.interpreter.Lexeme;

public class Literal implements Expression {
    private final Lexeme literal;

    public Literal(Lexeme literal) {
        this.literal = literal;
    }

    public Object getLiteral() {
        return literal;
    }

    @Override
    public <T> T accept(ASTVisitorInterface<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Literal(" + literal.getLexeme() +
                ')';
    }
}
