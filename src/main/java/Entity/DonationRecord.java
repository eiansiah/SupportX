/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDateTime;
//import adt
import Libraries.ArrayList;
import Libraries.ListInterface;

/**
 *
 * @author Ko Jie Qi
 */
public class DonationRecord {
    private int recordID;
    private Donor donor;
    private ListInterface<DonationItem> item;
    private ListInterface<Integer> qty;
    private ListInterface<Double> amount;
    private LocalDateTime donationDateTime;
    private static int nextRecordID;

    public DonationRecord() {
        recordID = nextRecordID;
        item = new ArrayList<>();
        qty = new ArrayList<>();
        amount=new ArrayList<>();
        donationDateTime = LocalDateTime.now();
    }

    public DonationRecord(int recordID, Donor donor, ListInterface<DonationItem> item, ListInterface<Integer> qty, ListInterface<Double> amount, LocalDateTime donationDateTime) {
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

    public ListInterface<DonationItem> getItem() {
        return item;
    }

    public void setItem(ListInterface<DonationItem> item) {
        this.item = item;
    }

    public ListInterface<Integer> getQty() {
        return qty;
    }

    public void setQty(ListInterface<Integer> qty) {
        this.qty = qty;
    }

    public ListInterface<Double> getAmount() {
        return amount;
    }

    public void setAmount(ListInterface<Double> amount) {
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
