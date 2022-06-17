package product;

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
            case "mittel" -> this.stoneWeight = StoneWeight.MIDDLE;
            case "schwer" -> this.stoneWeight = StoneWeight.HEAVY;
            case "leicht" -> this.stoneWeight = StoneWeight.LIGHT;
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
