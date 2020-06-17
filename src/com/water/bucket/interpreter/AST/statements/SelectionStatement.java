package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.AST.expressions.Expression;

public class SelectionStatement extends Statement{

    private final Expression expression;
    private final CompoundStatement statementGroup;
    private final Statement elseIf;

    public SelectionStatement(Expression expression, CompoundStatement statementGroup,
                              Statement elseIf) {
        this.expression = expression;
        this.statementGroup = statementGroup;
        this.elseIf = elseIf;
    }

    public Statement getElseIf() {
        return elseIf;
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
        return elseIf == null ? "SelectionStatement(" + this.expression + ", " + statementGroup.toString()+")" :
             "SelectionStatement(" + this.expression + ", " + statementGroup.toString()+", " + elseIf.toString()+")";
    }
}
