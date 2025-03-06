

/*
 * An enumeration for AST node types. A node's type is 
 * either a leaf node (FLOAT), or an interior node with 2
 * children. If an interior node, the type specifies the 
 * arithmetic operation, which can be + (PLUS), - (MINUS), 
 * (TIMES), or / (DIV).
 */
enum Type {
	PLUS,
	MINUS,
	TIMES,
	DIV,
	FLOAT
};


/* 
 * The Expr structure defines a node in the expression
 * abstract syntax tree (AST). It's fields are:
 * - type: the type of the node. 
 * - subexprs: if the node has type PLUS, MINUS, DIV, or
 *      FLOAT, this contains pointers to the child
 *      nodes in the AST, given by e1 and e2
 * - literal: if the node has type FLOAT, this contains
 *      the inner floating point value
 */

struct Expr {
	enum Type type;
	union {
		struct {
			struct Expr *e1;
			struct Expr *e2;
		} subexprs;
		float literal;
	};
};

/*
 * The following constructors build a heap-allocated
 * AST. See implementation in "expr.c" for details.
*/
struct Expr *mk_plus(struct Expr *e1, struct Expr *e2);
struct Expr *mk_minus(struct Expr *e1, struct Expr *e2);
struct Expr *mk_times(struct Expr *e1, struct Expr *e2);
struct Expr *mk_div(struct Expr *e1, struct Expr *e2);
struct Expr *mk_float(float f);

/*
 * Functions to build specific expression trees for
 * part 1. See "expr.c" for details.
*/
struct Expr *mk_expr1();
struct Expr *mk_expr2();
struct Expr *mk_expr3();

/*
 * This function should free all memory associated 
 * with the given AST.
*/
void free_expr(struct Expr *e);

/*
 * This function should evaluate the given AST and
 * return the floating-point result.
*/
float eval(struct Expr *e);

void print_expr(struct Expr *e);

