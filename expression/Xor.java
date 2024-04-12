package expression;

import java.math.BigInteger;
import java.util.List;

public class Xor extends Operations {
    public Xor(MyExpression a, MyExpression b) {
        super(a, b, -1, true,  "^");
    }

    @Override
    public int evaluate(int x) {
        return element1.evaluate(x) ^ element2.evaluate(x);
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return element1.evaluate(x).xor(element2.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return element1.evaluate(x, y, z) ^ element2.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> x) {
        return element1.evaluate(x) ^ element2.evaluate(x);
    }
}
