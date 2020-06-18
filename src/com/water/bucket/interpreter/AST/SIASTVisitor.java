package com.water.bucket.interpreter.AST;

import com.water.bucket.interpreter.AST.expressions.*;
import com.water.bucket.interpreter.AST.statements.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class SIASTVisitor implements ASTVisitor {

    private final Deque<ScopeInstance> callStack;

    public SIASTVisitor(){
        this.callStack = new ArrayDeque<>();
        this.callStack.push(new ScopeInstance());
    }

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
        this.callStack.peekFirst().addFunction(functionDefinitionStatement);
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
            if(this.callStack.peekFirst().isReturned()){ return this.callStack.peekFirst().getReturnVal(); };
        }
        return null;
    }

    @Override
    public Object visit(AssignmentStatement assignmentStatement) {
        Object literal = evaluate(assignmentStatement.getExpression());
        this.callStack.peekFirst().addVariable(assignmentStatement.getLexeme().getLexeme(), literal);
        return null;
    }

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
        Object variable;
        if((variable = this.callStack.peekFirst().getVariable(identifier.getIdentifier())) != null
        || (variable = this.callStack.peekLast().getVariable(identifier.getIdentifier()))!= null) {
            return variable;
        }else{
            System.err.println("Variable: " + identifier + " does not exist");
            System.exit(1);
        }
        return null;
    }

    @Override
    public Object visit(FunctionCallStatement functionCallStatement) {
        callStack.push(new ScopeInstance());
        FunctionDefinitionStatement function;
        if((function = this.callStack.peekLast().getFunction(functionCallStatement.getIdentifier().getLexeme())) != null
            || (function = this.callStack.peekFirst().getFunction(functionCallStatement.getIdentifier().getLexeme()))!= null) {

            if(function.getFunctionParams().size() != functionCallStatement.getArguments().size()){
                System.err.println("Mismatch in number of supplied parameters to function: " +
                        functionCallStatement.getIdentifier().getLexeme());
            }

            for(int i = 0; i < functionCallStatement.getArguments().size(); i++){
                callStack.peek().addVariable(function.getFunctionParams().get(i).getLexeme(),
                        evaluate(functionCallStatement.getArguments().get(i)));
            }
            evaluate(function.getStatementGroup());
            callStack.pop();
        }
        return null;
    }

    @Override
    public Object visit(WhileLoopStatement whileLoopStatement) {
        while((boolean) evaluate(whileLoopStatement.getCondition())){
            evaluate(whileLoopStatement.getStatementGroup());
        }
        return null;
    }

    @Override
    public Object visit(CompoundExpression groupedExpression) {
        return evaluate(groupedExpression.getExpression());
    }

    @Override
    public Object visit(ReAssignmentStatement reAssignmentStatement) {
        Object variable;
        if((variable = this.callStack.peekFirst().getVariable(reAssignmentStatement.getLexeme().getLexeme())) != null
        || (variable = this.callStack.peekLast().getVariable(reAssignmentStatement.getLexeme().getLexeme()))!= null) {
            variable = evaluate(reAssignmentStatement.getExpression());
            this.callStack.peekFirst().addVariable(reAssignmentStatement.getLexeme().getLexeme(), variable);
            return variable;
        }else{
            System.err.println("Variable: " + reAssignmentStatement.getLexeme().getLexeme() + " does not exist");
            System.exit(1);
        }
        return null;
    }

    @Override
    public Object visit(ReturnStatement returnStatement) {
        this.callStack.peekFirst().ret(evaluate(returnStatement.getToReturn()));
        return null;
    }

    @Override
    public Object visit(FunctionCallExpression functionCallExpression) {
        Object returnVal = null;
        FunctionDefinitionStatement function;
        if((function = this.callStack.peekFirst().getFunction(functionCallExpression.getIdentifier().getLexeme())) != null
            || (function = this.callStack.peekLast().getFunction(functionCallExpression.getIdentifier().getLexeme()))!= null) {

            if(function.getFunctionParams().size() != functionCallExpression.getArguments().size()){
                System.err.println("Mismatch in number of supplied parameters to function: " +
                        functionCallExpression.getIdentifier().getLexeme());
            }

            HashMap<String, Object> variables = new HashMap<>();

            for(int i = 0; i < functionCallExpression.getArguments().size(); i++){
                variables.put(function.getFunctionParams().get(i).getLexeme(),
                        evaluate(functionCallExpression.getArguments().get(i)));
            }
            callStack.push(new ScopeInstance(variables));
            returnVal = evaluate(function.getStatementGroup());
            callStack.pop();
        }else {
            System.err.println("Function: " + functionCallExpression.getIdentifier().getLexeme() + " does not exist");
            System.exit(1);
        }
        return returnVal;

    }
}
