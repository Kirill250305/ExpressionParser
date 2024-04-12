package expression;

import expression.exceptions.CheckedDivide;

import java.util.Objects;

public abstract class Operations implements MyExpression {
    protected final MyExpression element1;
    protected MyExpression element2 = null;
    private final int priority;
    private final String operation;
    private final boolean associativity;

    public Operations(MyExpression element1, MyExpression element2, int priority, boolean associativity, String operation) {
        this.element1 = element1;
        this.element2 = element2;
        this.priority = priority;
        this.operation = operation;
        this.associativity = associativity;
    }
    public Operations(MyExpression element1, int priority, boolean associativity, String operation) {
        this.element1 = element1;
        this.priority = priority;
        this.operation = operation;
        this.associativity = associativity;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "(" + element1.toString() + " " + operation + " " + element2.toString() + ")";
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        if (element1.getPriority() < priority) {
            sb.append("(").append(element1.toMiniString()).append(")");
        } else {
            sb.append(element1.toMiniString());
        }
        sb.append(" ").append(operation).append(" ");
        if (element2.getPriority() == priority &&
                (!associativity
                        || element2.getClass() == Divide.class
                        || element2.getClass() == CheckedDivide.class
                        || (element2.getClass() == Min.class
                        || element2.getClass() == Max.class) && element2.getClass() != this.getClass())
                || element2.getPriority() < priority) {
            sb.append("(").append(element2.toMiniString()).append(")");
        } else {
            sb.append(element2.toMiniString());
        }
        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operations operations = (Operations) o;
        return Objects.equals(element1, operations.element1) && Objects.equals(element2, operations.element2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element1, element2, this.getClass());
    }
}
