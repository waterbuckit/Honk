package com.water.bucket.interpreter;

import com.water.bucket.interpreter.AST.AbstractSyntaxTree;
import com.water.bucket.interpreter.AST.expressions.*;
import com.water.bucket.interpreter.AST.statements.*;

import java.util.ArrayList;
import java.util.List;

/*
statements → statement | (statements)*
statement  → assignment | functionDef | selectionStatement | whileLoop | printStatement | functionCall |
                '{' statements '}'
expression     → equality ;
equality       → comparison ( ( "!=" | "==" ) comparison )* // CAN BE REPEATED ZERO OR MORE TIMES ;
comparison     → addition ( ( ">" | ">=" | "<" | "<=" ) addition )* ;
addition       → multiplication ( ( "-" | "+" ) multiplication )* ;
multiplication → unary ( ( "/" | "*" ) unary )* ;
unary          → ( "!" | "-" ) unary
               | primary ;
primary        → NUMBER | STRING | "false" | "true" | "nil"
               | "(" expression ")" ;
 */
public class Parser {

    private final List<Lexeme> lexemes;
    private int currentLexeme;

    public Parser(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public AbstractSyntaxTree parse(){
        this.currentLexeme = 0;
        return new AbstractSyntaxTree(this.matchStatements());
    }

    private CompoundStatement matchStatements() {
        List<Statement> statements = new ArrayList<>();
        while(lexemes.get(currentLexeme).lexemeType != LexemeType.RIGHT_BRACE && existsNextLexeme()){
            Statement statement = this.matchStatement();
            if(statement == null){
                System.err.println("Parsing error near: \""+ lexemes.get(currentLexeme).getLexeme() +
                        "\" at line " +lexemes.get(currentLexeme).getLine());;
                System.exit(1);
            }
            statements.add(statement);
        }
        return new CompoundStatement(statements);
    }

    private Statement matchStatement() {

        int originalLexeme = this.currentLexeme;
        // assignment statement
        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.LET, LexemeType.IDENTIFIER, LexemeType.EQUAL}, originalLexeme)){
            Expression expression = this.matchExpression();
            if(this.matchOnLexemeType(new LexemeType[]{LexemeType.SEMICOLON})){
                return new AssignmentStatement(this.lexemes.get(originalLexeme + 1), expression);
            }
            this.currentLexeme = originalLexeme;
        }

        // if statement
        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.IF, LexemeType.LEFT_PAREN}, originalLexeme)){
            Expression expression = this.matchExpression();
            if(this.matchLexemeTypesOrdered(new LexemeType[]{LexemeType.RIGHT_PAREN, LexemeType.LEFT_BRACE}, originalLexeme)){
                CompoundStatement statementGroup = this.matchStatements();
                if(this.matchOnLexemeType(new LexemeType[]{LexemeType.RIGHT_BRACE})){
                    return new SelectionStatement(expression, statementGroup, matchElseIf(currentLexeme));
                }
                this.currentLexeme = originalLexeme;
            }
        }

        // function definition
        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.FUN, LexemeType.IDENTIFIER, LexemeType.LEFT_PAREN},
                originalLexeme)){
            Lexeme functionIdentifier = this.lexemes.get(originalLexeme+1);
            List<Lexeme> functionParams = new ArrayList<>();
            while(matchOnLexemeType(new LexemeType[]{LexemeType.IDENTIFIER})){
                functionParams.add(this.lexemes.get(currentLexeme-1));
            }
            if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.RIGHT_PAREN, LexemeType.LEFT_BRACE},originalLexeme)){
                CompoundStatement statementGroup = this.matchStatements();
                if(this.matchOnLexemeType(new LexemeType[]{LexemeType.RIGHT_BRACE})){
                    return new FunctionDefinitionStatement(functionIdentifier, functionParams, statementGroup);
                }
                this.currentLexeme = originalLexeme;
            }
        }

        // print statement
        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.PRINT, LexemeType.LEFT_PAREN}, originalLexeme)){
            Expression expression = this.matchExpression();

            if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.RIGHT_PAREN, LexemeType.SEMICOLON}, originalLexeme)){
                return new PrintStatement(expression);
            }
        }

        // function call
        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.IDENTIFIER,LexemeType.LEFT_PAREN}, originalLexeme)){
            Lexeme identifier = this.lexemes.get(originalLexeme);
            List<Expression> arguments = new ArrayList<>();
            while(matchOnLexemeType(new LexemeType[]{LexemeType.RIGHT_PAREN})){
                arguments.add(matchExpression());
            }
            if(matchOnLexemeType(new LexemeType[]{LexemeType.SEMICOLON})){
                return new FunctionCallStatement(identifier, arguments);
            }
            this.currentLexeme = originalLexeme;
        }

        // while loops
        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.WHILE, LexemeType.LEFT_PAREN}, originalLexeme)){
            Expression condition = matchExpression();
                        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.RIGHT_PAREN, LexemeType.LEFT_BRACE},originalLexeme)){
                CompoundStatement statementGroup = this.matchStatements();
                if(this.matchOnLexemeType(new LexemeType[]{LexemeType.RIGHT_BRACE})){
                    return new WhileLoopStatement(condition, statementGroup);
                }
                this.currentLexeme = originalLexeme;
            }

        }
        return null;
    }

    private Statement matchElseIf(int originalLexeme) {
        if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.ELSE, LexemeType.IF, LexemeType.LEFT_PAREN}, originalLexeme)) {
            Expression expression = this.matchExpression();
            if (this.matchLexemeTypesOrdered(new LexemeType[]{LexemeType.RIGHT_PAREN, LexemeType.LEFT_BRACE}, originalLexeme)) {
                CompoundStatement statementGroup = this.matchStatements();
                if (this.matchOnLexemeType(new LexemeType[]{LexemeType.RIGHT_BRACE})) {
                    return new SelectionStatement(expression, statementGroup, this.matchElseIf(currentLexeme));
                }
                this.currentLexeme = originalLexeme;
            }
        }else if(matchLexemeTypesOrdered(new LexemeType[]{LexemeType.ELSE,LexemeType.LEFT_BRACE}, originalLexeme)) {
                CompoundStatement statementGroup = this.matchStatements();
                if (this.matchOnLexemeType(new LexemeType[]{LexemeType.RIGHT_BRACE})) {
                    return statementGroup;
                }
                this.currentLexeme = originalLexeme;
        }
        return null;
    }

    private boolean existsNextLexeme() {
        return (currentLexeme < this.lexemes.size()-1);
    }

    private Expression matchExpression(){
        return this.matchEquality();
    }
    private boolean matchLexemeTypesOrdered(LexemeType[] lexemeTypes, int originalLexeme){
        for(LexemeType lexemeType : lexemeTypes) {
            if (lexemeType == this.lexemes.get(currentLexeme).lexemeType){
                if (existsNextLexeme()) {
                    currentLexeme++;
                }
            }else{
                currentLexeme = originalLexeme;
                return false;
            }
        }
        return true;
    }

    private boolean matchOnLexemeType(LexemeType[] lexemeTypes){
        for(LexemeType lexemeType : lexemeTypes){
            if(lexemeType == this.lexemes.get(currentLexeme).lexemeType) {
                if (existsNextLexeme()) {
                    currentLexeme++;
                }
                return true;
            }
        }
        return false;
    }

    private Expression matchEquality() {
        Expression leftSideComparison = this.matchComparison();

        while(matchOnLexemeType(new LexemeType[]{LexemeType.NOT_EQUAL, LexemeType.EQUAL_EQUAL})){
            Lexeme operator = this.lexemes.get(currentLexeme-1);
            Expression rightSideComparison = this.matchComparison();
            // we rewrite as the whole expression
            leftSideComparison = new CompoundExpression(
                    new BinaryExpression(operator, leftSideComparison, rightSideComparison));
        }

        // because the right hand side may not need to be completed for the equality production rule
        return leftSideComparison;
    }


    private Expression matchComparison() {

        Expression leftSideAddition = this.matchAddition();

        while(matchOnLexemeType(new LexemeType[]{LexemeType.GREATER, LexemeType.GREATER_EQUAL, LexemeType.LESS,
                LexemeType.LESS_EQUAL})){
            Lexeme operator = this.lexemes.get(currentLexeme-1);
            Expression rightSideAddition = this.matchAddition();
            leftSideAddition = new CompoundExpression(new BinaryExpression(operator, leftSideAddition, rightSideAddition));
        }

        return leftSideAddition;
    }

    private Expression matchAddition() {
        Expression leftSideMultiplication = this.matchMultiplication();

        while(matchOnLexemeType(new LexemeType[]{LexemeType.MINUS, LexemeType.PLUS})){
            Lexeme operator = this.lexemes.get(currentLexeme-1);
            Expression rightSideMultiplication = this.matchMultiplication();
            leftSideMultiplication = new CompoundExpression(
                    new BinaryExpression(operator, leftSideMultiplication, rightSideMultiplication));
        }

        return leftSideMultiplication;
    }

    private Expression matchMultiplication() {

        Expression leftSideUnary = this.matchUnary();

        while(matchOnLexemeType(new LexemeType[]{LexemeType.STAR, LexemeType.SLASH, LexemeType.PERCENT})){
            Lexeme operator = this.lexemes.get(currentLexeme-1);
            Expression rightSideUnary = this.matchUnary();
            leftSideUnary = new CompoundExpression(new BinaryExpression(operator, leftSideUnary, rightSideUnary));
        }

        return leftSideUnary;
    }

    private Expression matchUnary() {

        if(matchOnLexemeType(new LexemeType[]{LexemeType.NOT, LexemeType.MINUS})){
            Lexeme operator = this.lexemes.get(currentLexeme-1);
            Expression rightSideUnary = this.matchUnary();
            return new UnaryExpression(operator, rightSideUnary);
        }
        return this.matchPrimary();
    }

    private Expression matchPrimary() {

        if(matchOnLexemeType(new LexemeType[]{LexemeType.NUMBER, LexemeType.STRING, LexemeType.FALSE,
            LexemeType.TRUE, LexemeType.NIL,})) {
            return new Literal(this.lexemes.get(currentLexeme - 1));
        }else if(matchOnLexemeType(new LexemeType[]{LexemeType.IDENTIFIER})){
            return new Identifier(this.lexemes.get(currentLexeme - 1));
        }else if(matchOnLexemeType(new LexemeType[]{LexemeType.LEFT_PAREN})){
            Expression expression = this.matchExpression();
            if(matchOnLexemeType(new LexemeType[]{LexemeType.RIGHT_PAREN})){
                return new CompoundExpression(expression);
            }
        }
        System.err.println("Error in parsing: " + lexemes.get(currentLexeme).toString());
        System.exit(1);
        return null;
    }

}
