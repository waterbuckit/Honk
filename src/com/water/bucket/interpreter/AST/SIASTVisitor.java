package com.water.bucket.interpreter.AST;

import com.water.bucket.interpreter.AST.expressions.*;
import com.water.bucket.interpreter.AST.statements.*;

public class SIASTVisitor implements ASTVisitor {

    private Object evaluate(Statement statement) {
        return statement.accept(this);
    }
    private Object evaluate(Expression expression) {
        return expression.accept(this);
    }

    @Override
    public Object visit(PrintStatement printStatement) {
        System.out.println(printStatement.getExpression().accept(this));
        return null;
    }

    @Override
    public Object visit(FunctionDefinitionStatement functionDefinitionStatement) {
        return null;
    }

    @Override
    public Object visit(SelectionStatement selectionStatement) {
        Object expression = evaluate(selectionStatement.getExpression());
        if((boolean) expression) {
            evaluate(selectionStatement.getStatementGroup());
        } else if(selectionStatement.getElseIf() != null){
            evaluate(selectionStatement.getElseIf());
        }
        return null;
    }

    @Override
    public Object visit(CompoundStatement statementGroup) {
        for(Statement statement : statementGroup.getStatements()){
            statement.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(AssignmentStatement assignmentStatement) { return null;}

    @Override
    public Object visit(UnaryExpression unaryExpression) {
        return null;
    }

    @Override
    public Object visit(BinaryExpression binaryExpression) {
        Object lhs = evaluate(binaryExpression.getLeftExpression());
        Object rhs = evaluate(binaryExpression.getRightExpression());
        switch(binaryExpression.getLexeme().getLexemeType()){
            case GREATER:
                return (double) lhs > (double) rhs;
            case LESS:
                return (double) lhs < (double) rhs;
            case LESS_EQUAL:
                return (double) lhs <= (double) rhs;
            case GREATER_EQUAL:
                return (double) lhs >= (double) rhs;
            case EQUAL_EQUAL:
                return Double.compare((double) lhs, (double) rhs) == 0 ? true : false;
            case NOT_EQUAL:
                return Double.compare((double) lhs, (double) rhs) != 0 ? true : false;
            case PERCENT:
                return (double) lhs % (double) rhs;
            case PLUS:
                return (double) lhs + (double) rhs;
            case MINUS:
                return (double) lhs - (double) rhs;
            case STAR:
                return (double) lhs * (double) rhs;
            case SLASH:
                return (double) lhs / (double) rhs;
            default:
                return null;
        }
    }

    @Override
    public Object visit(Literal literal) {
        return literal.getLiteral();
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

    @Override
    public Object visit(CompoundExpression groupedExpression) {
        return evaluate(groupedExpression.getExpression());
    }
}
