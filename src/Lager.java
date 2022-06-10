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
        String prodName;
        String prodAtt;

        String pr = o.product.getNameAndProperty();
        if(pr.contains(":")) {
            String[] prod = pr.split(":");
            Logger.debug(o.product.getNameAndProperty(), 22, "Lager.java");
            prodName = prod[0];
            prodAtt = prod[1];
        } else {
            prodName = o.product.toString();
            prodAtt = null;
        }

        if(this.lager[posZ][posY][posZ] != null){
            // If the slot at the selected coordinates is already occupied return false
            Logger.err("Slot at coords z:" + posZ + ", y: " + posY + ", x: " + posX + " already occupied");
            return false;
        } else if(prodName.equals("Holz") && prodAtt.equals("BALKEN")) {
            // WOOD of type BALKEN can only be stored by occupying two slots,
            // therefore we check if the slot behind or in front is empty,
            // if not return false
            int otherZ = posZ == 1 ? 0 : 1;
            if(this.lager[otherZ][posY][posX] == null){
                this.lager[otherZ][posY][posX] = o.product;
            } else {
                Logger.err("Holz of type BALKEN needs two slots in front of each other");
                return false;
            }
        } else if(prodName.equals("Stein")) {
            if(prodAtt.equals("HEAVY")){
                // HEAVY stones can only be stored in the bottommost row
                if(posY != 0){
                    Logger.err("Stein of type HEAVY can only be stored in the bottommost row");
                    return false;
                }
            } else if(prodAtt.equals("MIDDLE")){
                // MIDDLE stones can not be stored in the topmost row
                if(posY == 2){
                    Logger.err("Stein of type MIDDLE can't be stored in the topmost row");
                    return false;
                }
            }
        }

        this.lager[posZ][posY][posZ] = o.product;
        this.balance.updateBalance(o.price, feedback, false);
        Logger.suc("Inserted Order with id: " + o.id);
        return true;
    }

    private boolean remove(Order o, int posX, int posY, int posZ){
        String feedback = String.format("%s: %d", o.product.toString(), o.price);

        if(this.lager[posZ][posY][posX] == null){
            Logger.err("Slot at coords z:" + posZ + ", y: " + posY + ", x: " + posX + " is already empty");
            return false;
        }

        // Delete the other slot taken up by the BALKEN
        if(o.product.toString().equals("Holz:BALKEN")){
            int otherZ = posZ == 1 ? 0 : 1;
            this.lager[otherZ][posY][posX] = new Product();
        }

        this.lager[posZ][posY][posX] = new Product();
        this.balance.updateBalance(o.price, feedback, true);
        Logger.suc("Removed Order with id: " + o.id);
        return true;
    }

    // TODO: implement; change to public
    // should probably work with
    private boolean rearrange(){
        return false;
    }

    public Product getSlot(int posX, int posY, int posZ){
        Product p = this.lager[posZ][posY][posZ];
        if(p != null) {
            Logger.suc("Query ["+ posZ + "][" + posY + "][" + posX + "] returned " + p.toString());
        } else {
            Logger.err("Query ["+ posZ + "][" + posY + "][" + posX + "] returned null");
        }
        return p;
    }
}
