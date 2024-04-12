package expression.exceptions;

public class OperationException extends MyExceptions {
    public OperationException(String massage) {
        super(massage);
    }
    public OperationException(){
        super();
    }
}