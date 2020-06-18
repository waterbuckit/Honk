package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.AST.expressions.Expression;

public class ReturnStatement extends Statement {

    private Expression toReturn;

    public ReturnStatement(Expression toReturn) {
        this.toReturn = toReturn;
    }

    public Expression getToReturn() {
        return toReturn;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
