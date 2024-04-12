package expression.exceptions;

import expression.*;
import expression.parser.Buffer;
import expression.parser.Lexeme;
import expression.parser.LexemeType;
import expression.parser.TripleParser;
import java.util.*;

public class ExpressionParser extends Lexemes implements TripleParser, ListParser {
    private static Buffer lexemes = new Buffer();

    @Override
    public MyExpression parse(String expression) throws MyExceptions {
        lexemes = parseStrToLexemes(expression, List.of("x", "y", "z"));
        return expression();
    }


    @Override
    public MyExpression parse(String expression, List<String> list) throws MyExceptions {
        lexemes = parseStrToLexemes(expression, list);
        return expression();
    }
    private static MyExpression expression() {
        Lexeme lexeme = lexemes.next();
        if (lexeme.getType() == LexemeType.END) {
            return new Const(0);
        } else {
            lexemes.back();
            return minMax();
        }
    }

    private static MyExpression minMax() {
        MyExpression value = or();
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case MIN -> value = new Min(value, or());
                case MAX -> value = new Max(value, or());
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    private static MyExpression or() {
        MyExpression value = xor();
        while (true) {
            Lexeme lexeme = lexemes.next();
            if (Objects.requireNonNull(lexeme.getType()) == LexemeType.OR) {
                value = new Or(value, xor());
            } else {
                lexemes.back();
                return value;
            }
        }
    }

    private static MyExpression xor() {
        MyExpression value = and();
        while (true) {
            Lexeme lexeme = lexemes.next();
            if (Objects.requireNonNull(lexeme.getType()) == LexemeType.XOR) {
                value = new Xor(value, and());
            } else {
                lexemes.back();
                return value;
            }
        }
    }

    private static MyExpression and() {
        MyExpression value = addSubtract();
        while (true) {
            Lexeme lexeme = lexemes.next();
            if (Objects.requireNonNull(lexeme.getType()) == LexemeType.AND) {
                value = new And(value, addSubtract());
            } else {
                lexemes.back();
                return value;
            }
        }
    }

    private static MyExpression addSubtract() {
        MyExpression value = multiplyDivide();
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case ADD -> value = new CheckedAdd(value, multiplyDivide());
                case SUBTRACT -> value = new CheckedSubtract(value, multiplyDivide());
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    private static MyExpression multiplyDivide() {
        MyExpression value = factor();
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case MULTIPLY -> value = new CheckedMultiply(value, factor());
                case DIVIDE -> value = new CheckedDivide(value, factor());
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    private static MyExpression factor() {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.getType()) {
            case UNARY -> {
                return new CheckedNegate(factor());
            }
            case LOnes -> {
                return new LOnes(factor());
            }
            case TOnes -> {
                return new TOnes(factor());
            }
            case CONST -> {
                try {
                    return new Const(Integer.parseInt(lexeme.getValue()));
                } catch (NumberFormatException e) {
                    throw new ConstException("Invalid expression " + lexeme.getType() +
                            " on position " + lexemes.getPosition() + ". Invalid number");
                }
            }
            case VARIABLE -> {
                return new Variable(lexeme.getValue(), lexeme.getNumberVariable());
            }
            case LEFT_ROUND, LEFT_SQUARE, LEFT_CURLY -> {
                MyExpression value = expression();
                lexemes.next();
                return value;
            }
            default -> throw new VariableException("Invalid expression " + lexeme.getType() +
                    " on position " + lexemes.getPosition() + ". Expected variable");
        }
    }

}
