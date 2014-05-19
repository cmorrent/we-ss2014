package HighsoreService;

/**
 * Created by willi on 5/19/14.
 */
public class InvalidUserDataException extends Exception{

    public InvalidUserDataException(){
        super();
    }


    public InvalidUserDataException(Throwable cause){
        super(cause);
    }


    public InvalidUserDataException(String msg){
        super(msg);
    }


    public InvalidUserDataException(String msg, Throwable cause){
        super(msg, cause);
    }

}
