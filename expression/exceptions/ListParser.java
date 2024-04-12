package expression.exceptions;

import expression.MyExpression;

import java.util.List;

@FunctionalInterface
public interface ListParser {
    MyExpression parse(String expression, final List<String> variables) throws Exception;
}
