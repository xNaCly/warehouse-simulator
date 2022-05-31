import product.Product;

public class Lager{
    Product[][][] lager = new Product[2][3][4]; 
    Balance balance;

    public Lager(Balance balance){
        this.balance = balance;
    }

    public boolean update(Order o,  int posX, int posY, int posZ){
        if(o.insertOrder) this.insert(o, posX, posY, posZ);
        else this.remove(o, posX, posY, posZ);
        return false;
    }

    private boolean insert(Order o, int posX, int posY, int posZ){
        String feedback = String.format("%s: %d", o.product.getNameAndProperty(), o.price);
        if(this.lager[posZ][posY][posZ] != null){
            return false;
        }
        this.lager[posZ][posY][posZ] = o.product;
        this.balance.updateBalance(o.price, feedback, false);
        return true;
    }

    private boolean remove(Order o, int posX, int posY, int posZ){
        String feedback = String.format("%s: %d", o.product.toString(), o.price);
        if(this.lager[posZ][posY][posZ] == null){
            return false;
        }
        this.lager[posZ][posY][posZ] = new Product();
        this.balance.updateBalance(o.price, feedback, true);
        return true;
    }

    // TODO: implement; change to public 
    private boolean rearrange(){
        return false;
    }

    public Product getSlot(int posX, int posY, int posZ){
        return this.lager[posZ][posY][posZ];
    }
}
