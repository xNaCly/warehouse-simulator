
public class Start {
    static String path;
    static boolean debug;
    static boolean silent;
    static boolean headless;

    public static void main(String[] args){
        Start.parseArgs(args);
        Logger.debug = Start.debug;
        Logger.silent = Start.silent;

        new Gui();
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
        } else if(args[1].equals("--headless") || args[1].equals("-h")){
          Start.headless = true;
        }
        if(args[2].equals("--headless") || args[2].equals("-h")){
          Start.headless = true;
        }
      } catch (ArrayIndexOutOfBoundsException e) {}
    }
}
