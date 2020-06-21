package com.water.bucket.interpreter.AST;

import com.water.bucket.interpreter.AST.expressions.*;
import com.water.bucket.interpreter.AST.statements.*;

import java.util.*;

public class SIASTVisitor implements ASTVisitor {

    private final Deque<ScopeInstance> callStack;

    public SIASTVisitor(){
        this.callStack = new ArrayDeque<>();
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
        this.callStack.push(new ScopeInstance(ScopeType.BLOCK_SCOPE));
        for(Statement statement : statementGroup.getStatements()){
            statement.accept(this);
            if(this.callStack.peekFirst().isReturned()){ return this.callStack.peekFirst().getReturnVal(); };
        }
        this.callStack.pop();
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
        Object variable = null;

        Iterator<ScopeInstance> descending = this.callStack.iterator();

        while (descending.hasNext()) {
            ScopeInstance current = descending.next();
            variable = current.getVariable(identifier.getIdentifier());

            if (variable != null || current.getScopeType() == ScopeType.FUNCTION_SCOPE) {
                break;
            }
        }
        if(variable != null || (variable = this.callStack.peekLast().getVariable(identifier.getIdentifier()))!= null) {
            return variable;
        }else{
            System.err.println("Variable: " + identifier + " does not exist");
            System.exit(1);
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
        Object variable = null;
        Iterator<ScopeInstance> descending = this.callStack.iterator();
        while (descending.hasNext()) {
            ScopeInstance current = descending.next();
            variable = current.getVariable(reAssignmentStatement.getLexeme().getLexeme());
            if (variable != null) {
                variable = evaluate(reAssignmentStatement.getExpression());
                current.addVariable(reAssignmentStatement.getLexeme().getLexeme(), variable);
                return variable;
            }
            if (current.getScopeType() == ScopeType.FUNCTION_SCOPE) {
                break;
            }
        }
        if(this.callStack.peekLast()
                .getVariable(reAssignmentStatement.getLexeme().getLexeme())!= null) {
            variable = evaluate(reAssignmentStatement.getExpression());
            this.callStack.peekLast().addVariable(reAssignmentStatement.getLexeme().getLexeme(), variable);
            return variable;
        }
        System.err.println("Variable: " + reAssignmentStatement.getLexeme().getLexeme() + " does not exist");
        System.exit(1);
        return null;
    }

    @Override
    public Object visit(ReturnStatement returnStatement) {
        while(callStack.peekFirst().getScopeType() != ScopeType.FUNCTION_SCOPE){
            callStack.pop();
        }
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
            callStack.push(new ScopeInstance(variables, ScopeType.FUNCTION_SCOPE));
            returnVal = evaluate(function.getStatementGroup());
            callStack.pop();
        }else {
            System.err.println("Function: " + functionCallExpression.getIdentifier().getLexeme() + " does not exist");
            System.exit(1);
        }
        return returnVal;

    }

    @Override
    public Object visit(ArrayIndex arrayIndex) {
        Object fetchedElement = null;
        Object variable = null;
        if((variable = this.visit(arrayIndex.getIdentifier())) != null
                && (variable instanceof ArrayList || variable instanceof String)) {
            for(Expression expression : arrayIndex.getIndexPath()){
                if(variable instanceof String){
                    return ((String) variable).charAt(Double.valueOf((double)evaluate(expression)).intValue());
                }else {
                    fetchedElement = ((ArrayList) variable)
                            .get(Double.valueOf((double)evaluate(expression)).intValue());
                }
                variable = fetchedElement;
            }

        }
        return fetchedElement;
    }

    @Override
    public Object visit(ExpressionStatement expressionStatement) {
        return evaluate(expressionStatement.getExpression());
    }

    @Override
    public Object visit(ArrayDefinition arrayDeclaration) {
        ArrayList<Object> elements = new ArrayList<>();
        for(Expression expr : arrayDeclaration.getElements()){
            elements.add(evaluate(expr));
        }
        return elements;
    }
}
