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
    private boolean rearrangeMode;
    private boolean recycleMode;
    private int[] rearrangeSlot = {-1, -1, -1, -1};
    private boolean orderFulfilled;

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
        this.r.setSize(1024,512);
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
        if(this.popOutActive){
            if(this.transactionsWindow != null){
                this.transactionsWindow.dispose();
                this.popOutActive = false;
            }
        }
    }

    private void popOutTransactions(){
        if(this.popOutActive) return;
        this.transactionsWindow = new JFrame("Warehouse simulator - Transactions");
        this.transactionsWindow.setSize(1024, 500);

        String[] headerRow = {"Transaktions ID", "Transaktion"};
        String[][] data = new String[this.b.getTransactions().size()][2];
        ArrayList<String> transactions = this.b.getTransactions();

        for(int i = 0; i < transactions.size(); i++){
            String s = transactions.get(i);
            data[i][0] = ""+i;
            data[i][1] = s;
        }

        JTable jt = new JTable(data, headerRow);
        JScrollPane jp = new JScrollPane(jt);
        JPanel container = new JPanel();
        JLabel incomeLabel = new JLabel("Umsätze: " + this.b.getIncome());
        JLabel costLabel = new JLabel("Kosten: " + this.b.getCost());
        container.add(incomeLabel);
        container.add(costLabel);
        this.transactionsWindow.add(jp, BorderLayout.NORTH);
        this.transactionsWindow.add(container, BorderLayout.SOUTH);

        // remove all Lables in transaction window after closing it
        this.transactionsWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                destroyTransactionsWindow();
            }
        });

        this.popOutActive = true;
        this.transactionsWindow.setVisible(true);
    }

    private void hud(){
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        this.balanceLabel = new JLabel();
        this.currentOrder = new JLabel();
        container.add(this.currentOrder, BorderLayout.EAST);
        container.add(this.balanceLabel, BorderLayout.WEST);
        this.r.add(container, BorderLayout.NORTH);
        this.rerenderHud();
    }

    private void rerenderHud(){
        try{
            if(this.orderFulfilled) this.currentOrder.setText("Kein aktiver Auftrag");
            else this.currentOrder.setText(this.o.get(this.currentOrderIndex).toString().replace(":", " "));
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
        JCheckBox rearrangeBox = new JCheckBox("Verschiebe Modus");
        JCheckBox recycleBox = new JCheckBox("Verschrotten Modus");
        JButton transactionButton = new JButton("Bilanz");
        JButton skipOrder = new JButton("Auftrag ablehnen");
        JButton nextOrder = new JButton("Neuer Auftrag");

        rearrangeBox.setActionCommand("move");
        rearrangeBox.setSize(250, 50);

        recycleBox.setActionCommand("recycle");
        recycleBox.setSize(250, 50);

        transactionButton.setActionCommand("list");
        transactionButton.setSize(250, 50);

        nextOrder.setActionCommand("next");
        nextOrder.setSize(250, 50);

        skipOrder.setActionCommand("skip");
        skipOrder.setSize(250, 50);

        transactionButton.addActionListener(new ButtonClickListener());
        skipOrder.addActionListener(new ButtonClickListener());
        rearrangeBox.addActionListener(new ButtonClickListener());
        recycleBox.addActionListener(new ButtonClickListener());
        nextOrder.addActionListener(new ButtonClickListener());

        container.add(transactionButton);
        container.add(skipOrder);
        container.add(nextOrder);
        container.add(rearrangeBox);
        container.add(recycleBox);

        this.r.add(container, BorderLayout.SOUTH);
    }

    private Order getCurOrder(){
        return this.o.get(this.currentOrderIndex);
    }

    private void rearrangeSlot(int x, int y, int z, int i){
        if(rearrangeSlot[0] == -1 && rearrangeSlot[1] == -1 && rearrangeSlot[2] == -1){
            rearrangeSlot = new int[]{x,y,z,i};
        } else {
            boolean feedback = l.rearrange(rearrangeSlot[0], rearrangeSlot[1], rearrangeSlot[2], x, y, z);
            if(!feedback) return;
            Logger.debug("test: "+ rearrangeSlot[3]);
            this.slots[rearrangeSlot[3]].setText(String.format("slot:%d_%d_%d", rearrangeSlot[2], rearrangeSlot[1], rearrangeSlot[0]));
            this.slots[i].setText(this.l.getSlot(x,y,z).getNameAndProperties());
            rearrangeSlot = new int[]{-1,-1,-1,-1};
        }
    }

    private void recycleSlot(int x, int y, int z, int i){
        JButton slot = this.slots[i];
        String prod = slot.getText();

        if(prod.contains("BALKEN")){
            int otherZ = z == 0 ? 1 : 0;
            this.slots[z == 0 ? i+12 : i-12].setText(String.format("slot:%d_%d_%d", otherZ, y, x));
        }

        this.l.recycle(x, y, z);
        slot.setText(String.format("slot:%d_%d_%d", z, y, x));
        this.rerenderHud();
        if(this.popOutActive){
            this.destroyTransactionsWindow();
            this.popOutTransactions();
        }
    }

    private void fulFillOrder(int x, int y, int z, int index){
        if(this.orderFulfilled){
            Logger.err("Kein aktiver Auftrag");
            return;
        }
        Order _o = this.getCurOrder();
        boolean f = this.l.update(_o, x, y, z);
        if(f){
            int otherZ = z == 0 ? 1 : 0;
            if(_o.insertOrder){
                this.slots[index].setText(this.l.getSlot(x,y,z).getNameAndProperties());
                if(_o.product.getNameAndProperty().equalsIgnoreCase("holz:balken")){
                    this.slots[z == 0 ? index+12 : index-12].setText(this.l.getSlot(x,y,otherZ).getNameAndProperties());
                }
            } 
            else {
                this.slots[index].setText(String.format("slot:%d_%d_%d", z, y, x));
                if(_o.product.getNameAndProperty().equalsIgnoreCase("holz:balken")){
                    this.slots[z == 0 ? index+12 : index-12].setText(String.format("slot:%d_%d_%d", otherZ, y, x));
                }
            }
            this.orderFulfilled = true;
        }
        this.rerenderHud();
        if(this.popOutActive){
            this.destroyTransactionsWindow();
            this.popOutTransactions();
        }
    }

    private void skipOrder(){
        if(this.orderFulfilled){
            Logger.err("Kein aktiver Auftrag");
            return;
        }
        Order curOrder = this.getCurOrder();
        if(this.currentOrderIndex == this.o.size()-1) this.currentOrderIndex = 0;
        else this.currentOrderIndex++;
        this.b.updateBalance(curOrder.price, "Auftrag abgelehnt: -" + curOrder.price + "€", true);
        this.rerenderHud();
        if(this.popOutActive){
            this.destroyTransactionsWindow();
            this.popOutTransactions();
        }
    }

    private void nextOrder(){
        if(this.orderFulfilled){
            if(this.currentOrderIndex == this.o.size()-1) this.currentOrderIndex = 0;
            else this.currentOrderIndex++;
            this.orderFulfilled = false;
            this.rerenderHud();
        } else {
            Logger.err("Auftrag muss erfüllt oder übersprungen werden, bevor ein neuer Auftrag angefordert werden kann.");
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            Logger.debug("Event: "+command);
            switch(command){
                case "list" -> popOutTransactions();
                case "skip" -> skipOrder();
                case "next" -> nextOrder();
                case "move" -> {
                    rearrangeMode = rearrangeMode ? false : true;
                    rearrangeSlot = new int[]{-1,-1,-1,-1};
                }
                case "recycle" -> recycleMode = recycleMode ? false : true;
                default -> {
                    if(command.startsWith("slot:")){
                        int x = -1;
                        int y = -1;
                        int z = -1;
                        int i = -1;

                        try {
                            String[] coords = command.split(":")[1].split("_");
                            z = Integer.parseInt(coords[0]);
                            y = Integer.parseInt(coords[1]);
                            x = Integer.parseInt(coords[2]);
                            i = Integer.parseInt(coords[3]);
                        } catch (ArrayIndexOutOfBoundsException ignored){}

                        if(recycleMode){
                            recycleSlot(x, y, z, i);
                        } else if(rearrangeMode){
                            rearrangeSlot(x, y, z, i);
                        } else {
                            fulFillOrder(x, y, z, i);
                        }
                    }
                }
            }
        }
    }
}
