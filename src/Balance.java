import java.util.ArrayList;

public class Balance {
    private int balance;
    private final ArrayList<String> transactions = new ArrayList<>();
    private int cost;
    private int income;

    /**
     * Balance manages a list of transactions and a balance
     */
    public Balance(){
        this.balance = 0;
    }

    /**
     * updates balance amount and transaction log depending on given parameters
     * @param amount amount in euro
     * @param log transaction info which will be stored in the transactions list
     * @param remove specify if the amount should be deducted or inducted
     */
    public void updateBalance(int amount, String log, boolean remove){
        transactions.add("("+ (transactions.size()+1) +") "+log);
        if(remove){
            this.balance -= amount;
            this.cost += amount;
        } else {
            this.balance += amount;
            this.income += amount;
        }
    }

    public int getBalance(){
        return this.balance;
    }

    public int getCost(){
        return this.cost;
    }

    public int getIncome(){
        return this.income;
    }

    public ArrayList<String> getTransactions(){
        return this.transactions;
    }
}
