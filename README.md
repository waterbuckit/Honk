# Honk

Honk is a **simple, general purpose, interpreted language** using a mixture of C and JavaScript syntax. It is dynamically types, uses recursive descent parsing. 

### Features
- Dynamic typing
- Multi-type arrays
- Typical Turing-completeness features (loops, selection statements)
- Functions, recursion
- Function/block scoping

### Example programs
**Printing**
To print the result of an expression, the `print();` statement is used, it returns nothing.
```
print((5+3)/2 + f(1.5));
```
**Variable declaration**
Variable declaration is denoted by the `let` keyword succeeded by an `=` and an expression, followed by a `;` to make it a statement.
```
let x = (5+3)/2 + f(1.5);
let p = true;
let n = null;
```
**Variable reassingment**
Variables, once declared, can be reassigned. This can be done using simply the identifier, the `=` and then an expression. This does not necessarily, however, need to be followed by a `;` as it is an expression and returns the value of the newly assigned variable. It allows for side effects like:
```
let x = 5;
if((x = f(x)) > 3){
	print(x);
}
```
**Arrays**
Arrays are multi-typed and are similar to other languages. One can define an array as follows:
```
let a = [3, 2, 1];
```
Accessing elements of the array is trivial:
```
let a = [3, 4];
let b = [[3,2],[1,7]];
print(a[0]);
print(a[0][1]);
```
Which returns:
```
3.0
2.0
```
**While loops**
Just as in any language, while loops are an expression which predicates the execution of the while-loops compounded statements.
```
let x = 0;
while((x = x + 1) < 3){
	print(x);	
}
```
**Statements** 
There are specific statement types but essentially all expressions must be put into a statement, that is to say, a lone expression at its outermost should be succeeded by a semicolon.
For example the following will evaluate as though a function were returning the result of 5+2. This allows the user to use statements like `5+2;` in the shell mode of the interpreter to evaluate the expression `5+2`.
```
5 + 2; 
```
**Compounded Statements**
Statement "groups" or those inside a code block create their own stack "scope" frame, and therefore any declared variables in them become unreachable after execution of said code block. For example, the following will throw an error:
```
if(true){
	if(true){
		let x = 5;
	}
}
print(x);
```
**Functions** 
Functions are evaluated as expressions so they can return a value or null.
```
fun f(x){
	...
}
f(1);
```
```
fun f(x){
	return x;
}
print(f(x));
```
### Todo
- Classes, simple inheritance
- File manipulation, reading/writing to files.
