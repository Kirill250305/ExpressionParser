package expression.exceptions;

import expression.Divide;
import expression.MyExpression;

import java.util.List;

public class CheckedDivide extends Divide {
    public CheckedDivide(MyExpression a, MyExpression b) {
        super(a, b);
    }
    @Override
    public int evaluate(int x, int y,int z) throws MyExceptions {
        return checkDivide(element1.evaluate(x, y, z), element2.evaluate(x, y, z));
    }
    @Override
    public int evaluate(List<Integer> list) throws MyExceptions {
        return checkDivide(element1.evaluate(list), element2.evaluate(list));
    }
    private int checkDivide(int x, int y) throws OverflowException, DivisionByZero {
        if (y == 0) {
            throw new DivisionByZero();
        }
        if (y == -1 && x == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return x / y;
    }
}
