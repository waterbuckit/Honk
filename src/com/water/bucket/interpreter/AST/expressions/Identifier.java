package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitorInterface;
import com.water.bucket.interpreter.Lexeme;

public class Identifier implements Expression {
    private Lexeme lexeme;

    public Identifier(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public <T> T accept(ASTVisitorInterface<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Identifier("+lexeme.getLexeme()+")";
    }
}
