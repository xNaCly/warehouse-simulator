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
    File file;

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
                while(sc.hasNextLine()){
                    String line = sc.nextLine();
                    if(line.startsWith("Auftrag")) continue;
                    Order o = this.parseCSV(line);
                    orders.add(o);
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

        if(Objects.equals(sl[2].toLowerCase(), "papier")){
            p = new Papier(sl[3], sl[4]);
        } else if(Objects.equals(sl[2].toLowerCase(), "stein")){
            p = new Stein(sl[3], sl[4]);
        } else if(Objects.equals(sl[2].toLowerCase(), "holz")){
            p = new Holz(sl[3], sl[4]);
        } else {
            p = new Product();
            p.setName("Unknown");
            Logger.err("Product at index: " + (index+1) + " is unknown");
        }

        int price = Integer.parseInt(sl[5]);

        Order o = new Order(index, orderType, p, price);
        return o;
    }
}
