/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDateTime;
//import adt
import Libraries.ArrayList;

/**
 *
 * @author Ko Jie Qi
 */
public class DonationRecord {
    private int recordID;
    private Donor donor;
    private ArrayList<DonationItem> item;
    private ArrayList<Integer> qty;
    private ArrayList<Double> amount;
    private LocalDateTime donationDateTime;
    private static int nextRecordID;

    public DonationRecord() {
        recordID = nextRecordID;
        item = new ArrayList<DonationItem>();
        qty = new ArrayList<Integer>();
        amount=new ArrayList<Double>();
        donationDateTime = LocalDateTime.now();
    }

    public DonationRecord(int recordID, Donor donor, ArrayList<DonationItem> item, ArrayList<Integer> qty, ArrayList<Double> amount, LocalDateTime donationDateTime) {
        this.recordID = recordID;
        this.donor = donor;
        this.item = item;
        this.qty = qty;
        this.amount = amount;
        this.donationDateTime = donationDateTime;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public ArrayList<DonationItem> getItem() {
        return item;
    }

    public void setItem(ArrayList<DonationItem> item) {
        this.item = item;
    }

    public ArrayList<Integer> getQty() {
        return qty;
    }

    public void setQty(ArrayList<Integer> qty) {
        this.qty = qty;
    }

    public ArrayList<Double> getAmount() {
        return amount;
    }

    public void setAmount(ArrayList<Double> amount) {
        this.amount = amount;
    }

    public LocalDateTime getDonationDateTime() {
        return donationDateTime;
    }

    public void setDonationDateTime(LocalDateTime donationDateTime) {
        this.donationDateTime = donationDateTime;
    }

    public static int getNextRecordID() {
        return nextRecordID;
    }

    public static void setNextRecordID(int nextRecordID) {
        DonationRecord.nextRecordID = nextRecordID;
    }

    @Override
    public String toString() {
        return "DonationRecord{" + "recordID=" + recordID + ", donor=" + donor + ", item=" + item + ", qty=" + qty + ", amount=" + amount + ", donationDateTime=" + donationDateTime + '}';
    }
    
}
