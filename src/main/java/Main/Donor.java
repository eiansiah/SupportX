package Main;

public class Donor extends Applicants {

    private String id;
    private String category;
    private String type;
    private static int donorCount;

    public Donor (){

    }

    public Donor(String name, String email, String phone , String category, String type) {
        super(name, email, phone);
        donorCount++;
        id = String.format("DN%05d", donorCount);
        this.category = category;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return id + super.toString() + "," + category + "," + type;
    }

}

