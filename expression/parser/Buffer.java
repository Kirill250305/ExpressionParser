package expression.parser;

import java.util.ArrayList;

public class Buffer {
    private final ArrayList<Lexeme> lexemes = new ArrayList<>();
    private int position = -1;
    public boolean end() {
        return position >= lexemes.size();
    }
    public void add(Lexeme lexeme) {
        lexemes.add(lexeme);
    }

    public Lexeme next() {
        position++;
        return lexemes.get(position);
    }
    public int size() {
        return lexemes.size();
    }
    public void back() {
        position--;
    }
    public int getPosition() {
        return position;
    }
    public Lexeme get(int n) {
        return lexemes.get(n);
    }
}
