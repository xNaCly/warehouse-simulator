package product;

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
    PaperSize paperSize = null;
    PaperColor paperColor = null;

    public Paper(String color, String size) {
        this.name = "Papier";

        switch (color.toLowerCase()) {
            case "blau" -> this.paperColor = PaperColor.BLUE;
            case "gruen" -> this.paperColor = PaperColor.GREEN;
            case "weiss" -> this.paperColor = PaperColor.WHITE;
        }

        switch (size.toLowerCase()) {
            case "a3" -> this.paperSize = PaperSize.A3;
            case "a4" -> this.paperSize = PaperSize.A4;
            case "a5" -> this.paperSize = PaperSize.A5;
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
