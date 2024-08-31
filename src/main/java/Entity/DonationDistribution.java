package Entity;

public class DonationDistribution {
    private String distributionID;
    private String itemCode;
    private String itemName;
    private int itemQuantity;
    private String doneeID;
    private final String distributionDate;

    public DonationDistribution() {
        this.distributionDate = "2024-08-01";
    }

    public DonationDistribution (String distributionID, String itemCode, String itemName, int itemQuantity, String doneeID, String distributionDate) {
        this.distributionID = distributionID;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.doneeID = doneeID;
        this.distributionDate = distributionDate;
    }

    public String getDistributionID() {
        return distributionID;
    }

    public void setDistributionID(String distributionID) {
        this.distributionID = distributionID;
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

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getDoneeID() {
        return doneeID;
    }

    public void setDoneeID(String doneeID) {
        this.doneeID = doneeID;
    }

    public String getDistributionDate() {
        return distributionDate;
    }

    @Override
    public String toString() {
        return (distributionID + "," + itemCode + "," + itemName + "," + itemQuantity + "," + doneeID + "," + distributionDate);
    }
}
