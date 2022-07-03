package product;

import javax.swing.*;

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
    WoodType woodType = null;
    WoodForm woodForm = null;
    public Wood(String type, String form){
        this.name = "Holz";

        switch (type.toLowerCase()) {
            case "kiefer" -> this.woodType = WoodType.KIEFER;
            case "buche" -> this.woodType = WoodType.BUCHE;
            case "eiche" -> this.woodType = WoodType.EICHE;
        }

        switch (form.toLowerCase()) {
            case "bretter" -> this.woodForm = WoodForm.BRETTER;
            case "balken" -> this.woodForm = WoodForm.BALKEN;
            case "scheit" -> this.woodForm = WoodForm.SCHEIT;
        }
    }

    @Override
    public String getNameAndProperty(){
        return this.name + ":" + this.woodForm;
    }

    @Override
    public String getNameAndProperties(){
        return this.name + ":" + this.woodType + ":" + this.woodForm;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public ImageIcon getIcon(){
        return new ImageIcon("../assets/holz.png");
    }
}
