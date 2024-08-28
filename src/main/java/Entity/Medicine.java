/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDate;

/**
 *
 * @author Ko Jie Qi
 */
public class Medicine extends DonationItem{
    private double netWeight;
    private LocalDate expiryDate;
    private String gender;
    private String age;
    private String venueCode;
    private String dosageForm;
    private String description;

    public Medicine(double netWeight, LocalDate expiryDate, String gender, String age, String venueCode, String dosageForm, String description, String itemCode, String itemName, int Quantity) {
        super(itemCode, itemName, Quantity);
        this.netWeight = netWeight;
        this.expiryDate = expiryDate;
        this.gender = gender;
        this.age = age;
        this.venueCode = venueCode;
        this.dosageForm = dosageForm;
        this.description = description;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nMedicine\n"+super.toString() + "\nNet Weight (g): " + netWeight + "\nExpiry Date: " + expiryDate + "\nGender: " + gender + "\nAge: " + age + "\nVenue Code: " + venueCode + "\nDosage Form: " + dosageForm + "\nDescription: " + description + '\n';
    }
    
}
