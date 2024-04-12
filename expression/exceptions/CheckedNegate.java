package expression.exceptions;

import expression.MyExpression;
import expression.Unary;

import java.util.List;

public class CheckedNegate extends Unary {
    public CheckedNegate(MyExpression a) {
        super(a);
    }

    @Override
    public int evaluate(int x, int y, int z) throws MyExceptions {
        return negateExact(element1.evaluate(x, y, z));
    }
    @Override
    public int evaluate(List<Integer> list) throws MyExceptions {
        return negateExact(element1.evaluate(list));
    }
    private int negateExact(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return -x;
    }
}
