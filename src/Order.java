import product.Product;

enum TypeOfTransaction {
    RECYCLE,
    REARRANGE,
    INSERT,
    REMOVE,
    SKIP
}

public class Order {
    boolean insertOrder;
    Product product;
    int price;
    int id;
    TypeOfTransaction toT;

    public Order(int id, boolean insertOrder, Product product, int price){
       this.id = id;
       this.insertOrder = insertOrder;
       this.product = product;
       this.price = price;
       this.toT = insertOrder ? TypeOfTransaction.INSERT : TypeOfTransaction.REMOVE;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.product.toString() + " | " + this.price;
    }
}
