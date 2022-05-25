import java.util.ArrayList;

public class Start {
    public static void main(String[] args){
        Fs fs = new Fs("../Leistungsnachweis.csv");
        ArrayList<Order> orders = fs.parseOrders();
        Lager l = new Lager();
        for(Order o : orders)
            System.out.println(o);
    }
}
