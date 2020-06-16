package com.water.bucket.interpreter.AST;

public interface Visitable {
    abstract <T> T accept(ASTVisitorInterface<T> visitor);
}
