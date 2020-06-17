package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.AST.expressions.Expression;

public class WhileLoopStatement extends Statement {
    private final Expression condition;
    private final CompoundStatement statementGroup;

    public WhileLoopStatement(Expression condition, CompoundStatement statementGroup) {
        this.condition = condition;
        this.statementGroup = statementGroup;
    }

    public Expression getCondition() {
        return condition;
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
        return "WhileLoopStatement("+ condition +
                ", "+statementGroup +
                ')';
    }
}
