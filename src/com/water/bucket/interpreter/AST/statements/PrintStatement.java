package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitorInterface;
import com.water.bucket.interpreter.AST.expressions.Expression;

public class PrintStatement extends Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public <T> T accept(ASTVisitorInterface<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "PrintStatement("+expression.toString()+")";
    }
}
