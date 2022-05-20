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

    private boolean isFile(){
        return this.file.exists() && this.file.canRead();
    }

    private ArrayList<String> getLines(){
        ArrayList<String> lines = new ArrayList<>();
        if(this.isFile()){
            try{
                Scanner sc = new Scanner(this.file);
                while(sc.hasNextLine()){
                    lines.add(sc.nextLine());
                }
            } catch (FileNotFoundException e){
                System.out.printf("File %s not found\n", this.file.getName());
                e.printStackTrace();
            }
        }
        return lines;
    }

    /**
     * Parses file passed to the Fs initialiser. The parser skips orders with an unknown product type.
     * @return parsed ArrayList of Orders
     */
    public ArrayList<Order> parseCSV(){
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<String> lines = this.getLines();

        lines.remove(0); // remove header row

        for(String i : lines){
            String[] sl = i.split(";");

            // convert csv data to correct types
            int index = Integer.parseInt(sl[0]);
            boolean orderType = Objects.equals(sl[1], "Einlagerung");
            Product p;

            if(Objects.equals(sl[2].toLowerCase(), "papier")){
                p = new Papier("Papier", sl[3], sl[4]);
            } else if(Objects.equals(sl[2].toLowerCase(), "stein")){
                p = new Stein("Stein", sl[3], sl[4]);
            } else if(Objects.equals(sl[2].toLowerCase(), "holz")){
                p = new Holz("Holz", sl[3], sl[4]);
            } else {
                // !this skips orders with no known Product.Product type!
                continue;
            }

            int price = Integer.parseInt(sl[5]);

            Order o = new Order(index, orderType, p, price);
            orders.add(o);
        }
        return orders;
    }
}
