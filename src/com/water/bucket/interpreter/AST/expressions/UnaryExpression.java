package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.Lexeme;

/*
    "-" and "!"
 */
public class UnaryExpression implements Expression {
    private final Lexeme lexeme;
    private final Expression rightExpression;

    public UnaryExpression(Lexeme lexeme, Expression right){
        this.lexeme = lexeme;
        this.rightExpression = right;
    }

    public Lexeme getLexeme() {
        return lexeme;
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
        return "UnaryExpression("+ lexeme.getLexeme() +", "+ rightExpression +
                ')';
    }
}
