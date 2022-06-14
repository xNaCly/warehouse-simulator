import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class Gui {
    private JFrame r; 
    private Balance b;
    private Storage l;
    private JLabel balanceLabel;
    private JFrame transactionsWindow;

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    public Gui() {
        this.r = new JFrame("Warehouse simulator");
        this.b = new Balance();
        this.l = new Storage(b);

        Fs fs = new Fs(Start.path);
        ArrayList<Order> o = fs.parseOrders();
        this.l.update(o.get(0), 0, 0, 0);
        this.l.recycle(0,0,0);
        this.start();
        setUIFont(new FontUIResource(new Font("Serif",Font.PLAIN,18)));
    }

    private void start(){
        this.r.setSize(400,500);

        // stop running after gui is closed, if this isn't specified the process keeps running in the terminal
        this.r.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.displayTransactionsButton();
        this.displayBalance();

        this.r.setVisible(true);
        this.r.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }

    private void popOutTransactions(){
        this.transactionsWindow = new JFrame("Warehouse simulator - Transactions");
        String[] headerRow = {"Transaction ID", "Transaction"};
        String[][] data = new String[this.b.getTransactions().size()][2];
        ArrayList<String> transactions = this.b.getTransactions();

        for(int i = 0; i < transactions.size(); i++){
            String s = transactions.get(i);
            Logger.debug(s);
            data[i][0] = ""+i;
            data[i][1] = s;
        }

        JTable table = new JTable(data, headerRow);
        JScrollPane sp = new JScrollPane(table);

        this.transactionsWindow.add(sp);
        this.transactionsWindow.setSize(1024, 500);
        this.transactionsWindow.setVisible(true);
        // remove all Lables in transaction window after closing it
        this.transactionsWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                transactionsWindow.removeAll();
            }
        });
    }

    private void displayBalance(){
        this.balanceLabel = new JLabel("Balance: " + this.b.getBalance() + "€");
        this.r.add(balanceLabel);
    }

    private void displayTransactionsButton(){
        JButton b = new JButton("View Transactions");
        b.setActionCommand("View Transactions");
        b.addActionListener(new ButtonClickListener());
        b.setSize(250, 50);
        this.r.add(b);
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if(command.equals("View Transactions"))
                popOutTransactions();
        }
    }
}
