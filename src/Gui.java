import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class Gui {
    private final JFrame r;
    private final Balance b;
    private final Storage l;
    private JLabel balanceLabel;
    private JLabel currentOrder;
    private JFrame transactionsWindow;
    private final ArrayList<Order> o;
    private int currentOrderIndex;
    private boolean popOutActive;
    private JButton[] slots = new JButton[24];

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        Enumeration<Object> keys = UIManager.getDefaults().keys();
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
        Logger.root = this.r;
    }

    private void start(){
        this.r.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.r.setSize(600,600);
        this.r.setLayout(new BorderLayout());

        this.renderButtons();
        this.hud();
        this.renderSlots();

        this.r.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        this.r.setVisible(true);
    }

    private void destroyTransactionsWindow(){
        if(this.transactionsWindow != null){
            transactionsWindow.removeAll();
            transactionsWindow.setVisible(false);
            popOutActive = false;
            transactionsWindow.dispose();
        }
    }

    private void popOutTransactions(){
        if(this.popOutActive) return;
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
                destroyTransactionsWindow();
            }
        });
        this.popOutActive = true;
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
        try{
            this.currentOrder.setText(this.o.get(this.currentOrderIndex).toString().replace(":", " "));
        } catch (IndexOutOfBoundsException ignored){}
        this.balanceLabel.setText("Balance: " + this.b.getBalance() + "€");
    }

    private void renderSlots(){
        JPanel jp = new JPanel();
        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(3, 4));
        JPanel jp2 = new JPanel();
        jp2.setLayout(new GridLayout(3, 4));
        for(int i = 0, z = 0; z < 2; z++){
            for(int y = 0; y < 3; y++){
                for(int x = 0; x < 4; x++){
                    String s = String.format("slot:%d_%d_%d_%d", z,y,x,i);
                    JButton jb = new JButton(s.substring(0, 10));
                    jb.setActionCommand(s);
                    jb.addActionListener(new ButtonClickListener());
                    Logger.debug("new "+s);
                    jb.setSize(100, 100);

                    if(z == 0) jp1.add(jb);
                    else jp2.add(jb);

                    this.slots[i] = jb;
                    i++;
                }
            }
        }
        jp.add(jp1);
        jp.add(jp2);
        this.r.add(jp);
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

    private void fulFillOrder(int x, int y, int z, int index){
        Order _o = this.getCurOrder();
        boolean f = this.l.update(_o, x, y, z);
        if(f){
            if(_o.insertOrder) this.slots[index].setText(this.l.getSlot(x,y,z).getNameAndProperties());
            else this.slots[index].setText(String.format("slot:%d_%d_%d", z, y, x));
            if(this.currentOrderIndex == this.o.size()-1) this.currentOrderIndex = 0;
            else this.currentOrderIndex++;
        }
        this.rerenderHud();
        Logger.debug(""+this.currentOrderIndex);
    }

    private void skipOrder(){
        Order curOrder = this.getCurOrder();
        this.currentOrderIndex++;
        if(this.currentOrderIndex >= this.o.size()){
            this.currentOrderIndex = 0;
        }
        this.b.updateBalance(curOrder.price, "Auftrag abgelehnt: -" + curOrder.price + "€", true);
        this.rerenderHud();
        if(this.popOutActive){
            this.destroyTransactionsWindow();
            this.popOutTransactions();
        }
    }

    private void nextOrder(){
        if(this.currentOrderIndex >= this.o.size()){
            this.currentOrderIndex = 0;
        }
        this.currentOrderIndex++;
        this.rerenderHud();
        if(this.popOutActive){
            this.destroyTransactionsWindow();
            this.popOutTransactions();
        }
    }

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            Logger.debug("Event: "+command);
            switch(command){
                case "list" -> popOutTransactions();
                case "next" -> nextOrder();
                case "skip" -> skipOrder();
                default -> {
                    if(command.startsWith("slot:")){
                        try {
                            String[] coords = command.split(":")[1].split("_");
                            int z = Integer.parseInt(coords[0]);
                            int y = Integer.parseInt(coords[1]);
                            int x = Integer.parseInt(coords[2]);
                            int index = Integer.parseInt(coords[3]);
                            fulFillOrder(x,y,z, index);
                        } catch (ArrayIndexOutOfBoundsException ignored){}
                    }
                }
            }
        }
    }
}
