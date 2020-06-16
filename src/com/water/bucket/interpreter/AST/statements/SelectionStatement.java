package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitorInterface;
import com.water.bucket.interpreter.AST.expressions.Expression;

public class SelectionStatement extends Statement{

    private final Expression expression;
    private final StatementGroup statementGroup;

    public SelectionStatement(Expression expression, StatementGroup statementGroup) {
        this.expression = expression;
        this.statementGroup = statementGroup;
    }

    @Override
    public <T> T accept(ASTVisitorInterface<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "SelectionStatement(" + this.expression + ", " + statementGroup.toString()+")";
    }
}
