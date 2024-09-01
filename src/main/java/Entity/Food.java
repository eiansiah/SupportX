/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDate;

/*
 *  author: Ko Jie Qi
 *  ID: 2307589
 * */
public class Food extends DonationItem{
    private double netWeight;
    private LocalDate expiryDate;
    private String foodType;
    private String venueCode;

    public Food(double netWeight, LocalDate expiryDate, String foodType, String venueCode, String itemCode, String itemName, int Quantity) {
        super(itemCode, itemName, Quantity);
        this.netWeight = netWeight;
        this.expiryDate = expiryDate;
        this.foodType = foodType;
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

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    @Override
    public String toString() {
        return "\nFood\n"+super.toString() + "\nNet Weight: " + netWeight + "\nExpiry Date: " + expiryDate + "\nFood Type: " + foodType + "\nVenue Code: " + venueCode + '\n';
    }
    
    

}
