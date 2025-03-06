# Instructions

### Part 1: Standard evaluation

Implement the `eval` method on all subclasses of `Expr`. This should evaluate the expression to produce a floating point value.

### Part 2: Visitor-pattern evaluation

Re-implement expression evaluation using the visitor pattern. This requires implementing the `accept` method on all subclasses of `Expr`, the `ExprVisitor` interface, and the `ExprEvaluator` class.

### Reference output

When your program is complete, your output should match the results in the `ref_out` file.

# Command to run the application
```
make; make run
```