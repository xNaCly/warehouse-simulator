package product;

import java.util.Objects;

enum PapierColor {
    WHITE,
    GREEN,
    BLUE,
}
enum PapierSize {
    A3,
    A4,
    A5
}

public class Papier extends Product {
    PapierSize papierSize;
    PapierColor papierColor;

    public Papier(String color, String size) {
        this.name = "Papier";

        if(Objects.equals(color.toLowerCase(), "blau")){
            this.papierColor = PapierColor.BLUE;
        } else if(Objects.equals(color.toLowerCase(), "gruen")){
            this.papierColor = PapierColor.GREEN;
        } else {
            this.papierColor = PapierColor.WHITE;
        }


        if(Objects.equals(size.toLowerCase(), "A4")){
            this.papierSize = PapierSize.A4;
        } else if(Objects.equals(color.toLowerCase(), "A5")){
            this.papierSize = PapierSize.A5;
        } else {
            this.papierSize = PapierSize.A3;
        }
    }

    public PapierSize getPapierSize() {
        return papierSize;
    }

    public void setPapierSize(PapierSize papierSize) {
        this.papierSize = papierSize;
    }

    public PapierColor getPapierColor() {
        return papierColor;
    }

    public void setPapierColor(PapierColor papierColor) {
        this.papierColor = papierColor;
    }

    public String getNameAndProperties(){
        return this.name + ":" + this.papierSize + ":" + this.papierColor;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
