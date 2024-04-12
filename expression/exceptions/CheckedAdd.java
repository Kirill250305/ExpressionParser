package expression.exceptions;

import expression.Add;
import expression.MyExpression;

import java.util.List;

public class CheckedAdd extends Add {
    public CheckedAdd(MyExpression a, MyExpression b) {
        super(a, b);
    }
    @Override
    public int evaluate(int x, int y, int z) throws MyExceptions {
        return checkAdd(element1.evaluate(x, y, z), element2.evaluate(x, y, z));
    }
    @Override
    public int evaluate(List<Integer> list) throws MyExceptions {
        return checkAdd(element1.evaluate(list), element2.evaluate(list));
    }
    public int checkAdd(int x, int y) throws OverflowException {
        int ans = x + y;
        if (((x ^ ans) & (y ^ ans)) < 0) {
            throw new OverflowException("overflow");
        }
        return ans;
    }
}
