import product.Product;

public class Order {
    boolean insertOrder;
    Product product;
    int price;
    int id;

    public Order(int id, boolean insertOrder, Product product, int price){
       this.id = id;
       this.insertOrder = insertOrder;
       this.product = product;
       this.price = price;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.product.toString() + " " + this.price;
    }
}
