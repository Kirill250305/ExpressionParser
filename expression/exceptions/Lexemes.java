package expression.exceptions;

import expression.parser.Buffer;
import expression.parser.Lexeme;
import expression.parser.LexemeType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Lexemes {
    private static final ArrayList<LexemeType> operations = new ArrayList<>();

    static {
        operations.add(LexemeType.MULTIPLY);
        operations.add(LexemeType.DIVIDE);
        operations.add(LexemeType.ADD);
        operations.add(LexemeType.SUBTRACT);
        operations.add(LexemeType.AND);
        operations.add(LexemeType.XOR);
        operations.add(LexemeType.OR);
        operations.add(LexemeType.MIN);
        operations.add(LexemeType.MAX);
        operations.add(LexemeType.LOnes);
        operations.add(LexemeType.TOnes);
        operations.add(LexemeType.UNARY);
    }

    public Buffer parseStrToLexemes(String expression, List<String> list) {
        ArrayDeque<LexemeType> parenthesis = new ArrayDeque<>();
        boolean wasWhitespace = true;
        Buffer lexemes = new Buffer();
        boolean lastConst = false;
        int pos = 0;
        int numberVariable = 0;
        while (pos < expression.length()) {
            if (!Character.isWhitespace(expression.charAt(pos))) {
                StringBuilder str = new StringBuilder(String.valueOf(expression.charAt(pos)));
                LexemeType type = switch (expression.charAt(pos)) {
                    case '(' -> {
                        parenthesis.add(LexemeType.LEFT_ROUND);
                        yield LexemeType.LEFT_ROUND;
                    }
                    case ')' -> {
                        if (parenthesis.isEmpty() || parenthesis.peek() != LexemeType.LEFT_ROUND) {
                            throw new BracketException(
                                    "Invalid expression :" + expression + ". Token: " + expression.charAt(pos)
                            );
                        }
                        parenthesis.pop();
                        yield LexemeType.RIGHT_ROUND;
                    }

                    case '[' -> {
                        parenthesis.add(LexemeType.LEFT_SQUARE);
                        yield LexemeType.LEFT_SQUARE;
                    }
                    case ']' -> {
                        if (parenthesis.isEmpty() || parenthesis.peek() != LexemeType.LEFT_SQUARE) {
                            throw new BracketException(
                                    "Invalid expression :" + expression + ". Token: " + expression.charAt(pos)
                            );
                        }
                        parenthesis.pop();
                        yield LexemeType.RIGHT_SQUARE;
                    }

                    case '{' -> {
                        parenthesis.add(LexemeType.LEFT_CURLY);
                        yield LexemeType.LEFT_CURLY;
                    }
                    case '}' -> {
                        if (parenthesis.isEmpty() || parenthesis.peek() != LexemeType.LEFT_CURLY) {
                            throw new BracketException(
                                    "Invalid expression :" + expression + ". Token: " + expression.charAt(pos)
                            );
                        }
                        parenthesis.pop();
                        yield LexemeType.RIGHT_CURLY;
                    }

                    case '*' -> {
                        lastConst = false;
                        yield LexemeType.MULTIPLY;
                    }
                    case '/' -> {
                        lastConst = false;
                        yield LexemeType.DIVIDE;
                    }
                    case '+' -> {
                        lastConst = false;
                        yield LexemeType.ADD;
                    }
                    case '-' -> {
                        LexemeType lastType = null;
                        if (lexemes.size() > 0) {
                            lastType = lexemes.get(lexemes.size() - 1).getType();
                        }
                        if (operations.contains(lastType)
                                || lastType == null
                                || lastType == LexemeType.LEFT_ROUND
                                || lastType == LexemeType.LEFT_SQUARE
                                || lastType == LexemeType.LEFT_CURLY) {
                            if (pos + 1 >= expression.length()) {
                                throw new VariableException(
                                        "Invalid expression :" + expression +
                                                ". Token: " + str + ". Expected variable"
                                );
                            } else if (Character.isDigit(expression.charAt(pos + 1))) {
                                pos++;
                                while (pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
                                    str.append(expression.charAt(pos));
                                    pos++;
                                }
                                pos--;
                                if (lastConst) {
                                    throw new OperationException(
                                            "Invalid expression :" + expression +
                                                    ". Token: " + str + ". Expected operation"
                                    );
                                }
                                lastConst = true;
                                yield LexemeType.CONST;
                            }
                            yield LexemeType.UNARY;
                        }
                        lastConst = false;
                        yield LexemeType.SUBTRACT;
                    }
                    case 'm' -> {
                        lastConst = false;
                        boolean invalidMinMax = !wasWhitespace && (lexemes.size() == 0 || lexemes.get(lexemes.size() - 1).getType() != LexemeType.RIGHT_ROUND);

                        if (pos + 2 < expression.length()
                                && expression.charAt(pos + 1) == 'i'
                                && expression.charAt(pos + 2) == 'n') {
                            str = new StringBuilder("min");
                            pos += 2;
                            if (invalidMinMax) {
                                throw new OperationException(
                                        "Invalid expression :" + expression + ". Token: " + str + ". Expected \")\" or space before operation"
                                );
                            }
                            yield LexemeType.MIN;
                        }
                        if (pos + 2 < expression.length()
                                && expression.charAt(pos + 1) == 'a'
                                && expression.charAt(pos + 2) == 'x') {
                            str = new StringBuilder("max");
                            pos += 2;
                            if (invalidMinMax) {
                                throw new OperationException(
                                        "Invalid expression :" + expression + ". Token: " + str + ". Expected \")\" or space before operation"
                                );
                            }
                            yield LexemeType.MAX;
                        }
                        for (int i = 0; i < list.size(); i++) {
                            if (expression.substring(pos).startsWith(list.get(i))) {
                                numberVariable = i;
                                str = new StringBuilder(list.get(i));
                                pos += list.get(i).length() - 1;
                                yield LexemeType.VARIABLE;
                            }
                        }
                        throw new OperationException(
                                "Invalid expression :" + expression +
                                        ". Token: " + str + " in " + lexemes.getPosition() + " position"
                        );
                    }
                    case '&' -> LexemeType.AND;
                    case '^' -> LexemeType.XOR;
                    case '|' -> LexemeType.OR;
                    case 'l' -> {
                        boolean bits = pos + 1 < expression.length() && expression.charAt(pos + 1) == '1';
                        if (bits) {
                            pos++;
                            str.append("1");
                            yield LexemeType.LOnes;
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                if (expression.substring(pos).startsWith(list.get(i))) {
                                    numberVariable = i;
                                    str = new StringBuilder(list.get(i));
                                    pos += list.get(i).length() - 1;
                                    yield LexemeType.VARIABLE;
                                }
                            }
                            throw new VariableException(
                                    "Invalid expression :" + expression + ". Token: " + "l" + expression.charAt(pos + 1)
                            );
                        }
                    }
                    case 't' -> {
                        boolean bits = pos + 1 < expression.length() && expression.charAt(pos + 1) == '1';
                        if (bits) {
                            pos++;
                            str.append("1");
                            yield LexemeType.TOnes;
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                if (expression.substring(pos).startsWith(list.get(i))) {
                                    numberVariable = i;
                                    str = new StringBuilder(list.get(i));
                                    pos += list.get(i).length() - 1;
                                    yield LexemeType.VARIABLE;
                                }
                            }
                            throw new VariableException(
                                    "Invalid expression :" + expression + ". Token: " + "l" + expression.charAt(pos + 1)
                            );
                        }
                    }
                    default -> {
                        for (int i = 0; i < list.size(); i++) {
                            if (expression.substring(pos).startsWith(list.get(i))) {
                                numberVariable = i;
                                str = new StringBuilder(list.get(i));
                                pos += list.get(i).length() - 1;
                                yield LexemeType.VARIABLE;
                            }
                        }
                        str = new StringBuilder();
                        if (Character.isDigit(expression.charAt(pos))) {
                            while (pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
                                str.append(expression.charAt(pos));
                                pos++;
                            }
                            pos--;
                            if (lastConst) {
                                throw new OperationException(
                                        "Invalid expression :" + expression + ". Token: " + expression.charAt(pos) +
                                                ". Expected operation."
                                );
                            }
                            lastConst = true;
                            yield LexemeType.CONST;
                        } else {
                            throw new OperationException(
                                    "Invalid expression :" + expression + ". Token: " + expression.charAt(pos)
                            );
                        }
                    }
                };
                lexemes.add(new Lexeme(type, str.toString(), numberVariable));
                wasWhitespace = false;
            } else {
                wasWhitespace = true;
            }
            pos++;
        }
        lexemes.add(new Lexeme(LexemeType.END, "", numberVariable));
        if (!parenthesis.isEmpty()) {
            throw new SyntaxException(
                    "Invalid expression :" + expression + ". Expected closing parenthesis"
            );
        }
        return lexemes;
    }
}
