package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.AST.expressions.Expression;
import com.water.bucket.interpreter.Lexeme;

public class AssignmentStatement extends Statement {
    private final Lexeme lexeme;
    private final Expression expression;

    public AssignmentStatement(Lexeme lexeme, Expression expression) {
        this.lexeme = lexeme;
        this.expression = expression;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "AssignmentStatement(" +
                "identifier=" + lexeme.toString() +
                ", expression=" + expression.toString() +
                ')';
    }
}
