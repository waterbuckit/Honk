package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitorInterface;
import com.water.bucket.interpreter.AST.expressions.Expression;

public class WhileLoopStatement extends Statement {
    private final Expression condition;
    private final StatementGroup statementGroup;

    public WhileLoopStatement(Expression condition, StatementGroup statementGroup) {
        super();
        this.condition = condition;
        this.statementGroup = statementGroup;
    }

    @Override
    public <T> T accept(ASTVisitorInterface<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "WhileLoopStatement("+ condition +
                ", "+statementGroup +
                ')';
    }
}
