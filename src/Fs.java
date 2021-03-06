/**
 * @author xnacly
 * Fs - filesystem interaction
 */
import product.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Fs {
    final File file;

    /**
     * exposes csv parsing functionality
     */
    public Fs(String path){
        this.file = new File(path);
    }

    /**
     * checks if a file exists and is readable 
     * @return boolean 
     */
    public boolean isFile(){
        return this.file.exists() && this.file.canRead();
    }

    /**
     * parse file.csv contents into Orders arraylist
     * @return ArrayList<Order>
     */
    public ArrayList<Order> parseOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        if(this.isFile()){
            try{
                Scanner sc = new Scanner(this.file);
                int i = 0;
                while(sc.hasNextLine()){
                    String line = sc.nextLine();
                    if(line.startsWith("Auftrag")) continue;
                    Order o = this.parseCSV(line);
                    orders.add(o);
                    Logger.debug("parsed item (" + i + ") "+ o + " from file");
                    i++;
                }
                sc.close();
            } catch (FileNotFoundException e){
                Logger.err("File '" + this.file.getName() + "' not found or not a file");
                e.printStackTrace();
            }
        } else {
            Logger.err("File '" + this.file.getName() + "' not found or not a file");
        }
        Logger.inf("Loaded and parsed " + orders.size() + " Products");
        return orders;
    }

    /**
     * Parses line separated by ';' into Order datastructure
     * @return parsed Order
     */
    private Order parseCSV(String line){
        String[] sl = line.split(";");

        // convert csv data to correct types
        int index = Integer.parseInt(sl[0]);
        boolean orderType = Objects.equals(sl[1], "Einlagerung");
        Product p;

        switch (sl[2].toLowerCase()) {
            case "papier" -> p = new Paper(sl[3], sl[4]);
            case "stein" -> p = new Stone(sl[3], sl[4]);
            case "holz" -> p = new Wood(sl[3], sl[4]);
            default -> {
                p = new Product();
                p.setName("empty");
                Logger.err("Product at index: " + (index + 1) + " is unknown");
            }
        }

        int price = Integer.parseInt(sl[5]);

        return new Order(index, orderType, p, price);
    }
}
