import java.util.ArrayList;

public class Test {
    static Lager l;
    static Balance b;
    static Fs fs;
    static ArrayList<Order> o;
    static int failedTests;
    static int successfullTests;

    public static void main(String args[]){
        Logger.silent = true;
        Logger.debug = true;
        Test.initBalance();
        Test.initLager();
        Test.initFs(args[0]);
        Test.parseOrders();
        Test.insertOrder();
        Test.removeOrder();
        // Test.trashProduct();
        Test.end();
    }

    private static void end(){
        int total = Test.failedTests + Test.successfullTests;
        int percentF = Test.failedTests * 100 / total;
        int percentS = Test.successfullTests * 100 / total;
        System.out.printf("test results: %d (%d%%) passed; %d (%d%%) failed; %d measured\n", Test.successfullTests, percentS, Test.failedTests, percentF, total);
    }

    private static void initBalance(){
        Test.b = new Balance();
        if(Test.b == null){
            Logger.err("Test init Balance failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
    }

    private static void initLager(){
        Test.l = new Lager(b);
        if(Test.l == null){
            Logger.err("Test init Lager failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
    }

    private static void initFs(String filename){
        Test.fs = new Fs(filename);
        if(Test.fs == null){
            Logger.err("Test init fs failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
    }
    
    private static void parseOrders(){
        Test.o = Test.fs.parseOrders();
        if(Test.o.size() == 0){
            Logger.err("Test parse orders failed");
            Test.failedTests++;
            return;
        }
        Test.successfullTests++;
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
    }

    private static void rearrangeProduct(){

    }

    // private static void trashProduct(){
    //     Test.l.getSlot(0,0,0);
    //     Test.l.update(o.get(0), 0, 0, 0);
    //     boolean f = Test.l.recycle(0,0,0);
    //     if(!f){
    //         Logger.err("Test trash product failed");
    //         Test.failedTests++;
    //         return;
    //     }
    //     Test.successfullTests++;
    // }
}
