package com.water.bucket.interpreter.AST;

public interface Visitable {
    public abstract <T> T accept(ASTVisitor<T> visitor);
}
