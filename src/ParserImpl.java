public class ParserImpl extends Parser {

    @Override
    public Expr do_parse() throws Exception {
        Expr result = parseT();
        // After parsing the start symbol, there should be no remaining tokens.
        if (tokens != null) {
            throw new Exception("Unexpected token: " + tokens.elem.lexeme);
        }
        return result;
    }
    
    // Nonterminal T: T -> F AddOp T | F
    private Expr parseT() throws Exception {
        Expr left = parseF();
        // Check for an AddOp (PLUS or MINUS)
        if (tokens != null && (tokens.elem.ty == TokenType.PLUS || tokens.elem.ty == TokenType.MINUS)) {
            // Peek the operator and consume it
            Token op = tokens.elem;
            if (op.ty == TokenType.PLUS) {
                consume(TokenType.PLUS);
            } else {
                consume(TokenType.MINUS);
            }
            // Parse the right-hand side recursively
            Expr right = parseT();
            if (op.ty == TokenType.PLUS) {
                return new PlusExpr(left, right);
            } else {
                return new MinusExpr(left, right);
            }
        }
        return left;
    }
    
    // Nonterminal F: F -> Lit MulOp F | Lit
    private Expr parseF() throws Exception {
        Expr left = parseLit();
        // Check for a MulOp (TIMES or DIV)
        if (tokens != null && (tokens.elem.ty == TokenType.TIMES || tokens.elem.ty == TokenType.DIV)) {
            Token op = tokens.elem;
            if (op.ty == TokenType.TIMES) {
                consume(TokenType.TIMES);
            } else {
                consume(TokenType.DIV);
            }
            Expr right = parseF();
            if (op.ty == TokenType.TIMES) {
                return new TimesExpr(left, right);
            } else {
                return new DivExpr(left, right);
            }
        }
        return left;
    }
    
    // Nonterminal Lit: Lit -> NUM | LPAREN T RPAREN
    private Expr parseLit() throws Exception {
        if (tokens == null) {
            throw new Exception("Unexpected end of input");
        }
        if (tokens.elem.ty == TokenType.NUM) {
            Token numToken = consume(TokenType.NUM);
            return new FloatExpr(Float.parseFloat(numToken.lexeme));
        } else if (tokens.elem.ty == TokenType.LPAREN) {
            consume(TokenType.LPAREN);
            Expr expr = parseT();
            consume(TokenType.RPAREN);
            return expr;
        } else {
            throw new Exception("Unexpected token: " + tokens.elem.lexeme);
        }
    }
}