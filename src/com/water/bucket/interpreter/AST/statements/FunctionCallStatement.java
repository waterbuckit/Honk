package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitorInterface;
import com.water.bucket.interpreter.AST.expressions.Expression;
import com.water.bucket.interpreter.Lexeme;

import java.util.List;

public class FunctionCallStatement extends Statement {
    private final Lexeme identifier;
    private final List<Expression> arguments;

    public FunctionCallStatement(Lexeme identifier, List<Expression> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public <T> T accept(ASTVisitorInterface<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "FunctionCallStatement(" + identifier +
                ", " + arguments +
                ')';
    }
}
