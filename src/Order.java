import product.Product;

public class Order {
    final boolean insertOrder;
    final Product product;
    final int price;
    final int id;

    public Order(int id, boolean insertOrder, Product product, int price){
       this.id = id;
       this.insertOrder = insertOrder;
       this.product = product;
       this.price = price;
    }

    @Override
    public String toString() {
        return this.id + " ("+ (this.insertOrder?"E" : "A") +") " + this.product.getNameAndProperties() + " " + this.price + "€";
    }
}
