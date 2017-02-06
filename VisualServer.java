

/**
 * Created by E/13/107
 * Gamage C.T.N
 * Lab 09 : Auction Server
 */

import java.util.*;

class VisualServer extends MainServer {
    //This class coordinates the functions with the GUI

    public VisualServer(int socket, Stocks user) {
        super(socket, user);

    }

    public HashMap getBids() {

        return super.bids;
    }
}
