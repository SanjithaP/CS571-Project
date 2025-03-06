package src;

public class ExprEvaluator implements ExprVisitor<Float> {
    @Override
    public Float visitPlusExpr(PlusExpr expr) {
        return expr.getE1().accept(this) + expr.getE2().accept(this);
    }
    @Override
    public Float visitMinusExpr(MinusExpr expr) {
        return expr.getE1().accept(this) - expr.getE2().accept(this);
    }
    @Override
    public Float visitTimesExpr(TimesExpr expr) {
        return expr.getE1().accept(this) * expr.getE2().accept(this);
    }
    @Override
    public Float visitDivExpr(DivExpr expr) {
        return expr.getE1().accept(this) / expr.getE2().accept(this);
    }
    @Override
    public Float visitFloatExpr(FloatExpr expr) {
        return expr.literal;
    }
}