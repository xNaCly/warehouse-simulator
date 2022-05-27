import java.util.ArrayList;

public class Start {
    public static void main(String[] args){
        Fs fs = new Fs("../Leistungsnachweis.csv");
        ArrayList<Order> orders = fs.parseOrders();
        Balance balance = new Balance();
        Lager l = new Lager(balance);
        l.insert(orders.get(0), 0);
        System.out.println(balance.getBalance());
        l.remove(orders.get(0), 0);
        System.out.println(balance.getBalance());
    }
}
