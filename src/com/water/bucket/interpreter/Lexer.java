package com.water.bucket.interpreter;

import com.water.bucket.interpreter.automata.Automata;
import com.water.bucket.interpreter.automata.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Lexer {
    private final String source;
    private final List<Lexeme> lexemes;
    private final Interpreter interpreter;
    //private final Automata automata;

    private int start;
    private int current;
    private int line;

    private HashMap<String, LexemeType> keywords;

    public Lexer(String source, Interpreter interpreter) {
        this.interpreter = interpreter;
        this.source = source;
        this.lexemes = new ArrayList<>();

        this.keywords = new HashMap<>();
        setUpKeyWords();

        //this.automata = new Automata();
        //setUpAutomata();
    }

    private void setUpKeyWords(){
        keywords.put("and",   LexemeType.AND);
        keywords.put("class", LexemeType.CLASS);
        keywords.put("else",  LexemeType.ELSE);
        keywords.put("false", LexemeType.FALSE);
        keywords.put("for",   LexemeType.FOR);
        keywords.put("fun",   LexemeType.FUN);
        keywords.put("if",    LexemeType.IF);
        keywords.put("nil",   LexemeType.NIL);
        keywords.put("or",    LexemeType.OR);
        keywords.put("print", LexemeType.PRINT);
        keywords.put("return",LexemeType.RETURN);
        keywords.put("super", LexemeType.SUPER);
        keywords.put("this",  LexemeType.THIS);
        keywords.put("true",  LexemeType.TRUE);
        keywords.put("let",   LexemeType.LET);
        keywords.put("while", LexemeType.WHILE);

    }

    public List<Lexeme> scanLexemes(){
        while(!this.isAtEnd()){
            start = current;
            scanLexeme();
        }
        this.lexemes.add(new Lexeme(LexemeType.EOF, "", null, line));
        return lexemes;
    }

    private boolean isAtEnd() {
        return this.current >= this.source.length();
    }

    //private void setUpAutomata() {
    //    Vertex numericLiteralVert = new Vertex(this::handleNumericLiteral);
    //    Vertex identifierLiteralVert = new Vertex(this::handleIdentifierLiteral);
    //    for(char i = 'a'; i <= 'z'; i++){
    //        this.automata.insertEdge(i, identifierLiteralVert);
    //    }
    //    for(char i = 'A'; i <= 'Z'; i++){
    //        this.automata.insertEdge(i, identifierLiteralVert);
    //    }
    //    for(int i = 0; i < 10; i++){
    //        this.automata.insertEdge((char) (i + 48), numericLiteralVert);
    //    }
    //    this.automata.insertEdge('(', new Vertex(LexemeType.LEFT_PAREN));
    //    this.automata.insertEdge(')', new Vertex(LexemeType.RIGHT_PAREN));
    //    this.automata.insertEdge('{', new Vertex(LexemeType.LEFT_BRACE));
    //    this.automata.insertEdge('}', new Vertex(LexemeType.RIGHT_BRACE));
    //    this.automata.insertEdge(',', new Vertex(LexemeType.COMMA));
    //    this.automata.insertEdge('.', new Vertex(LexemeType.DOT));
    //    this.automata.insertEdge('-', new Vertex(LexemeType.MINUS));
    //    this.automata.insertEdge('+', new Vertex(LexemeType.PLUS));
    //    this.automata.insertEdge(';', new Vertex(LexemeType.SEMICOLON));
    //    this.automata.insertEdge('*', new Vertex(LexemeType.STAR));
    //    this.automata.insertEdge('!', new Vertex(LexemeType.NOT)).insertEdge('=',
    //            new Vertex(LexemeType.NOT_EQUAL));
    //    this.automata.insertEdge('=', new Vertex(LexemeType.EQUAL)).insertEdge('=',
    //            new Vertex(LexemeType.EQUAL_EQUAL));
    //    this.automata.insertEdge('>', new Vertex(LexemeType.GREATER)).insertEdge('=',
    //            new Vertex(LexemeType.GREATER_EQUAL));
    //    this.automata.insertEdge('<', new Vertex(LexemeType.LESS)).insertEdge('=',
    //            new Vertex(LexemeType.LESS_EQUAL));
    //    this.automata.insertEdge(' ', new Vertex());
    //    this.automata.insertEdge('\t', new Vertex());
    //    this.automata.insertEdge('\r', new Vertex());
    //    this.automata.insertEdge('\n', new Vertex(()->line++));
    //    this.automata.insertEdge('/', new Vertex(LexemeType.SLASH))
    //            .insertEdge('/', new Vertex(()->{
    //                while(peek() != '\n' && !isAtEnd()){
    //                    incrementCurrent();
    //                }
    //            }));
    //    this.automata.insertEdge('"', new Vertex(this::handleStringLiteral));
    //    this.automata.insertEdge('|', new Vertex(this::lexemeError))
    //            .insertEdge('|', new Vertex(LexemeType.OR));
    //    this.automata.insertEdge('&', new Vertex(this::lexemeError))
    //            .insertEdge('&', new Vertex(LexemeType.AND));
    //}

    private void lexemeError() {
        this.interpreter.error(line, "Lexeme error at: " + source.charAt(current-1));
    }

    //private void scanLexeme(Vertex currentVertex) {
    private void scanLexeme() {
        char c = incrementCurrent();

        switch (c) {
            case '(': addLexeme(LexemeType.LEFT_PAREN); break;
            case ')': addLexeme(LexemeType.RIGHT_PAREN); break;
            case '[': addLexeme(LexemeType.LEFT_SQUARE); break;
            case ']': addLexeme(LexemeType.RIGHT_SQUARE); break;
            case '{': addLexeme(LexemeType.LEFT_BRACE); break;
            case '}': addLexeme(LexemeType.RIGHT_BRACE); break;
            case ',': addLexeme(LexemeType.COMMA); break;
            case '%': addLexeme(LexemeType.PERCENT); break;
            case '.': addLexeme(LexemeType.DOT); break;
            case '-': addLexeme(LexemeType.MINUS); break;
            case '+': addLexeme(LexemeType.PLUS); break;
            case ';': addLexeme(LexemeType.SEMICOLON); break;
            case '*': addLexeme(LexemeType.STAR); break; // [slash]
            case '!': addLexeme(incrementCurrentOnMatch('=') ? LexemeType.NOT_EQUAL : LexemeType.NOT); break;
            case '=': addLexeme(incrementCurrentOnMatch('=') ? LexemeType.EQUAL_EQUAL : LexemeType.EQUAL); break;
            case '<': addLexeme(incrementCurrentOnMatch('=') ? LexemeType.LESS_EQUAL : LexemeType.LESS); break;
            case '>': addLexeme(incrementCurrentOnMatch('=') ? LexemeType.GREATER_EQUAL : LexemeType.GREATER); break;
            case '/':
                if (incrementCurrentOnMatch('/')) {
                    while (peek() != '\n' && !isAtEnd()) incrementCurrent();
                } else {
                    addLexeme(LexemeType.SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            case '"': handleStringLiteral(); break;

            default:
                if (Character.isDigit(c)) {
                    handleNumericLiteral();
                } else if (Character.isAlphabetic(c)) {
                    handleIdentifierLiteral();
                } else {
                    lexemeError();
                    System.exit(1);
                }
                break;
        }

        //Vertex nextVertex = currentVertex.matchEdge(c);
        //if(nextVertex == null){
        //    System.out.println(this.lexemes);
        //    this.lexemeError();
        //    System.exit(1);
        //}
        //if (nextVertex.hasEdges()) {
        //    scanLexeme(nextVertex);
        //} else if (nextVertex.getLexeme() != null) {
        //    this.addLexeme(nextVertex.getLexeme());
        //} else if (nextVertex.doAction()) {
        //}
    }

    private void handleIdentifierLiteral() {
        while(Character.isAlphabetic(peek()) || Character.isDigit(peek())) {
            incrementCurrent();
        }
        LexemeType type = this.keywords.get(source.substring(start, current));
        if(type == null){
            type = LexemeType.IDENTIFIER;
        }
        addLexeme(type);
    }

    private void handleNumericLiteral() {
        while(Character.isDigit(peek())){
            incrementCurrent();
        }
        if(peek() == '.' && Character.isDigit(peekNext())){
           incrementCurrent();
           while(Character.isDigit(peek())) {
               incrementCurrent();
           }
        }
        addLexeme(LexemeType.NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext() {
        if(!(current+1 >= source.length())){
            return source.charAt(current+1);
        }
        return '\0';
    }

    private void handleStringLiteral() {
        while(peek() != '"' && !isAtEnd()){
            if(peek() == '\n'){
                line++;
            }
            incrementCurrent();
        }
        if(isAtEnd()){
            this.interpreter.error(line,"Unterminated string literal");
        }
        // since we should be at the closing " now, we must advance on it.
        incrementCurrent();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addLexeme(LexemeType.STRING, value);
    }

    private char peek() {
        return isAtEnd() ? '\0' : source.charAt(current);
    }

    private boolean incrementCurrentOnMatch(char c) {
        if(this.isAtEnd()) return false;
        if(this.source.charAt(this.current) == c){
            current++;
            return true;
        }
        return false;
    }

    private void addLexeme(LexemeType lexemeType) {
        this.addLexeme(lexemeType, null);
    }

    private void addLexeme(LexemeType lexemeType, Object literal) {
        String text = source.substring(start, current);
        this.lexemes.add(new Lexeme(lexemeType, text, literal, line));
    }

    // get current character while incrementing
    private char incrementCurrent() {
        //System.out.println(source.charAt((current)));
        return source.charAt(++this.current-1);
    }
}
