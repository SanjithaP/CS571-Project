package src;
public interface ExprVisitor<R> {
    R visitPlusExpr(PlusExpr expr);
    R visitMinusExpr(MinusExpr expr);
    R visitTimesExpr(TimesExpr expr);
    R visitDivExpr(DivExpr expr);
    R visitFloatExpr(FloatExpr expr);
}