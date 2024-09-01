/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/*
 *  author: Ko Jie Qi
 *  ID: 2307589
 * */
public class Clothing extends DonationItem{
    private String size;
    private String clothingCategory;
    private String gender;
    private String age;
    private String venueCode;

    public Clothing(String size, String clothingCategory, String gender, String age, String venueCode, String itemCode, String itemName, int Quantity) {
        super(itemCode, itemName, Quantity);
        this.size = size;
        this.clothingCategory = clothingCategory;
        this.gender = gender;
        this.age = age;
        this.venueCode = venueCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getClothingCategory() {
        return clothingCategory;
    }

    public void setClothingCategory(String clothingCategory) {
        this.clothingCategory = clothingCategory;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    @Override
    public String toString() {
        return "\nClothing\n" + super.toString()+"\nSize: " + size + "\nClothing Category: " + clothingCategory + "\nGender: " + gender + "\nAge: " + age + "\nVenue Code: " + venueCode + '\n';
    }
    
}
