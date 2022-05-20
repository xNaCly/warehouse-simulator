import java.util.Objects;

public class Holz extends Product {
    enum Type {
        KIEFER,
        BUCHE,
        EICHE
    }

    enum Form {
        BRETTER,
        BALKEN,
        SCHEIT
    }
    Type type;
    Form form;
    public Holz(String name, String type, String form){
        super(name);

        if(Objects.equals(type.toLowerCase(), "kiefer")){
            this.type = Type.KIEFER;
        } else if(Objects.equals(type.toLowerCase(), "buche")){
            this.type = Type.BUCHE;
        } else {
            this.type = Type.EICHE;
        }

        if(Objects.equals(form.toLowerCase(), "bretter")){
            this.form = Form.BRETTER;
        } else if(Objects.equals(form.toLowerCase(), "balken")){
            this.form = Form.BALKEN;
        } else {
            this.form = Form.SCHEIT;
        }
    }
}
