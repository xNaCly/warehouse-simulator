import java.util.Scanner;
import java.util.ArrayList;

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
     * parses the '--debug', '--silent' and '--headless' flags and the path to the .csv file which contains the orders
     * @param args arguments which are passed to the tool by the operating system
     */
    static void parseArgs(String[] args){
      try{
        Start.path = args[0];
        for(String s : args){
            switch (s) {
                case "-d", "--debug" -> Start.debug = true;
                case "-s", "--silent" -> Start.silent = true;
            }
        }
      } catch (ArrayIndexOutOfBoundsException ignored) {}
    }
}
