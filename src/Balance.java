import java.util.ArrayList;

public class Balance {
    private int balance;
    private ArrayList<String> transactions = new ArrayList<>();

    public Balance(){
        this.balance = 0;
    }

    public void updateBalance(int amount, String log, boolean remove){
        transactions.add(log);
        if(remove) this.balance -= amount;
        else this.balance += amount;
    }

    public int getBalance(){
        return this.balance;
    }

    public ArrayList<String> getTransactions(){
        return this.transactions;
    }
}
