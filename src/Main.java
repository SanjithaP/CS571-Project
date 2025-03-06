package src;
public class Main {
    public static void main(String[] args) {
        System.out.println("Output of part 1");
        System.out.println("================");
        Expr expr1 = mkExpr1();
        System.out.println("Result of expr1: " + expr1.eval());
        Expr expr2 = mkExpr2();
        System.out.println("Result of expr2: " + expr2.eval());
        Expr expr3 = mkExpr3();
        System.out.println("Result of expr3: " + expr3.eval());
        System.out.println("Output of part 2");
        System.out.println("================");
        System.out.println("Result of expr1: " + expr1.accept(new ExprEvaluator()));
        System.out.println("Result of expr2: " + expr2.accept(new ExprEvaluator()));
        System.out.println("Result of expr3: " + expr3.accept(new ExprEvaluator()));
    }
    public static Expr mkExpr1() {
        return new PlusExpr(new FloatExpr(3), new TimesExpr(new FloatExpr(2), new FloatExpr(5)));
    }
    public static Expr mkExpr2() {
        return new PlusExpr(new DivExpr(new FloatExpr(1), new FloatExpr(2)), new DivExpr(new FloatExpr(3), new FloatExpr(8)));
    }
    public static Expr mkExpr3() {
        return new MinusExpr(new DivExpr(new FloatExpr(1), new FloatExpr(2)), new DivExpr(new FloatExpr(4), new PlusExpr(new FloatExpr(2), new FloatExpr(3))));
    }
}