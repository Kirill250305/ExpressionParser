package expression;

import java.math.BigInteger;
import java.util.List;

public interface MyExpression {
    default int getPriority() {
        return 0;
    }
    int evaluate(int x);
    int evaluate(int x, int y, int z);
    default String toMiniString() {
        return toString();
    }
    int evaluate(List<Integer> variables);
    BigInteger evaluate(BigInteger x);
}
