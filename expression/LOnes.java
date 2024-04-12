package expression;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class LOnes extends Operations {
    public LOnes(MyExpression a) {
        super(a, 4, false, "l1");
    }

    @Override
    public int evaluate(int x) {
        if (element1.evaluate(x) == 0) return 0;
        for (int i = 31; i >= 0; --i) {
            if (((1 << i) & element1.evaluate(x)) == 0) return 31 - i;
        }
        return 32;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (element1.evaluate(x, y, z) == 0) return 0;
        for (int i = 31; i >= 0; --i) {
            if (((1 << i) & element1.evaluate(x, y, z)) == 0) return 31 - i;
        }
        return 32;
    }

    @Override
    public String toString() {
        return "l1(" + element1.toString() + ")";
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append("l1");
        if (element1.getPriority() < 4) {
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
    public BigInteger evaluate(BigInteger x) {
        return null;
    }

    @Override
    public int evaluate(List<Integer> x) {
        if (element1.evaluate(x) == 0) return 0;
        for (int i = 31; i >= 0; --i) {
            if (((1 << i) & element1.evaluate(x)) == 0) return 31 - i;
        }
        return 32;
    }
}
