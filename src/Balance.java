import java.util.ArrayList;

public class Balance {
    private int balance;
    private ArrayList<Order> transactions;

    public Balance(){
        this.balance = 0;
    }

    public void updateBalance(Order o, TypeOfTransaction typeOfTransaction){
        transactions.add(o);
    }

    public int getBalance(){
        return this.balance;
    }
}
