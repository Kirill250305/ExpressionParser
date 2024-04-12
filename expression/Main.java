package expression;

import expression.exceptions.ExpressionParser;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        ExpressionParser te = new ExpressionParser();
        System.out.println(te.parse("1 * - 1").toString());
    }
}