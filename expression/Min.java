package expression;

import java.math.BigInteger;
import java.util.List;

public class Min extends Operations{
    public Min(MyExpression a, MyExpression b) {
        super(a, b, -100, true, "min");
    }

    @Override
    public int evaluate(int x) {
        return Math.min(element1.evaluate(x), element2.evaluate(x));
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        BigInteger a = element1.evaluate(x);
        BigInteger b = element2.evaluate(x);
        if (a.subtract(b).signum() < 0) return a;
        return b;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Math.min(element1.evaluate(x, y, z), element2.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> x) {
        return Math.min(element1.evaluate(x), element2.evaluate(x));
    }
}
