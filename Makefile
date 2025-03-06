CC=gcc -g -Wall -Wextra -Werror -fsanitize=address,leak

default : expr

expr : expr.c main.c print-expr.c
	$(CC) $^ -o $@

clean :
	rm -rf expr new_expr
