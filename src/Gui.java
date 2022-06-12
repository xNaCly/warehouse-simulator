import java.util.ArrayList;
import javax.swing.*;

public class Gui {
    private JFrame r; 
    private Balance b;
    private Lager l;

    public Gui() {
        this.r = new JFrame("Warehouse simulator");
        this.b = new Balance();
        this.l = new Lager(b);

        Fs fs = new Fs(Start.path);
        ArrayList<Order> o = fs.parseOrders();
        this.l.update(o.get(1), 0, 0, 0);
        this.l.getSlot(0,0,0);
        this.l.update(o.get(27), 0, 0, 0);
        Logger.debug("Balance: " + this.b.getBalance(), 19, "Gui.java");
        for(String s : this.b.getTransactions())
            System.out.println(s);
        // this.start();
    }

    private void start(){
        this.r.setSize(400, 400);
        this.r.setVisible(true);
    }
}
