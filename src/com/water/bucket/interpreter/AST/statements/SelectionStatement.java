package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.AST.expressions.Expression;

public class SelectionStatement extends Statement{

    private final Expression expression;
    private final CompoundStatement statementGroup;

    public SelectionStatement(Expression expression, CompoundStatement statementGroup) {
        this.expression = expression;
        this.statementGroup = statementGroup;
    }

    public Expression getExpression() {
        return expression;
    }

    public CompoundStatement getStatementGroup() {
        return statementGroup;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "SelectionStatement(" + this.expression + ", " + statementGroup.toString()+")";
    }
}
