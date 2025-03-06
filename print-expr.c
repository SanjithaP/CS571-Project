#include "stdlib.h"
#include "expr.h"
#include "stdio.h"

void print_expr_helper(struct Expr *e) {
    if(e->type == PLUS) {
        printf("(");
        print_expr_helper(e->subexprs.e1);
        printf(" + ");
        print_expr_helper(e->subexprs.e2);
        printf(")");
    } else if (e->type == MINUS) {
        printf("(");
        print_expr_helper(e->subexprs.e1);
        printf(" - ");
        print_expr_helper(e->subexprs.e2);
        printf(")");
    } else if (e->type == TIMES) {
        printf("(");
        print_expr_helper(e->subexprs.e1);
        printf(" * ");
        print_expr_helper(e->subexprs.e2);
        printf(")");
    } else if (e->type == DIV) {
        printf("(");
        print_expr_helper(e->subexprs.e1);
        printf(" / ");
        print_expr_helper(e->subexprs.e2);
        printf(")");
    } else if (e->type == FLOAT) {
        printf("%f", e->literal);
    } else {
        exit(1);
    }
}

void print_expr(struct Expr *e) {
    print_expr_helper(e);
    printf("\n");
}