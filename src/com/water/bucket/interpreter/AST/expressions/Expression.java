package com.water.bucket.interpreter.AST.expressions;


import com.water.bucket.interpreter.AST.Visitable;

/*
    Expressions match:
        literals -> numbers, strings, true, false, null
        unary -> "-" or "!"
        binary -> expression OPERATOR expression
        grouping -> expression wrapped in ( )
        operators -> booleans, arithmetic
 */
public interface Expression extends Visitable {
}
