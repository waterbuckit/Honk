package com.water.bucket.interpreter.AST.expressions;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.AST.statements.Statement;
import com.water.bucket.interpreter.Lexeme;

import java.util.List;

public class FunctionCallExpression implements Expression {
    private final Lexeme identifier;
    private final List<Expression> arguments;

    public FunctionCallExpression(Lexeme identifier, List<Expression> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public Lexeme getIdentifier() {
        return identifier;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "FunctionCallExpression(" + identifier +
                ", " + arguments +
                ')';
    }
}
