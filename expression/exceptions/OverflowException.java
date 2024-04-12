package expression.exceptions;

public class OverflowException extends MyExceptions {
    public OverflowException(String massage) {
        super(massage);
    }
    public OverflowException(){
        super();
    }
}
