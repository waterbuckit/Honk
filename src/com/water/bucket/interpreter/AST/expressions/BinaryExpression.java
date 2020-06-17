package com.water.bucket.interpreter.AST.expressions;

/*
    Expressions like expression operator expression
 */

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.Lexeme;

public class BinaryExpression implements Expression {
    private final Lexeme lexeme;
    private final Expression leftExpression;
    private final Expression rightExpression;

    public BinaryExpression(Lexeme lexeme, Expression leftExpression, Expression rightExpression) {
        this.lexeme = lexeme;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "BinaryExpression("+ lexeme.getLexeme() +
                ", " + leftExpression +
                ", " + rightExpression +
                ')';
    }
}
