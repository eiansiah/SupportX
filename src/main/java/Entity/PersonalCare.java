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
public class PersonalCare extends DonationItem{
    private double netWeight;
    private LocalDate expiryDate;
    private String gender;
    private String age;
    private String personalCareCategory;
    private String venueCode;

    public PersonalCare(double netWeight, LocalDate expiryDate, String gender, String age, String personalCareCategory, String venueCode, String itemCode, String itemName, int Quantity) {
        super(itemCode, itemName, Quantity);
        this.netWeight = netWeight;
        this.expiryDate = expiryDate;
        this.gender = gender;
        this.age = age;
        this.personalCareCategory = personalCareCategory;
        this.venueCode = venueCode;
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

    public String getPersonalCareCategory() {
        return personalCareCategory;
    }

    public void setPersonalCareCategory(String personalCareCategory) {
        this.personalCareCategory = personalCareCategory;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    @Override
    public String toString() {
        return "\nPersonal Care\n"+super.toString() + "\nNet Weight (g): " + netWeight + "\nExpiry Date: " + expiryDate + "\nGender: " + gender + "\nAge: " + age + "\nPersonal Care Category: " + personalCareCategory + "\nVenue Code: " + venueCode + '\n';
    }
   
}
