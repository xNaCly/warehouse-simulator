package product;

import javax.swing.*;

enum StoneType {
    MARMOR,
    GRANIT,
    SANDSTEIN
}

enum StoneWeight {
    LEICHT,
    MITTELSCHWER,
    SCHWER,
}

public class Stone extends Product {
    StoneType stoneType = null;
    StoneWeight stoneWeight = null;

    public Stone(String type, String weight){
        this.name = "Stein";

        switch (type.toLowerCase()) {
            case "marmor" -> this.stoneType = StoneType.MARMOR;
            case "granit" -> this.stoneType = StoneType.GRANIT;
            case "sandstein" -> this.stoneType = StoneType.SANDSTEIN;
        }

        switch (weight.toLowerCase()) {
            case "mittel" -> this.stoneWeight = StoneWeight.MITTELSCHWER;
            case "schwer" -> this.stoneWeight = StoneWeight.SCHWER;
            case "leicht" -> this.stoneWeight = StoneWeight.LEICHT;
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

    @Override
    public ImageIcon getIcon(){
        return new ImageIcon("../assets/stein.png");
    }
}
