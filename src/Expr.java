package src;

abstract class Expr {
    abstract float eval();
    abstract <R> R accept(ExprVisitor<R> v);
}
abstract class BinaryExpr extends Expr {
    private Expr e1;
    private Expr e2;
    protected BinaryExpr(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    public Expr getE1() {
        return e1;
    }
    public Expr getE2() {
        return e2;
    }
}
class PlusExpr extends BinaryExpr {
    public PlusExpr(Expr e1, Expr e2) {
        super(e1, e2);
    }
    @Override
    float eval() {
        return getE1().eval() + getE2().eval();
    }
    @Override
    <R> R accept(ExprVisitor<R> v) {
        return v.visitPlusExpr(this);
    }
}
class MinusExpr extends BinaryExpr {
    public MinusExpr(Expr e1, Expr e2) {
        super(e1, e2);
    }
    @Override
    float eval() {
        return getE1().eval() - getE2().eval();
    }
    @Override
    <R> R accept(ExprVisitor<R> v) {
        return v.visitMinusExpr(this);
    }
}
class TimesExpr extends BinaryExpr {
    public TimesExpr(Expr e1, Expr e2) {
        super(e1, e2);
    }
    @Override
    float eval() {
        return getE1().eval() * getE2().eval();
    }
    @Override
    <R> R accept(ExprVisitor<R> v) {
        return v.visitTimesExpr(this);
    }
}
class DivExpr extends BinaryExpr {
    public DivExpr(Expr e1, Expr e2) {
        super(e1, e2);
    }
    @Override
    float eval() {
        return getE1().eval() / getE2().eval();
    }
    @Override
    <R> R accept(ExprVisitor<R> v) {
        return v.visitDivExpr(this);
    }
}
class FloatExpr extends Expr {
    protected float literal;

    public FloatExpr(float f) {
        this.literal = f;
    }
    @Override
    float eval() {
        return literal;
    }
    @Override
    <R> R accept(ExprVisitor<R> v) {
        return v.visitFloatExpr(this);
    }
}