import product.*;

public class Storage {
    Product[][][] lager = new Product[2][3][4];
    Balance balance;

    /**
     * Lager enables storing of products at slots in a 3 dimensional product array.
     */
    public Storage(Balance balance){
        this.balance = balance;
    }

    /**
     * @param o Order class which includes the product which gets inserted at the given coordiantes
     * @param posX
     * @param posY
     * @param posZ
     */
    public boolean update(Order o, int posX, int posY, int posZ){
        if(posX > 3 || posY > 2 || posZ > 1){
            return false;
        }
        if(o.insertOrder) this.insert(o, posX, posY, posZ);
        else this.remove(o, posX, posY, posZ);
        return true;
    }

    private boolean insert(Order o, int posX, int posY, int posZ){
        String feedback = String.format("[Order: %d] %s: %d€ (Einlagerung)", o.id, o.product.getNameAndProperties().replace(":", " "), o.price);

        if(!this.insertInternal(o.product, posX, posY, posZ)) return false;
        this.balance.updateBalance(o.price, feedback, false);
        Logger.suc("Inserted '" + o.product.getNameAndProperties() + "' with order id " + o.id + " at Slot ["+ posZ + "][" + posY + "][" + posX + "]");
        return true;
    }

    private boolean remove(Order o, int posX, int posY, int posZ){
        String feedback = String.format("[Order: %d] %s: %d€ (Auslagerung)", o.id, o.product.getNameAndProperties().replace(":", " "), o.price);

        if(!this.removeInternal(o.product, posX, posY, posZ)) return false;

        this.balance.updateBalance(o.price, feedback, false);
        Logger.suc("Removed '" + o.product.getNameAndProperties() + "' with order id " + o.id + " at Slot ["+ posZ + "][" + posY + "][" + posX + "]");
        return true;
    }

    /**
     * move target slot to dest slot
     */
    public boolean rearrange(int targetX, int targetY, int targetZ, int destX, int destY, int destZ){
        Product p = this.lager[targetX][targetY][targetZ];
        return this.insertInternal(p, destX, destY, destZ) && this.removeInternal(p, targetX, targetY, targetZ);
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
        if(pr.contains(":")) {
            String[] prod = pr.split(":");
            prodName = prod[0];
            prodAtt = prod[1];
        } else {
            Logger.err("Product is probably unknown");
            return false;
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
            this.lager[otherZ][posY][posX] = null;
        }

        this.lager[posZ][posY][posX] = null;
        return true;
    }

    /**
     * recycle / destroy the product at the coordinates
     */
    public boolean recycle(int posX, int posY, int posZ){
        Product p = this.lager[posZ][posY][posX];
        if(p == null || p.getNameAndProperties() == null) return false;
        String feedback = String.format("[Order: R] %s: -300€ (Recycling)", p.getNameAndProperties().replace(":", " "), 300);
        this.balance.updateBalance(300, feedback,  true);
        boolean f = this.removeInternal(p, posX, posY, posZ);
        if(f) Logger.suc("Recycled Item at ["+ posZ + "][" + posY + "][" + posX + "]: " + p.getNameAndProperties());
        return f;
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