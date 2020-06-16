package com.water.bucket;

import com.water.bucket.interpreter.Interpreter;

import java.io.IOException;

public class SimpleInterpreter {

    public static void main(String[] args) throws IOException {
        if(args.length > 1) {
            System.err.println("Argument error, usage: si [path to script]");
            System.exit(1);
        } else if(args.length == 1){
            new Interpreter(args[0]);
        } else {
            new Interpreter();
        }
    }
}
