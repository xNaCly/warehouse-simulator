import java.util.Objects;

public class Stein extends Product{
    Type type;
    Weight weight;
    enum Type {
        MARMOR,
        GRANIT,
        SANDSTEIN
    }

    enum Weight {
        LIGHT,
        MIDDLE,
        HEAVY,
    }
    public Stein(String name, String type, String weight){
        super(name);

        if(Objects.equals(type.toLowerCase(), "marmor")){
            this.type = Type.MARMOR;
        } else if(Objects.equals(type.toLowerCase(), "granit")){
            this.type = Type.GRANIT;
        } else {
            this.type = Type.SANDSTEIN;
        }
    }
}
