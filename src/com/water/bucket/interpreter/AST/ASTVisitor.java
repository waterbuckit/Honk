package com.water.bucket.interpreter.AST;


import com.water.bucket.interpreter.AST.expressions.*;
import com.water.bucket.interpreter.AST.statements.*;

public interface ASTVisitor<T> {
    public T visit(PrintStatement printStatement);
    public T visit(FunctionDefinitionStatement functionDefinitionStatement);
    public T visit(SelectionStatement selectionStatement);
    public T visit(CompoundStatement statementGroup);
    public T visit(AssignmentStatement assignmentStatement);
    public T visit(UnaryExpression unaryExpression);
    public T visit(BinaryExpression binaryExpression);
    public T visit(Literal literal);
    public T visit(Identifier identifier);
    public T visit(FunctionCallStatement functionCallStatement);
    public T visit(WhileLoopStatement whileLoopStatement);

    public T visit(CompoundExpression groupedExpression);
}
