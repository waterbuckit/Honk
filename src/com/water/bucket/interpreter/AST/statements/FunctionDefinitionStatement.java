package com.water.bucket.interpreter.AST.statements;

import com.water.bucket.interpreter.AST.ASTVisitor;
import com.water.bucket.interpreter.Lexeme;

import java.util.List;

public class FunctionDefinitionStatement extends Statement {
    private final Lexeme functionIdentifier;
    private final List<Lexeme> functionParams;
    private final CompoundStatement statementGroup;

    public FunctionDefinitionStatement(Lexeme functionIdentifier, List<Lexeme> functionParams, CompoundStatement statementGroup) {
        this.functionIdentifier = functionIdentifier;
        this.functionParams = functionParams;
        this.statementGroup = statementGroup;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "FunctionDefinitionStatement(" + functionIdentifier.toString() +
                ", " + functionParams.toString() +
                ", " + statementGroup.toString() +
                ')';
    }
}
