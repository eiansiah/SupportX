/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Ko Jie Qi
 */
public class Money extends DonationItem{
    private double amount;
    private String source;

    public Money(double amount, String source, String itemCode, String itemName, int Quantity) {
        super(itemCode, itemName, Quantity);
        this.amount = amount;
        this.source = source;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "\nMoney\nItem Code: "+super.getItemCode()+"\nItem Name: "+super.getItemName() + "\nAmount: " + amount + "\nSource: " + source + '\n';
    }

    

}
