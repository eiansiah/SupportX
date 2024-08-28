/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Ko Jie Qi
 */
public class DonationItem {
    private String itemCode;
    private String itemName;
    private int Quantity;

    public DonationItem(String itemCode, String itemName, int Quantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.Quantity = Quantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    @Override
    public String toString() {
        return "Item Code: " + itemCode + "\nItem Name: " + itemName + "\nQuantity: " + Quantity;
    }

    
    
    
}
