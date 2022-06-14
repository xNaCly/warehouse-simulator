package product;

import java.util.Objects;

enum WoodType {
    KIEFER,
    BUCHE,
    EICHE
}

enum WoodForm {
    BRETTER,
    BALKEN,
    SCHEIT
}

public class Wood extends Product {
    final WoodType woodType;
    final WoodForm woodForm;
    public Wood(String type, String form){
        this.name = "Holz";

        if(Objects.equals(type.toLowerCase(), "kiefer")){
            this.woodType = WoodType.KIEFER;
        } else if(Objects.equals(type.toLowerCase(), "buche")){
            this.woodType = WoodType.BUCHE;
        } else {
            this.woodType = WoodType.EICHE;
        }

        if(Objects.equals(form.toLowerCase(), "bretter")){
            this.woodForm = WoodForm.BRETTER;
        } else if(Objects.equals(form.toLowerCase(), "balken")){
            this.woodForm = WoodForm.BALKEN;
        } else {
            this.woodForm = WoodForm.SCHEIT;
        }
    }

    @Override
    public String getNameAndProperty(){
        return this.name + ":" + this.woodForm;
    }

    public String getNameAndProperties(){
        return this.name + ":" + this.woodType + ":" + this.woodForm;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
