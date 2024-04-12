package expression.parser;

public class Lexeme {
    private final LexemeType type;
    private final String str;
    private final int numberVariable;
    public Lexeme(LexemeType type, String str, int numberVariable) {
        this.type = type;
        this.str = str;
        this.numberVariable = numberVariable;
    }
    public LexemeType getType() {
        return type;
    }
    public String getValue() {
        return str;
    }
    public int getNumberVariable() {
        return numberVariable;
    }

}
