import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.util.Date;

public class Logger {
    static boolean debug;
    static boolean silent;
    static boolean headless;
    static int curTest;
    static JFrame root;

    static public String getDate(){
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
     * prints text to stdout with TEST as the prefix 
     * @param text 
     */
    static void test(String text){
        Logger.curTest++;
        if(headless) System.out.printf("[TEST][%d][%s]: %s\n", Logger.curTest, Logger.getDate(), text);
    }

    /**
     * prints text to stdout with ERROR as the prefix 
     * (Only logging method exempt from the '--silent' flag)
     * @param text 
     */
    static void err(String text){
        int line = Thread.currentThread().getStackTrace()[2].getLineNumber();
        String file = Thread.currentThread().getStackTrace()[2].getFileName();
        text = String.format("%s (%s l:%d)", text, file, line);
        System.out.printf("[ERROR][%s]: %s\n", Logger.getDate(), text);
        if(!Logger.headless) JOptionPane.showMessageDialog(root, text);
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
        int line = Thread.currentThread().getStackTrace()[2].getLineNumber();
        String file = Thread.currentThread().getStackTrace()[2].getFileName();
        if(Logger.debug) System.out.printf("[DEBUG][%s]: %s (%s l:%d)\n", Logger.getDate(), text, file, line);
    }
}
