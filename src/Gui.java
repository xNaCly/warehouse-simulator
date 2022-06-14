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
    private JLabel currentOrder;
    private JFrame transactionsWindow;
    private int currentOpenOrders;
    private ArrayList<Order> o;
    private int currentOrderIndex;
    private boolean popOutActive;

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
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.r = new JFrame("Warehouse simulator");
        this.b = new Balance();
        this.l = new Storage(b);

        Fs fs = new Fs(Start.path);
        this.o = fs.parseOrders();
        this.start();
        setUIFont(new FontUIResource(new Font("Serif",Font.PLAIN,18)));
    }

    private void start(){
        this.r.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.r.setSize(600,600);
        this.r.setLayout(new BorderLayout());

        this.renderButtons();
        this.hud();

        this.r.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        this.r.setVisible(true);
    }

    private void popOutTransactions(){
        this.popOutActive = true;
        this.transactionsWindow = new JFrame("Warehouse simulator - Transactions");
        this.transactionsWindow.setSize(1024, 500);
        this.transactionsWindow.setVisible(true);

        String[] headerRow = {"Transaction ID", "Transaction"};
        String[][] data = new String[this.b.getTransactions().size()][2];
        ArrayList<String> transactions = this.b.getTransactions();

        for(int i = 0; i < transactions.size(); i++){
            String s = transactions.get(i);
            data[i][0] = ""+i;
            data[i][1] = s;
        }


        JTable jt = new JTable(data, headerRow);
        JScrollPane jp = new JScrollPane(jt);
        this.transactionsWindow.add(jp);

        // remove all Lables in transaction window after closing it
        this.transactionsWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                transactionsWindow.removeAll();
                transactionsWindow.setVisible(false);
                popOutActive = false;
                transactionsWindow.dispose();
            }
        });
    }

    private void hud(){
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        this.balanceLabel = new JLabel();
        this.currentOrder = new JLabel();
        this.rerenderHud();
        container.add(this.currentOrder, BorderLayout.WEST);
        container.add(this.balanceLabel, BorderLayout.EAST);
        this.r.add(container, BorderLayout.NORTH);
    }

    private void rerenderHud(){
        this.currentOrder.setText(this.o.get(this.currentOrderIndex).toString().replace(":", " "));
        this.balanceLabel.setText("Balance: " + this.b.getBalance() + "€");
    }

    private void renderMenu(){
        JPanel container = new JPanel();
        JButton fulFillOrder = new JButton("Auftrag erfüllen");
        fulFillOrder.setActionCommand("fulfill");
        fulFillOrder.setSize(250, 50);

        container.add(fulFillOrder);
        this.r.add(container);
    }

    private void renderButtons(){
        JPanel container = new JPanel();
        container.setLayout(new GridLayout());
        JButton transactionButton = new JButton("Transaktionsliste");
        JButton nextOrder = new JButton("Nächster Auftrag");
        JButton skipOrder = new JButton("Auftrag überspringen");

        transactionButton.setActionCommand("list");
        transactionButton.setSize(250, 50);


        nextOrder.setActionCommand("next");
        nextOrder.setSize(250, 50);

        skipOrder.setActionCommand("skip");
        skipOrder.setSize(250, 50);

        transactionButton.addActionListener(new ButtonClickListener());
        nextOrder.addActionListener(new ButtonClickListener());
        skipOrder.addActionListener(new ButtonClickListener());

        container.add(transactionButton);
        container.add(nextOrder);
        container.add(skipOrder);
        this.r.add(container, BorderLayout.SOUTH);
    }

    private Order getCurOrder(){
        return this.o.get(this.currentOrderIndex);
    }

    private void skipOrder(){
        Order curOrder = getCurOrder();
        this.currentOrderIndex++;
        if(this.currentOrderIndex >= this.o.size()){
            this.currentOrderIndex = 0;
        }
        this.b.updateBalance(curOrder.price, "Auftrag abgelehnt: -" + curOrder.price + "€", true);
        this.rerenderHud();
    }

    private void nextOrder(){
//        if(this.currentOpenOrders <= 3){
//            this.currentOrderIndex++;
//            this.currentOpenOrders++;
//        }
        if(this.currentOrderIndex >= this.o.size()){
            this.currentOrderIndex = 0;
        }
        this.rerenderHud();
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch(command){
                case "list" -> popOutTransactions();
                case "next" -> nextOrder();
                case "skip" -> skipOrder();
            }
        }
    }
}
