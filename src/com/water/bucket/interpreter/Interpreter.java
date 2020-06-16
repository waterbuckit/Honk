package com.water.bucket.interpreter;

import com.water.bucket.interpreter.AST.AbstractSyntaxTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Interpreter {

    private boolean hadError = false;

    public Interpreter(String arg) throws IOException {
        this.run(new String(Files.readAllBytes(Paths.get(arg)), Charset.defaultCharset()));

        if(this.hadError) System.exit(1);
    }

    public void error(int line, String message) {
        this.report(line, "", message);
    }

    private void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        this.hadError = true;
    }
    private void run(String source) {
        Lexer lexer = new Lexer(source, this);
        List<Lexeme> lexemes = lexer.scanLexemes();

        //for (Lexeme lexeme : lexemes) {
        //    System.out.println(lexeme);
        //}

        Parser parser = new Parser(lexemes);
        System.out.println(parser.parse());
    }

    public Interpreter() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        while(true){
            System.out.print("> ");
            run(reader.readLine());
            this.hadError = false;
        }
    }
}
