

/**
 * Created by E/13/107
 * Gamage C.T.N
 * Lab 09 : Auction Server
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;


public class Search extends JPanel implements ActionListener {
    //This will create the layout for the Bid history area and update price area
    VisualServer server;

    public JButton search;
    public JButton update;
    public JTextField field;
    public JTextField value;
    public JTextArea textArea;

    public Search(VisualServer server) {
        super(new GridBagLayout());
        setBackground(new Color(100, 100, 100));

        GridBagConstraints c = new GridBagConstraints();


        search = new JButton("Search Bid history");
        update = new JButton("Update Price");
        textArea = new JTextArea("Start Searching the Bid History...");
        field = new JTextField();
        value = new JTextField();


        search.setEnabled(true);
        search.addActionListener(this);
        search.setBackground(new Color(210, 210, 210));

        update.setEnabled(true);
        update.addActionListener(this);
        update.setBackground(new Color(210, 210, 210));

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Calibri", Font.BOLD, 16));
        textArea.setBackground(new Color(70, 70, 70));
        textArea.setForeground(new Color(200, 200, 200));


        value.setBackground(new Color(240, 240, 240));
        field.setBackground(new Color(240, 240, 240));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(-120, 100, 0, 0);
        add(field, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(-50, 100, 0, 0);
        add(search, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(50, 100, 0, 0);
        add(value, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(120, 100, 0, 0);
        add(update, c);

        JScrollPane scrollPane = new JScrollPane(textArea);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.ipady = 0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridwidth = 20;
        c.gridy = 2;
        add(scrollPane, c);

        this.server = server;

    }

       @Override
    public void actionPerformed(ActionEvent e) {

        // this will listen to the Search button click
        if (e.getSource() == search) {

            String history = (String) server.getBids().get(field.getText());
            if (!server.isAvailable(field.getText())) {
                textArea.setText("Input Symbol is Wrong !");
                return;
            }

            textArea.setText("");
            if (history != null) {
                try {
                    String[] bidArray = history.split(",");
                    textArea.append("Bid History for " + field.getText() + " : " + server.getName(field.getText()) + "\n\n");

                    for (int i = 0; i < bidArray.length; i++)
                        textArea.append((i + 1) + ")  " + bidArray[i] + "\n");

                } catch (PatternSyntaxException ex) {
                    textArea.setText("Internal Server Error!\n");
                }
            } else
                textArea.setText("No bids available for " + field.getText() + " yet! \nInitial Price is " + server.currentValue(field.getText()));

        }
        if (e.getSource() == update) {
            if (!server.isAvailable(field.getText())) {
                textArea.setText("Input Symbol is Wrong !");
                return;
            }

            try {
                server.serverUpdatePrice(field.getText(), Double.parseDouble(value.getText()));
                server.storeBid("Server Update ", field.getText(), Double.parseDouble(value.getText()));
                textArea.setText("Success! The Value of "+ field.getText()+" updated for "+server.currentValue(field.getText()));

            } catch (Exception ex) {
                textArea.setText("Please Enter a valid value!");
            }

        }
    }
}
