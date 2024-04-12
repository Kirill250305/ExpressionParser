package expression;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class Const implements MyExpression {

    private final Object x;
    public Const(int x) {
        this.x = x;
    }
    public Const(BigInteger bigX) {
        this.x = bigX;
    }

    @Override
    public int getPriority() {
        return 4;
    }
    @Override
    public int evaluate(int x) {
        return (int) this.x;
    }
    @Override
    public BigInteger evaluate(BigInteger x) {
        return (BigInteger) this.x;
    }
    @Override
    public int evaluate(int a, int b, int c) {
        return (int) this.x;
    }
    @Override
    public int evaluate(List<Integer> x) {
        return (int) this.x;
    }
    @Override
    public String toString() {
        return String.valueOf(this.x);
    }

    @Override
    public String toMiniString() {
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return Objects.equals(x, aConst.x);

    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }
}
