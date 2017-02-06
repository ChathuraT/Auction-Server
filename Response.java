
/**
 * Created by E/13/107
 * Gamage C.T.N
 * Lab 09 : Auction Server
 */


public class Response {
    // This class is Used for synchronization for the reading of bid values and updating the bids
    private boolean responseBoolean;
    private double responseInt;

    public boolean isResponseBoolean() {
        return responseBoolean;
    }

    public void setResponseBoolean(boolean responseBoolean) {
        this.responseBoolean = responseBoolean;
    }

    public double getResponseDouble() {
        return responseInt;
    }

    public void setResponseDouble(double responseInt) {
        this.responseInt = responseInt;
    }
}