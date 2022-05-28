package product;

public class Product {
    String name;
    public Product() {
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNameAndProperty(){
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }

}

