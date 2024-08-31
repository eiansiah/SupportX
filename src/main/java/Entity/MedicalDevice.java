/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/*
 *  author: Ko Jie Qi
 *  ID: 2307589
 * */
public class MedicalDevice extends DonationItem{
    private String venueCode;
    private String description;

    public MedicalDevice(String venueCode, String description, String itemCode, String itemName, int Quantity) {
        super(itemCode, itemName, Quantity);
        this.venueCode = venueCode;
        this.description = description;
    }
    
    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nMedicine Device\n"+super.toString() + "\nVenue Code: " + venueCode + "\nDescription: " + description + '\n';
    }
    
}
