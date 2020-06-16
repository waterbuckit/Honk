package com.water.bucket.interpreter;

public class Lexeme {
    final LexemeType lexemeType;
    final String lexeme;
    final Object literal;
    final int line;

    public Lexeme(LexemeType lexemeType, String lexeme, Object literal, int line) {
        this.lexemeType = lexemeType;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public LexemeType getLexemeType() {
        return lexemeType;
    }

    public String getLexeme() {
        return lexeme;
    }

    public Object getLiteral() {
        return literal;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "lexemeType=" + lexemeType +
                ", lexeme='" + lexeme + '\'' +
                ", literal=" + literal +
                ", line=" + line +
                '}';
    }
}
