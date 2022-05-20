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
}
