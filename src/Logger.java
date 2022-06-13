import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    static boolean debug;
    static boolean silent;

    static private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * prints text to stdout with INFO as the prefix 
     * @param text 
     */
    static void inf(String text){
        if(!silent) System.out.printf("[INFO][%s]: %s\n", Logger.getDate(), text);
    }

    /**
     * prints text to stdout with ERROR as the prefix 
     * (Only logging method exempt from the '--silent' flag)
     * @param text 
     */
    static void err(String text){
        System.out.printf("[ERROR][%s]: %s\n", Logger.getDate(), text);
    }

    /**
     * prints text to stdout with SUCCESS as the prefix 
     * @param text 
     */
    static void suc(String text){
        if(!silent) System.out.printf("[SUCCESS][%s]: %s\n", Logger.getDate(), text);
    }

    /**
     * prints text to stdout with DEBUG as the prefix (only prints if '--debug' flag is specified)
     * @param text 
     */
    static void debug(String text){
        if(Logger.debug) System.out.printf("[DEBUG][%s]: %s\n", Logger.getDate(), text);
    }
}
