//Use be Donor,Donee and Volunteers
public class Donor extends Applicants{

    private String category;
    private String type;

    public Donor (){

    }

    public Donor(String category, String type, String id, String name, String email, String phone) {
        super(id, name, email, phone);
        this.category = category;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Donor{" + "category=" + category + ", type=" + type + '}';
    }

}
