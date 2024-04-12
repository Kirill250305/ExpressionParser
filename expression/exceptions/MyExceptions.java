package expression.exceptions;

public class MyExceptions extends RuntimeException{

    public MyExceptions(String  massage) {
        super(massage);
    }
    public MyExceptions(){
        super();
    }
}
