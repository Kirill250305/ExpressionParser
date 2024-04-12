package expression.exceptions;

import expression.MyExpression;
import expression.Subtract;

import java.util.List;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(MyExpression a, MyExpression b) {
        super(a, b);
    }

    @Override
    public int evaluate(int x, int y, int z) throws MyExceptions {
        return subtractExact(element1.evaluate(x, y, z), element2.evaluate(x, y, z));
    }
    @Override
    public int evaluate(List<Integer> list) throws MyExceptions {
        return subtractExact(element1.evaluate(list), element2.evaluate(list));
    }
    private int subtractExact(int x, int y) throws OverflowException {
        int result = x - y;
        if (((x ^ y) & (x ^ result)) < 0) {
            throw new OverflowException("overflow");
        }
        return result;

    }
}
