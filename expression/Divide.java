package expression;

import java.math.BigInteger;
import java.util.List;

public class Divide extends Operations {
    public Divide(MyExpression a, MyExpression b) {
        super(a, b, 2, false, "/");
    }

    @Override
    public int evaluate(int x) {
        return element1.evaluate(x) / element2.evaluate(x);
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return element1.evaluate(x).divide(element2.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return element1.evaluate(x, y, z) / element2.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> x) {
        return element1.evaluate(x) / element2.evaluate(x);
    }
}
