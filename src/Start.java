import java.util.Scanner;
import java.util.ArrayList;

public class Start {
    static String path;
    static boolean debug;
    static boolean silent;
    static boolean headless;
    static boolean test;

    public static void main(String[] args){
        Start.parseArgs(args);
        Logger.debug = Start.debug;
        Logger.silent = Start.silent;

        if(Start.test) Start.test();
        else if(!Start.headless) new Gui();
        else Start.cli();
    }

    /**
     * parses the '--debug', '--silent' and '--headless' flags and the path to the .csv file which contains the orders
     * @param args arguments which are passed to the tool by the operating system
     */
    static void parseArgs(String[] args){
      try{
        Start.path = args[0];
        if(args[1].equals("-d") || args[1].equals("--debug")){
          Start.debug = true;
        } else if(args[1].equals("-s") || args[1].equals("--silent")){
          Start.silent = true;
        } else if(args[1].equals("--headless") || args[1].equals("-h")){
          Start.headless = true;
        } else if(args[1].equals("--test") || args[1].equals("-t")){
          Start.test = true;
        } 
        if(args[2].equals("--headless") || args[2].equals("-h")){
          Start.headless = true;
        } else if(args[2].equals("--test") || args[2].equals("-t")){
          Start.test = true;
        }
      } catch (ArrayIndexOutOfBoundsException e) {}
    }

    static void test(){
      Fs fs = new Fs(Start.path);
      Balance b = new Balance();
      Lager l = new Lager(b);
      ArrayList<Order> o = fs.parseOrders();
      l.update(o.get(3), 0, 0, 0);
      l.getSlot(0,0,0);
      l.getSlot(0,0,1);
      l.update(o.get(29), 0, 0, 0);
      l.getSlot(0,0,0);
      l.getSlot(0,0,1);
    }

    static void cli(){
      boolean status = true;
      Scanner sc = new Scanner(System.in);
      Fs fs = new Fs(Start.path);
      Balance b = new Balance();
      Lager l = new Lager(b);
      ArrayList<Order> o = fs.parseOrders();
      System.out.println("Warehouse simulator - https://github.com/xnacly");

      while(status){
        System.out.print("> ");
        String cmd = sc.nextLine();

        if(cmd.equals(".exit")){
          status = false;
        } else if(cmd.equals(".orders")){
          for(Order _o : o) System.out.println(_o.toString());
        } else if(cmd.startsWith(".fulfill")){
          int orderID;

          int posX;
          int posY;
          int posZ;

          String[] inputString = cmd.split(" ");

          try{
            orderID = Integer.parseInt(inputString[1]);
            posX = Integer.parseInt(inputString[2]);
            posY = Integer.parseInt(inputString[3]);
            posZ = Integer.parseInt(inputString[4]);
          } catch (ArrayIndexOutOfBoundsException e) {
            Logger.err("Missing order id or coordinates, couldn't fulfill");
            continue;
          }

          l.update(o.get(orderID-1), posX, posY, posZ);
        } else if(cmd.startsWith(".query")){
          int posX;
          int posY;
          int posZ;

          String[] inputString = cmd.split(" ");

          try{
            posX = Integer.parseInt(inputString[1]);
            posY = Integer.parseInt(inputString[2]);
            posZ = Integer.parseInt(inputString[3]);
          } catch (ArrayIndexOutOfBoundsException e) {
            Logger.err("Missing coordinates, couldn't query");
            continue;
          }
          l.getSlot(posX, posY, posZ);
        } else if(cmd.startsWith(".move")){
          int targetX;
          int targetY;
          int targetZ;
          int destX;
          int destY;
          int destZ;

          String[] inputString = cmd.split(" ");

          try{
             targetX = Integer.parseInt(inputString[1]);
             targetY = Integer.parseInt(inputString[2]);
             targetZ = Integer.parseInt(inputString[3]);
             destX = Integer.parseInt(inputString[4]);
             destY = Integer.parseInt(inputString[5]);
             destZ = Integer.parseInt(inputString[6]);
          } catch (ArrayIndexOutOfBoundsException e) {
            Logger.err("Missing coordinates, couldn't move");
            continue;
          }
          l.rearrange(targetX, targetY, targetZ, destX, destY, destZ);
        } else if (cmd.equals(".balance")) {
          Logger.inf("Balance: " + b.getBalance());
        } else if (cmd.equals(".transactions")) {
          for(String s : b.getTransactions()) System.out.println(s);
        } else if(cmd.equals(".help")) {
          System.out.println("Usage: \n\t.exit \n\t\t(exit the shell)\n\t.help \n\t\t(print this screen)\n\t.fulfill [orderID] [posX] [posY] [posZ] \n\t\t(fulfill an order)\n\t.orders \n\t\t(view available orders)\n\t.query [posX] [posY] [posZ] \n\t\t(get product at coords)\n\t.balance \n\t\t(view current balance)\n\t.transactions \n\t\t(view transaction log)");
        } else {
          Logger.err("Unknown command: " + cmd);
          continue;
        }
      }
      sc.close();
    }
}
