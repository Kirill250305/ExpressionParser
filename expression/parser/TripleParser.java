package expression.parser;

import expression.MyExpression;

@FunctionalInterface
public interface TripleParser {
    MyExpression parse(String expression);
}
