package product;

import java.util.Objects;

enum PaperColor {
    WHITE,
    GREEN,
    BLUE,
}
enum PaperSize {
    A3,
    A4,
    A5
}

public class Paper extends Product {
    final PaperSize paperSize;
    final PaperColor paperColor;

    public Paper(String color, String size) {
        this.name = "Papier";

        if(Objects.equals(color.toLowerCase(), "blau")){
            this.paperColor = PaperColor.BLUE;
        } else if(Objects.equals(color.toLowerCase(), "gruen")){
            this.paperColor = PaperColor.GREEN;
        } else {
            this.paperColor = PaperColor.WHITE;
        }


        if(Objects.equals(size.toLowerCase(), "A4")){
            this.paperSize = PaperSize.A4;
        } else if(Objects.equals(color.toLowerCase(), "A5")){
            this.paperSize = PaperSize.A5;
        } else {
            this.paperSize = PaperSize.A3;
        }
    }

    @Override
    public String getNameAndProperty(){
        return this.name + ":" + this.paperColor;
    }
    @Override
    public String getNameAndProperties(){
        return this.name + ":" + this.paperSize + ":" + this.paperColor;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
