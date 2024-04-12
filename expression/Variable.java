package expression;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Variable implements MyExpression {

    private final String str;
    private final int numberVariable;
    public Variable(String str) {
        this.str = str;
        this.numberVariable = -1;
    }
    public Variable(String str, int numberVariable) {
        this.str = str;
        this.numberVariable = numberVariable;
    }
    public Variable(int numberVariable) {
        this.numberVariable = numberVariable;
        this.str = String.valueOf(numberVariable);
    }
    @Override
    public int getPriority() {
        return 4;
    }
    @Override
    public int evaluate(int x) {
        return x;
    }
    @Override
    public BigInteger evaluate(BigInteger x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (str) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public String toMiniString() {
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(this.str, variable.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(numberVariable);
    }
}
