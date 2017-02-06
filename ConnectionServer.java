

/**
 * Created by E/13/107
 * Gamage C.T.N
 * Lab 09 : Auction Server
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class ConnectionServer implements Runnable {
    // This is where a client is managed after welcoming his/her connection

    // state handlers
    public static final int WELCOME = 0;
    public static final int AUTH_DONE = 1;
    public static final int SYMBOL_SELECTED = 2;

    public static final String SELECT_SYMBOL = "please enter the symbol \n";
    public static final String CURRENT_VALUE = "The current value of the item ";

    private Socket mySocket;
    private int currentState;
    private String clientName;
    private String symbolSelected;
    private double clientBid;
    private MainServer mainServer;


    public ConnectionServer(MainServer mainServer) {
        this.mySocket = null;
        this.currentState = WELCOME;
        this.clientName = null;
        this.mainServer = mainServer;

    }

    public boolean handleConnection(Socket socket) {
        this.mySocket = socket;
        Thread newThread = new Thread(this);
        newThread.start();
        return true;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new
                    BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            out = new
                    PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));

            String line, outline;
            for (line = in.readLine();
                 line != null && !line.equals("quit");
                 line = in.readLine()) {

                switch (currentState) {

                    case WELCOME:
                        //Initially user needs to enter his/her name
                        if (line != null) {
                            currentState = AUTH_DONE;
                            clientName = line;
                            out.println("Hello " + clientName + "!");
                            outline = SELECT_SYMBOL;
                            break;
                        } else {
                            outline = "Enter a valid Name";
                            break;
                        }

                    case AUTH_DONE:
                        // Prompts the user to enter the symbol he/she needs to bid on
                        symbolSelected = line;
                        if (mainServer.isAvailable(line)) {
                            currentState = SYMBOL_SELECTED;
                            out.println(CURRENT_VALUE + symbolSelected + " is " + mainServer.operation(symbolSelected, -1).getResponseDouble() + "\n");
                            outline = "Enter your Bid\n";
                            break;

                        } else {
                            out.println("-1");
                            outline = "Please Enter a correct Symbol \n";
                            break;
                        }

                    case SYMBOL_SELECTED:
                        // User is in a state which he/she can keep on bidding in the selected item
                        try {
                            clientBid = Double.parseDouble(line);
                            if (mainServer.operation(symbolSelected, clientBid).isResponseBoolean()) {
                                mainServer.storeBid(this.clientName, symbolSelected, clientBid);
                                out.println("Your Bid Placed Successfully\n");
                            } else
                                out.println("Value entered should be greater than current value\n");

                        } catch (NumberFormatException ex) {
                            out.println("Value entered is not valid\n");
                        }

                        outline = "Enter your next bid value \n";
                        break;

                    default:
                        System.out.println("Undefined state");
                        return;
                }

                out.print(outline);
                out.flush();

            }

            out.close();
            in.close();
            this.mySocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}




