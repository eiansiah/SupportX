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
public class Beverage extends DonationItem{
    private double netVolume;
    private LocalDate expiryDate;
    private String venueCode;

    public Beverage(double netVolume, LocalDate expiryDate, String venueCode, String itemCode, String itemName, int Quantity) {
        super(itemCode, itemName, Quantity);
        this.netVolume = netVolume;
        this.expiryDate = expiryDate;
        this.venueCode = venueCode;
    }

    public double getNetVolume() {
        return netVolume;
    }

    public void setNetVolume(double netVolume) {
        this.netVolume = netVolume;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    @Override
    public String toString() {
        return "\nBeverage\n"+super.toString() + "\nNet Volume: " + netVolume + "\nExpiry Date: " + expiryDate + "\nVenue Code: " + venueCode + '\n';
    }
    
}
