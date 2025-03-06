#include "expr.h"
#include "stdlib.h"
#include "stdio.h"


struct Expr *mk_plus(struct Expr *e1, struct Expr *e2) {
    struct Expr *ret = malloc(sizeof(struct Expr));
    ret->type = PLUS;
    ret->subexprs.e1 = e1;
    ret->subexprs.e2 = e2;

    return ret;
}

struct Expr *mk_minus(struct Expr *e1, struct Expr *e2) {
    struct Expr *ret = malloc(sizeof(struct Expr));
    ret->type = MINUS;
    ret->subexprs.e1 = e1;
    ret->subexprs.e2 = e2;

    return ret;
}

struct Expr *mk_times(struct Expr *e1, struct Expr *e2) {
    struct Expr *ret =  malloc(sizeof(struct Expr));
    ret->type = TIMES;
    ret->subexprs.e1 = e1;
    ret->subexprs.e2 = e2;

    return ret;
}

struct Expr *mk_div(struct Expr *e1, struct Expr *e2) {
    struct Expr *ret = malloc(sizeof(struct Expr));
    ret->type = DIV;
    ret->subexprs.e1 = e1;
    ret->subexprs.e2 = e2;

    return ret;
}

struct Expr *mk_float(float f) {
    struct Expr *ret = malloc(sizeof(struct Expr));
    ret->type = FLOAT;
    ret->literal = f;

    return ret;
}

/* This function should create the expr (1 + (4 * 5))
 * using the above constructors.
 */
struct Expr *mk_expr1() {
    /* TODO: Your code here */
    return mk_plus( 
        mk_float(1),
        mk_times( mk_float(4) , mk_float(5))    
    );
}

/* This function should create the expr (1 + (7 / 8))
 * using the above constructors.
 */
struct Expr *mk_expr2() {
    /* TODO: Your code here */
    return mk_plus(
        mk_float(1), mk_div(mk_float(7),mk_float(8))
    );
}

/* This function should create the expr ((1 / 3) - (4 / (2 + 3)))
 * using the above constructors.
 */
struct Expr *mk_expr3() {
    /* TODO: Your code here */
    return mk_minus(mk_div(mk_float(1),mk_float(3)),mk_div(mk_float(4),mk_plus(mk_float(2),mk_float(3))));
}


/*
 * This function should free all memory associated 
 * with the given AST.
*/
void free_expr(struct Expr* e) {
    /* TODO: Your code here */
    if(!e){
        return ;
    }

    if(e->type != FLOAT) {
        free_expr(e->subexprs.e1);
        free_expr(e->subexprs.e2);
    }
    free(e);
}

/*
 * This function should evaluate the given AST and
 * return the floating-point result.
*/
float eval(struct Expr* e) {
    /* TODO: Your code here */
    if(e->type == FLOAT){
        return e->literal;
    }

    float l = eval(e->subexprs.e1);
    float r = eval(e->subexprs.e2);

    if(e->type == PLUS){
        return l + r ;
    }
    else if (e->type == MINUS) {
        return l - r ;
    }

    else if(e->type == TIMES ){
        return l * r;
    }

    else if(e->type == DIV){
        if (r == 0.0){
            printf(" Error : Division by zero ");
            return 0.0;
        }
        return l/r;
    }
    else{
        printf(" Not a valid Expression ");
        return 0.0;
    }
}




