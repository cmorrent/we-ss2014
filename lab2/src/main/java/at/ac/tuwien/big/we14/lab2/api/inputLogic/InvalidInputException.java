package at.ac.tuwien.big.we14.lab2.api.inputLogic;

/**
 * Created by willi on 4/19/14.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(){
        super();
    }

    public InvalidInputException(String msg){
        super(msg);
    }

    public InvalidInputException(String msg, Throwable cause){
        super(msg, cause);
    }
}
