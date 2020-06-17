package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.AST.Visitable;
import java.util.List;

public class CompoundStatement extends Statement implements Visitable {
    private List<Statement> statements;

    public CompoundStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return this.statements.toString();
    }
}
