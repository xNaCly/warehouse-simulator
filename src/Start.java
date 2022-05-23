/**
 * Entry point to the whole program
 */
public class Start {
    public static void main(String[] args){
        Fs fs = new Fs("../Leistungsnachweis.csv");
        for(Order o : fs.parseCSV())
            System.out.println(o);
    }
}
