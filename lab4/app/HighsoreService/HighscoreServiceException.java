package HighsoreService;

/**
 * Created by willi on 5/18/14.
 */
public class HighscoreServiceException extends Exception {

    private int faultCode;

    private String reason;

    public HighscoreServiceException(){
        super();
    }

    public HighscoreServiceException(Throwable cause){
        super(cause);
    }

    public HighscoreServiceException(String msg){
        super(msg);
    }

    public HighscoreServiceException(String msg, Throwable cause){
        super(msg, cause);
    }

    public HighscoreServiceException(String msg, String reason, int faultCode){
        super(msg);
        this.faultCode = faultCode;
        this.reason = reason;
    }

    public int getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(int faultCode) {
        this.faultCode = faultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
