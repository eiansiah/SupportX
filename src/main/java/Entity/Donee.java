/*
 *  author: Siah E-Ian
 *  ID: 2307610
 * */

package Entity;

import java.time.LocalDate;

public class Donee extends Applicants {

    private String doneeID;
    private String address;
    private String doneeType;
    private String itemCategoryRequired;
    private String doneeUrgency;
    private final LocalDate registeredDate;

    public Donee (){
        this.registeredDate = LocalDate.now();
    }

    public Donee (String doneeID, String name, String email, String phone, String address, String doneeType, String itemCategoryRequired, String doneeUrgency, LocalDate registeredDate) {
        super (name, email, phone);
        this.doneeID = doneeID;
        this.address = address;
        this.doneeType = doneeType;
        this.itemCategoryRequired = itemCategoryRequired;
        this.doneeUrgency = doneeUrgency;
        this.registeredDate = registeredDate;
    }

    public String getDoneeID() {
        return doneeID;
    }

    public void setDoneeID(String doneeID) {
        this.doneeID = doneeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoneeType() {
        return doneeType;
    }

    public void setDoneeType(String doneeType) {
        this.doneeType = doneeType;
    }

    public String getItemCategoryRequired() {
        return itemCategoryRequired;
    }

    public void setItemCategoryRequired(String itemCategoryRequired) {
        this.itemCategoryRequired = itemCategoryRequired;
    }

    public String getDoneeUrgency() {
        return doneeUrgency;
    }

    public void setDoneeUrgency(String doneeUrgency) {
        this.doneeUrgency = doneeUrgency;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    @Override
    public String toString() {
        return (doneeID + super.toString() + "," + address + "," + doneeType + "," + itemCategoryRequired + "," + doneeUrgency + "," + registeredDate);
    }
}