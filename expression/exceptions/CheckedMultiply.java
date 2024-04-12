package expression.exceptions;

import expression.Multiply;
import expression.MyExpression;

import java.util.List;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(MyExpression a, MyExpression b) {
        super(a, b);
    }
    @Override
    public int evaluate(int x, int y, int z) throws MyExceptions {
        return checkMultiply(element1.evaluate(x, y, z), element2.evaluate(x, y, z));
    }
    @Override
    public int evaluate(List<Integer> list) throws MyExceptions {
        return checkMultiply(element1.evaluate(list), element2.evaluate(list));
    }
    public int checkMultiply(int x, int y) throws OverflowException {
        int ans = x * y;
        if (x == -1 && y == Integer.MIN_VALUE
                || y == -1 && x == Integer.MIN_VALUE
                || y != 0 && ans / y != x) {
            throw new OverflowException("overflow");
        }
        return ans;
    }
}
