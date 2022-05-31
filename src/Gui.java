import javax.swing.*;
import java.util.ArrayList;

public class Gui extends JPanel {
    JFrame rootWin;
    JFrame childWin;
    Lager l; 
    Balance b;
    ArrayList<Order> o;
    ArrayList<String> os;
    String filepath;

    public Gui(String pathCSV) {
        this.rootWin = new JFrame("Warehouse");
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.rootWin.setSize(750, 750);
        this.rootWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.rootWin.setLocationRelativeTo(null);

        if(pathCSV == null){
            this.showError("Please specify an input file");
            System.exit(1);
        }

        this.filepath = pathCSV;
        this.b = new Balance();
        this.l = new Lager(this.b);
    }

    public void start(){
        this.rootWin.setVisible(true);
        this.loadOrders();
    }

    private void loadOrders(){
        Fs fs = new Fs(this.filepath);
        this.o = fs.parseOrders();
        this.os = new ArrayList<>();
        JLabel label = new JLabel();
        String orders = "</html><ul>\n";
        for(Order _o : this.o){
            orders += "<li>"+_o.toString()+"</li>\n";
        }
        orders += "</ul>\n</html>";
        System.out.println(orders);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.rootWin.add(label);
        label.setText(orders);
        this.rootWin.pack();
    }

    public void showError(String text){
        JOptionPane.showMessageDialog(null, text);
    }
}
