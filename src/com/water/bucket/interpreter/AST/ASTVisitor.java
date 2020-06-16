package com.water.bucket.interpreter.AST;

import com.water.bucket.interpreter.AST.expressions.BinaryExpression;
import com.water.bucket.interpreter.AST.expressions.Identifier;
import com.water.bucket.interpreter.AST.expressions.Literal;
import com.water.bucket.interpreter.AST.expressions.UnaryExpression;
import com.water.bucket.interpreter.AST.statements.*;

public class ASTVisitor implements ASTVisitorInterface {

    @Override
    public Object visit(PrintStatement printStatement) {
        return null;
    }

    @Override
    public Object visit(FunctionDefinitionStatement functionDefinitionStatement) {
        return null;
    }

    @Override
    public Object visit(SelectionStatement selectionStatement) {
        return null;
    }

    @Override
    public Object visit(StatementGroup statementGroup) {
        return null;
    }

    @Override
    public Object visit(AssignmentStatement asssignmentStatement) { return null;}

    @Override
    public String visit(UnaryExpression unaryExpression) {
        return null;
    }

    @Override
    public String visit(BinaryExpression binaryExpression) {
        return null;
    }

    @Override
    public String visit(Literal literal) {
        return null;
    }

    @Override
    public Object visit(Identifier identifier) {
        return null;
    }

    @Override
    public Object visit(FunctionCallStatement functionCallStatement) {
        return null;
    }

    @Override
    public Object visit(WhileLoopStatement whileLoopStatement) {
        return null;
    }
}
