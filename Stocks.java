
/**
 * Created by E/13/107
 * Gamage C.T.N
 * Lab 09 : Auction Server
 */
import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import java.io.*;
import java.util.*;

public class Stocks {


    private Map<String, Structure> stock;
    private String [] fields;

    public Stocks(String cvsFile, String key, String name, String price)  {
        FileReader fileRd=null;
        BufferedReader reader=null;

        try {
            fileRd = new FileReader(cvsFile);
            reader = new BufferedReader(fileRd);

        /* read the CSV file's first line which has
         * the names of fields.
         */
            String header = reader.readLine();
            fields = header.split(",");// keep field names

            // find where the key and the value are
            int keyIndex = findIndexOf(key);
            int keyName = findIndexOf(name);
            int keyPrice = findIndexOf(price);

            if(keyIndex == -1 || keyName == -1 || keyPrice==-1)
                throw new IOException("CVS file does not have data");
            // note how you can throw a new exception

            // get a new hash map
            stock = new HashMap<String, Structure>();

        /* read each line, getting it split by ,
         * use the indexes to get the key and value
         */
            String [] tokens;
            for(String line = reader.readLine();
                line != null;
                line = reader.readLine()) {
                tokens = line.split(",");
                stock.put(tokens[keyIndex], new Structure(tokens[keyName], Double.parseDouble(tokens[keyPrice])));
            }

            if(fileRd != null) fileRd.close();
            if(reader != null) reader.close();

            // System.out.println(stock.get("ABCW").name+" "+stock.get("ABCW").price);
            // I can catch more than one exceptions
        } catch (IOException e) {
            System.out.println(e);
            System.exit(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Malformed CSV file");
            System.out.println(e);
        }
    }

    private int findIndexOf(String key) {
        for(int i=0; i < fields.length; i++)
            if(fields[i].equals(key)) return i;
        return -1;
    }


    // public interface
    public Structure findName(String key) {
        return stock.get(key);
    }

}
