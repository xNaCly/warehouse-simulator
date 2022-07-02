/**
 * Unit tests for internal components, are used in github ci checks
 * author: https://github.com/xnacly
 * */
import java.util.ArrayList;
import product.*;

public class Test {
    static Storage l;
    static Balance b;
    static Fs fs;
    static ArrayList<Order> o;
    static int failedTests;
    static int successfullTests;

    public static void main(String[] args){
        Logger.debug = true;
        Logger.headless = true;
        Test.initBalance();
        Test.initLager();
        Test.initFs(args[0]);
        Test.parseOrders();
        Test.insertOrder();
        Test.getOrder();
        Test.removeOrder();
        Test.blocking();
        Test.move();
        Test.moveBALKEN();
        Test.trashProduct();
        Test.end();
    }

    private static void end(){
        int total = Test.failedTests + Test.successfullTests;
        int percentF = Test.failedTests * 100 / total;
        int percentS = Test.successfullTests * 100 / total;
        System.out.printf("\ntest results: %d (%d%%) passed; %d (%d%%) failed; %d measured\n", Test.successfullTests, percentS, Test.failedTests, percentF, total);
        if(Test.failedTests != 0) System.exit(1);
    }

    private static void initBalance(){
        Test.b = new Balance();
        Test.successfullTests++;
        Logger.test("Balance Test success");
    }

    private static void initLager(){
        Test.l = new Storage(b);
        Test.successfullTests++;
        Logger.test("Storage Test success");
    }

    private static void initFs(String filename){
        Test.fs = new Fs(filename);
        Test.successfullTests++;
        Logger.test("Filesystem Test success");
    }
    
    private static void parseOrders(){
        Test.o = Test.fs.parseOrders();
        if(Test.o.size() == 0){
            Logger.err("Test parse orders failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;

        Logger.test("parse orders Test success");
    }

    private static void insertOrder(){
        Order _o = Test.o.get(0);
        boolean f = Test.l.update(_o, 0, 0, 0);
        if(!f){
            Logger.err("Test insert order failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
        Logger.test("insert order Test success");
    }

    @SuppressWarnings({"unused"}) // used to hide the dead code warning
    private static void getOrder(){
        Product p = Test.l.getSlot(0, 0, 0);

         if(p == null){
            Logger.err("Test get order failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
        Logger.test("get order Test success");
    }

    private static void removeOrder(){
        Order _o = Test.o.get(40);
        boolean f = Test.l.update(_o, 0, 0, 0);
        if(!f){
            Logger.err("Test remove order failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
        Logger.test("remove order Test success");
    }

    private static void blocking() {
        Order _o = Test.o.get(3);
        Order __o = Test.o.get(29);
        boolean _f = Test.l.update(_o, 0,0,0);
        boolean __f = Test.l.update(__o, 0,0,0);

        boolean _f1 = Test.l.update(_o, 1,0,0);
        boolean __f1 = Test.l.update(__o, 1,0,0);
        if(_f && __f && _f1 && __f1){
            Test.successfullTests++;
            Logger.test("blocking product Test success");
        } else {
            Logger.err("Test blocking order failed");
            Test.failedTests++;
        }
    }

    private static void move(){
        Order _o = Test.o.get(0);
        Test.l.update(_o, 0,0,0);
        boolean f = Test.l.rearrange(0,0,0,1,1,1);
        boolean f1 = Test.l.getSlot(1,1,1).equals(_o.product);
        if(!f || !f1){
            Logger.err("Test move product failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
        Logger.test("move product Test success");
    }

    private static void moveBALKEN(){
        Order _o = Test.o.get(3);
        Test.l.update(_o, 0,0,0);
        boolean f = Test.l.rearrange(0,0,0,0,1,1);
        boolean f1 = Test.l.getSlot(0,1,1).equals(_o.product);
        boolean f2 = Test.l.getSlot(1,1,1).equals(_o.product);
        if(!f || !f1 || !f2){
            Logger.err("Test move BALKEN failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
        Logger.test("move product Test success");
    }

    private static void trashProduct(){
        Test.l.update(o.get(0), 0, 0, 0);
        boolean f = Test.l.recycle(0,0,0);
        if(!f){
            Logger.err("Test recylce product failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
        Logger.test("recycle product Test success");
    }
}
