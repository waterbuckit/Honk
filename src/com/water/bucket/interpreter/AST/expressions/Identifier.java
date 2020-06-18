package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.Lexeme;

public class Identifier implements Expression {
    private Lexeme lexeme;

    public Identifier(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public String getIdentifier() {
        return lexeme.getLexeme();
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Identifier("+lexeme.getLexeme()+")";
    }
}
