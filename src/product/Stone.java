package product;

import java.util.Objects;

enum StoneType {
    MARMOR,
    GRANIT,
    SANDSTEIN
}

enum StoneWeight {
    LIGHT,
    MIDDLE,
    HEAVY,
}

public class Stone extends Product {
    final StoneType stoneType;
    final StoneWeight stoneWeight;

    public Stone(String type, String weight){
        this.name = "Stein";
        if(Objects.equals(type.toLowerCase(), "marmor")){
            this.stoneType = StoneType.MARMOR;
        } else if(Objects.equals(type.toLowerCase(), "granit")){
            this.stoneType = StoneType.GRANIT;
        } else {
            this.stoneType = StoneType.SANDSTEIN;
        }

        if(Objects.equals(weight.toLowerCase(), "mittel")){
            this.stoneWeight = StoneWeight.MIDDLE;
        } else if(Objects.equals(weight.toLowerCase(), "schwer")){
            this.stoneWeight = StoneWeight.HEAVY;
        } else {
            this.stoneWeight = StoneWeight.LIGHT;
        }
    }

    @Override
    public String getNameAndProperty(){
        return this.name + ":" + this.stoneWeight;
    }

    @Override
    public String getNameAndProperties(){
        return this.name + ":" + this.stoneType + ":" + this.stoneWeight;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
