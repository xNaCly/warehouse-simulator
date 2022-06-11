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
        this.start();
    }

    private void start(){
        this.r.setSize(400, 400);
        this.r.setVisible(true);
    }
}
