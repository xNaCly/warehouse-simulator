import product.*;

public class Lager{
    Product[][][] lager = new Product[2][3][4];
    Balance balance;

    public Lager(Balance balance){
        this.balance = balance;
    }

    public boolean update(Order o, int posX, int posY, int posZ){
        if(o.insertOrder){
            this.insert(o, posX, posY, posZ);
        } else{
            this.remove(o, posX, posY, posZ);
        }
        return false;
    }

    private boolean insert(Order o, int posX, int posY, int posZ){
        String feedback = String.format("[Order: %d] %s: %d (Einlagerung)", o.id, o.product.toString(), o.price);

        if(!this.insertInternal(o.product, posX, posY, posZ)) return false;

        this.balance.updateBalance(o.price, feedback, false);
        Logger.suc("Inserted '" + o.product.getNameAndProperties() + "' with order id " + o.id + " at Slot ["+ posZ + "][" + posY + "][" + posX + "]");
        return true;
    }

    private boolean remove(Order o, int posX, int posY, int posZ){
        String feedback = String.format("[Order: %d] %s: %d (Auslagerung)", o.id, o.product.toString(), o.price);

        if(!this.removeInternal(o.product, posX, posY, posZ)) return false;

        this.balance.updateBalance(o.price, feedback, false);
        Logger.suc("Removed '" + o.product.getNameAndProperties() + "' with order id " + o.id + " at Slot ["+ posZ + "][" + posY + "][" + posX + "]");
        return true;
    }

    public boolean rearrange(int targetX, int targetY, int targetZ, int destX, int destY, int destZ){
        Product p = this.lager[targetX][targetY][targetZ];
        this.insertInternal(p, destX, destY, destZ);
        this.removeInternal(p, targetX, targetY, targetZ);
        return true;
    }

    private boolean insertInternal(Product p, int posX, int posY, int posZ){
        String prodName;
        String prodAtt;

        // check if the selected slot is block by a pallet in the front shelf
        if(posZ != 0 && this.lager[0][posY][posX] != null){
            Logger.err("Slot at coords z:" + posZ + ", y: " + posY + ", x: " + posX + " blocked by a pallet in front of it");
            return false;
        }

        String pr = p.getNameAndProperty(); 
        System.out.println(pr);
        if(pr.contains(":")) {
            String[] prod = pr.split(":");
            prodName = prod[0];
            prodAtt = prod[1];
        } else {
            prodName = p.toString();
            prodAtt = null;
        }

        if(this.lager[posZ][posY][posX] != null){
            // If the slot at the selected coordinates is already occupied return false
            Logger.err("Slot ["+ posZ + "][" + posY + "][" + posX + "] already occupied");
            return false;
        } else if(prodName.equals("Holz") && prodAtt.equals("BALKEN")) {
            // WOOD of type BALKEN can only be stored by occupying two slots,
            // therefore we check if the slot behind or in front is empty,
            // if not return false
            int otherZ = posZ == 1 ? 0 : 1;
            if(this.lager[otherZ][posY][posX] == null){
                this.lager[otherZ][posY][posX] = p;
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

        this.lager[posZ][posY][posX] = p;
        return true;
    }

    private boolean removeInternal(Product p, int posX, int posY, int posZ){
        if(this.lager[posZ][posY][posX] == null){
            Logger.err("Slot ["+ posZ + "][" + posY + "][" + posX + "] is empty");
            return false;
        } 

        // check if the selected slot is blocked by a pallet in the front shelf
        if(posZ != 0 && this.lager[0][posY][posX] != null){
            Logger.err("Slot at coords z:" + posZ + ", y: " + posY + ", x: " + posX + " blocked by a pallet in front of it");
            return false;
        }

        Product productAtSlot = this.lager[posZ][posY][posX];
        // check if the products are exactly equal
        if(!productAtSlot.getNameAndProperties().equals(p.getNameAndProperties())){
            Logger.err("Product at Slot ["+ posZ + "][" + posY + "][" + posX + "] not equal to requested product");
            return false;
        }

        // Delete the other slot taken up by the BALKEN
        if(p.getNameAndProperty().equals("Holz:BALKEN")){
            int otherZ = posZ == 1 ? 0 : 1;
            this.lager[otherZ][posY][posX] = new Product();
        }

        this.lager[posZ][posY][posX] = new Product();
        return true;
    }

    public Product getSlot(int posX, int posY, int posZ){
        Product p = this.lager[posZ][posY][posX];
        if(p != null) {
            Logger.suc("Query ["+ posZ + "][" + posY + "][" + posX + "] returned " + p.getNameAndProperties());
        } else {
            Logger.err("Query ["+ posZ + "][" + posY + "][" + posX + "] returned null");
        }
        return p;
    }
}
