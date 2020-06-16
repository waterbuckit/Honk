package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitorInterface;
import com.water.bucket.interpreter.AST.Visitable;
import java.util.List;

public class StatementGroup extends Statement implements Visitable {
    private List<Statement> statements;

    public StatementGroup(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public <T> T accept(ASTVisitorInterface<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return this.statements.toString();
    }
}
