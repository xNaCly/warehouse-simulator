package product;

import java.util.Objects;

enum SteinType {
    MARMOR,
    GRANIT,
    SANDSTEIN
}

enum SteinWeight {
    LIGHT,
    MIDDLE,
    HEAVY,
}

public class Stein extends Product {
    SteinType steinType;
    SteinWeight steinWeight;

    public Stein(String type, String weight){
        this.name = "Stein";
        if(Objects.equals(type.toLowerCase(), "marmor")){
            this.steinType = SteinType.MARMOR;
        } else if(Objects.equals(type.toLowerCase(), "granit")){
            this.steinType = SteinType.GRANIT;
        } else {
            this.steinType = SteinType.SANDSTEIN;
        }

        if(Objects.equals(weight.toLowerCase(), "mittel")){
            this.steinWeight = SteinWeight.MIDDLE;
        } else if(Objects.equals(weight.toLowerCase(), "schwer")){
            this.steinWeight = SteinWeight.HEAVY;
        } else {
            this.steinWeight = SteinWeight.LIGHT;
        }
    }

    public SteinType getSteinType() {
        return steinType;
    }

    public void setSteinType(SteinType steinType) {
        this.steinType = steinType;
    }

    public SteinWeight getSteinWeight() {
        return steinWeight;
    }

    public void setSteinWeight(SteinWeight steinWeight) {
        this.steinWeight = steinWeight;
    }

    @Override
    public String getNameAndProperty(){
        return this.name + ":" + this.steinWeight;
    }

    public String getNameAndProperties(){
        return this.name + ":" + this.steinType + ":" + this.steinWeight;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
