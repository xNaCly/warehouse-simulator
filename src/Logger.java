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

    static void inf(String text){
        if(!silent) System.out.printf("[INF][%s]: %s\n", Logger.getDate(), text);
    }

    static void err(String text){
        System.out.printf("[ERR][%s]: %s\n", Logger.getDate(), text);
    }

    static void suc(String text){
        if(!silent) System.out.printf("[SUC][%s]: %s\n", Logger.getDate(), text);
    }
    static void debug(String text, int l, String file){
        if(!silent) if(Logger.debug) System.out.printf("[DEBUG][%s][f:%s][l:%d]: %s\n", Logger.getDate(), file, l, text);
    }
}
