
/**
 * Created by E/13/107
 * Gamage C.T.N
 * Lab 09 : Auction Server
 */

import java.awt.*;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import java.io.IOException;


public class AuctionServer extends JPanel implements ActionListener {
    // This is the main class which will generate the gui and functions

    VisualServer server;
    public JButton buttons[] = new JButton[8];
    public String dispSymbols[] = {"FB", "VRTU", "MSFT", "GOOGL", "YHOO", "XLNX", "TSLA", "TXN"};


    public AuctionServer(VisualServer server) {
        super(new GridLayout(4, 2));


        for (int i = 0; i < buttons.length; i++) {

            buttons[i] = new JButton("");
            buttons[i].setEnabled(false);
            add(buttons[i]);
            buttons[i].addActionListener(this);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 15));
            buttons[i].setHorizontalAlignment(SwingConstants.LEFT);
            buttons[i].setBackground(new Color(0, 0, 0));


        }


        Timer timer = new Timer(500, this);
        timer.start();

        this.server = server;
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < dispSymbols.length; i++) {
            buttons[i].setText("<html><b > (" + dispSymbols[i] + ")</b> " + server.getName(dispSymbols[i]) + "<br /><b>Current Price : " + Double.toString(server.operation(dispSymbols[i], -1).getResponseDouble()) + "</b></html>");

        }

    }

    public static void main(String[] args) throws IOException {

        //Create and set up the window.
        JFrame frame = new JFrame("Auction Server");

       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); (Keeps the server on even if the gui is closed)


        // sever and Stock Database
        Stocks allowedUsers = new Stocks("stocks.csv", "Symbol", "Security Name", "Price ");
        VisualServer server = new VisualServer(MainServer.BASE_PORT, allowedUsers);

        //Add contents to the window.
        frame.setLayout(new GridLayout(1, 1));
        frame.add(new AuctionServer(server), BorderLayout.NORTH);
        frame.add(new Search(server), BorderLayout.SOUTH);

        //Display the window.
        frame.setSize(700, 500);
        frame.setVisible(true);

        server.server_loop();
    }


}



