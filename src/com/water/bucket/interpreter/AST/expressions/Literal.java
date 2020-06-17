package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.Lexeme;
import com.water.bucket.interpreter.LexemeType;

public class Literal implements Expression {
    private final Lexeme literalLexeme;
    private final Object literal;

    public Literal(Lexeme literal) {
        this.literalLexeme = literal;
        switch(literalLexeme.getLexemeType()){
            case FALSE:
                this.literal = false;
                break;
            case TRUE:
                this.literal = true;
                break;
            case NUMBER:
            case STRING:
                this.literal = literalLexeme.getLiteral();
                break;
            case NIL:
            default:
                this.literal = null;
        }
    }

    public Object getLiteralLexeme() {
        return literalLexeme;
    }

    public Object getLiteral() {
        return literal;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Literal(" + literalLexeme.getLexeme() +
                ')';
    }
}
