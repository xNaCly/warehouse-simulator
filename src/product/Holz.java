package product;

import java.util.Objects;

enum HolzType {
    KIEFER,
    BUCHE,
    EICHE
}

enum HolzForm {
    BRETTER,
    BALKEN,
    SCHEIT
}

public class Holz extends Product {
    HolzType holzType;
    HolzForm holzForm;
    public Holz(String type, String form){
        this.name = "Holz";

        if(Objects.equals(type.toLowerCase(), "kiefer")){
            this.holzType = HolzType.KIEFER;
        } else if(Objects.equals(type.toLowerCase(), "buche")){
            this.holzType = HolzType.BUCHE;
        } else {
            this.holzType = HolzType.EICHE;
        }

        if(Objects.equals(form.toLowerCase(), "bretter")){
            this.holzForm = HolzForm.BRETTER;
        } else if(Objects.equals(form.toLowerCase(), "balken")){
            this.holzForm = HolzForm.BALKEN;
        } else {
            this.holzForm = HolzForm.SCHEIT;
        }
    }

    public HolzType getHolzType() {
        return holzType;
    }

    public void setHolzType(HolzType holzType) {
        this.holzType = holzType;
    }

    public HolzForm getHolzForm() {
        return holzForm;
    }

    public void setHolzForm(HolzForm holzForm) {
        this.holzForm = holzForm;
    }

    @Override
    public String getNameAndProperty(){
        return this.name + ":" + this.holzType;
    }

    public String getNameAndProperties(){
        return this.name + ":" + this.holzType + ":" + this.holzForm;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
