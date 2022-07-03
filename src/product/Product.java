package product;

import javax.swing.*;

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

    public String getNameAndProperties(){
        return this.name;
    }

    public ImageIcon getIcon(){
        return new ImageIcon("../assets/empty.png");
    }

    @Override
    public String toString() {
        return name;
    }

}

