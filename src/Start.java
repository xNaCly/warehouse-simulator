import java.util.ArrayList;

public class Start {
    static String path;
    static boolean debug;
    static boolean silent;

    public static void main(String[] args){
        Start.parseArgs(args);
        Logger.debug = Start.debug;
        Logger.silent = Start.silent;

        Balance b = new Balance();
        Lager l = new Lager(b);

        Fs fs = new Fs(Start.path);
        ArrayList<Order> o = fs.parseOrders();
        l.update(o.get(0), 0, 0, 0);
        l.getSlot(0, 0, 0);
        l.update(o.get(1), 0, 0, 1);
        l.getSlot(0, 0, 1);
        // l.update(o.get(8), 0, 1, 0);
        // l.getSlot(0,1,0);
        // l.update(o.get(0), 0, 0, 0);
        // l.getSlot(0,0,0);
        // l.update(o.get(7), 0, 2, 0);
        // l.getSlot(0,2,0);
        Logger.inf("Balance: " + b.getBalance());
    }

    /**
     * parses the '-d' / '--debug' flag and the path to the .csv file which contains the orders
     * @param args arguments which are passed to the tool by the operating system
     */
    static void parseArgs(String[] args){
      try{
        Start.path = args[0];
        if(args[1].equals("-d") || args[1].equals("--debug")){
          Start.debug = true;
        } else if(args[1].equals("-s") || args[1].equals("--silent")){
            Start.silent = true;
        }
      } catch (ArrayIndexOutOfBoundsException e) {}
    }
}
