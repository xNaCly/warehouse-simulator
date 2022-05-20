import java.util.Objects;

enum Color {
    WHITE,
    GREEN,
    BLUE,
}
enum Size {
    A3,
    A4,
    A5
}
public class Papier extends Product {
    Size size;
    Color color;


    public Papier(String name, String color, String size) {
        super(name);

        if(Objects.equals(color.toLowerCase(), "blau")){
            this.color = Color.BLUE;
        } else if(Objects.equals(color.toLowerCase(), "gruen")){
            this.color = Color.GREEN;
        } else {
            this.color = Color.WHITE;
        }


        if(Objects.equals(size.toLowerCase(), "A4")){
            this.size = Size.A4;
        } else if(Objects.equals(color.toLowerCase(), "A5")){
            this.size = Size.A5;
        } else {
            this.size = Size.A3;
        }
    }
}
