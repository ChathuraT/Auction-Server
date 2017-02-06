

/**
 * Created by E/13/107
 * Gamage C.T.N
 * Lab 09 : Auction Server
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class MainServer {
    // This class contains basic Functions of the server

    public static final int BASE_PORT = 2000;

    private ServerSocket serverSocket = null;
    private Stocks items = null;
    public HashMap<String, String> bids = new HashMap<String, String>();

    public MainServer(int socket, Stocks items) {
        this.items = items;
        try {
            this.serverSocket = new ServerSocket(socket);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Checking whether a given symbol name is valid
    public boolean isAvailable(String symbol) {
        return this.items.findName(symbol) != null;
    }

    // Obtaining the Current BID Price of an item
    public synchronized double currentValue(String symbol) {
        return this.items.findName(symbol).price;
    }

    // This will validate the user's bid whether it is greater than the current value
    public synchronized boolean updatePrice(String symbol, double clientBid) {
        if (currentValue(symbol) < clientBid) {
            this.items.findName(symbol).price = clientBid;
            return true;
        }
        return false;
    }

    //A synchronized function to access the both read and write bid functions to avoid misbehaves
    public synchronized Response operation(String symbol, double clientBid) {
        Response response = new Response();
        if (clientBid == -1) {

            response.setResponseDouble(currentValue(symbol));

        } else {
            response.setResponseBoolean(updatePrice(symbol, clientBid));
        }
        return response;
    }

    // this function update the value of the item irrespective of any condition(used for server side updates)
   public synchronized void serverUpdatePrice(String symbol, double adminValue) {
        this.items.findName(symbol).price = adminValue;

    }

    // this function return the bid history of an item for the Search bid history purpose
    public synchronized String getName(String symbol) {
        return this.items.findName(symbol).name;
    }

    //This function updates the current value of an item to the client's value if it is valid
    public synchronized void storeBid(String client, String symbol, Double value) {

        if (this.bids.get(symbol) != null) {
            this.bids.put(symbol, this.bids.get(symbol) + client + " " + Double.toString(value) + ",");
        } else {
            this.bids.put(symbol, client + " " + Double.toString(value) + ",");

        }
    }

    // server loop for accepting new clients
    public void server_loop() {
        try {
            while (true) {
                Socket socket = this.serverSocket.accept();
                ConnectionServer worker = new ConnectionServer(this);
                worker.handleConnection(socket);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}







