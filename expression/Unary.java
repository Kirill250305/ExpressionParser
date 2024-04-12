package expression;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class Unary extends Operations {
    public Unary(MyExpression a) {
        super(a, 4, true, "-");
    }

    @Override
    public int evaluate(int x) {
        return -element1.evaluate(x);
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return element1.evaluate(x).negate();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -element1.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + element1.toString() + ")";
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-");
        if (element1.getPriority() < 4 || element1.toString().startsWith("(")) {
            sb.append("(").append(element1.toMiniString()).append(")");
        } else {
            sb.append(" ").append(element1.toMiniString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operations operations = (Operations) o;
        return Objects.equals(element1, operations.element1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element1, this.getClass());
    }

    @Override
    public int evaluate(List<Integer> x) {
        return -element1.evaluate(x);
    }
}
