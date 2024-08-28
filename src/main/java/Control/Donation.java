/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
//import entity
import Entity.DonationItem;
import Entity.DonationRecord;
import Entity.Food;
import Entity.Beverage;
import Entity.Clothing;
import Entity.PersonalCare;
import Entity.MedicalDevice;
import Entity.Medicine;
import Entity.Money;
import Entity.Donor;
//import other subsystem function
import Control.DonorFunctions;

//import basic library
//file
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

//date
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//for sort
import java.util.Comparator;

//import adt
import Libraries.ArrayList;
import static Main.Event.EventHandler.searchEventByEventID;
//will be removed for boundary
import java.util.Scanner;
/**
 *
 * @author vivia
 */
public class Donation {

    public Donation() {
    }
    
    private DonorFunctions donorHandling= new DonorFunctions();
    
    //Load Files
    //Load Donation Record File
    public void loadIntoDR(ArrayList<DonationRecord> recordList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int recordID;
        Donor donor;
        ArrayList<DonationItem> itemList = new ArrayList<DonationItem>();
        loadIntoAll(itemList);
        ArrayList<DonationItem> recordItemList;
        ArrayList<Integer> qty;
        ArrayList<Double> amt;
        LocalDateTime donationDateTime;
        int nextRecordID = 1;
        try {
            File myObj = new File("DonationRecord.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);
                int index = 0;
                while (readerFile.hasNextLine()) {
                    recordItemList = new ArrayList<DonationItem>();
                    qty = new ArrayList<Integer>();
                    amt = new ArrayList<Double>();
                    String[] dataInLine = readerFile.nextLine().split("#");
                    recordID = Integer.parseInt(dataInLine[0]);
                    String _donorID = dataInLine[1];
                    String[] item2 = dataInLine[2].split(",");
                    String[] qty2 = dataInLine[3].split(",");
                    String[] amt2 = dataInLine[4].split(",");
                    donationDateTime = LocalDateTime.parse(dataInLine[5], dateFormat);
                    for (String qty3 : qty2) {
                        qty.add(Integer.valueOf(qty3));
                    }
                    for (String amt3 : amt2) {
                        amt.add(Double.valueOf(amt3));
                    }
                    for (String code : item2) {
                        for (DonationItem item : itemList) {
                            if (item.getItemCode().equals(code)) {
                                recordItemList.add(item);
                            }
                        }
                    }
                    donor = donorHandling.checkIfDonorExist(_donorID);
                    if (donor == null) {
                        donor = new Donor();
                        donor.setId("-");
                        donor.setName("Deleted Account");
                    }
                    //Get Donor Object
                    DonationRecord record = new DonationRecord(recordID, donor, recordItemList, qty, amt, donationDateTime);
                    recordList.add(record);
                    DonationRecord.setNextRecordID(recordID+1);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackDRFile(ArrayList<DonationRecord> recordList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            FileWriter writeFile = new FileWriter("Food.txt");
            for (DonationRecord record : recordList) {
                String line = record.getRecordID() + "#" + record.getDonor().getId() + "#";
                for (int i = 0; i < record.getItem().size(); i++) {
                    if (i != record.getItem().size() - 1) {
                        line += record.getItem().get(i).getItemCode() + ",";
                    } else {
                        line += record.getItem().get(i).getItemCode() + "#";
                    }
                }
                for (int i = 0; i < record.getQty().size(); i++) {
                    if (i != record.getQty().size() - 1) {
                        line += record.getQty().get(i) + ",";
                    } else {
                        line += record.getQty().get(i) + "#";
                    }
                }
                for (int i = 0; i < record.getAmount().size(); i++) {
                    if (i != record.getAmount().size() - 1) {
                        line += record.getAmount().get(i) + ",";
                    } else {
                        line += record.getAmount().get(i) + "#";
                    }
                }
                line += record.getDonationDateTime().format(dateFormat);
                writeFile.write(line + "\n");

            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //All
    public void loadIntoAll(ArrayList<DonationItem> itemList) {
        loadIntoFood(itemList);
        loadIntoBeverage(itemList);
        loadIntoClothing(itemList);
        loadIntoMedicalDevice(itemList);
        loadIntoMedicine(itemList);
        loadIntoPersonalCare(itemList);
        loadIntoMoney(itemList);
    }

    //Food
    public void loadIntoFood(ArrayList<DonationItem> itemList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double _netWeight;
        LocalDate _expiryDate;
        int _qty;
        String _foodType, _venueCode, _itemCode, _itemName;
        try {
            File myObj = new File("Food.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);

                while (readerFile.hasNextLine()) {
                    String[] dataInLine = readerFile.nextLine().split("#");
                    _itemCode = dataInLine[0];
                    _itemName = dataInLine[1];
                    _qty = Integer.parseInt(dataInLine[2]);
                    _netWeight = Double.parseDouble(dataInLine[3]);
                    _expiryDate = LocalDate.parse(dataInLine[4], dateFormat);
                    _foodType = dataInLine[5];
                    _venueCode = dataInLine[6];
                    Food item = new Food(_netWeight, _expiryDate, _foodType, _venueCode, _itemCode, _itemName, _qty);
                    itemList.add(item);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackFoodFile(ArrayList<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Food.txt");
            for (DonationItem item : itemList) {
                if (item instanceof Food) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#" + ((Food) item).getNetWeight() + "#" + ((Food) item).getExpiryDate() + "#" + ((Food) item).getFoodType() + "#" + ((Food) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Beverage
    public void loadIntoBeverage(ArrayList<DonationItem> itemList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double _netVolume;
        LocalDate _expiryDate;
        int _qty;
        String _venueCode, _itemCode, _itemName;
        try {
            File myObj = new File("Beverage.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);

                while (readerFile.hasNextLine()) {
                    String[] dataInLine = readerFile.nextLine().split("#");
                    _itemCode = dataInLine[0];
                    _itemName = dataInLine[1];
                    _qty = Integer.parseInt(dataInLine[2]);
                    _netVolume = Double.parseDouble(dataInLine[3]);
                    _expiryDate = LocalDate.parse(dataInLine[4], dateFormat);
                    _venueCode = dataInLine[5];
                    Beverage item = new Beverage(_netVolume, _expiryDate, _venueCode, _itemCode, _itemName, _qty);
                    itemList.add(item);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackBeverageFile(ArrayList<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Beverage.txt");
            for (DonationItem item : itemList) {
                if (item instanceof Beverage) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#" + ((Beverage) item).getNetVolume() + "#" + ((Beverage) item).getExpiryDate() + "#" + ((Beverage) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Clothing
    public void loadIntoClothing(ArrayList<DonationItem> itemList) {
        int _qty;
        String _size, _clothingCategory, _gender, _age, _venueCode, _itemCode, _itemName;
        try {
            File myObj = new File("Clothing.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);

                while (readerFile.hasNextLine()) {
                    String[] dataInLine = readerFile.nextLine().split("#");
                    _itemCode = dataInLine[0];
                    _itemName = dataInLine[1];
                    _qty = Integer.parseInt(dataInLine[2]);
                    _size = dataInLine[3];
                    _clothingCategory = dataInLine[4];
                    _gender = dataInLine[5];
                    _age = dataInLine[6];
                    _venueCode = dataInLine[7];
                    Clothing item = new Clothing(_size, _clothingCategory, _gender, _age, _venueCode, _itemCode, _itemName, _qty);
                    itemList.add(item);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackClothingFile(ArrayList<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Clothing.txt");
            for (DonationItem item : itemList) {
                if (item instanceof Clothing) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#" + ((Clothing) item).getSize() + "#" + ((Clothing) item).getClothingCategory() + "#" + ((Clothing) item).getGender() + "#" + ((Clothing) item).getAge() + "#" + ((Clothing) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Personal Care
    public void loadIntoPersonalCare(ArrayList<DonationItem> itemList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double _netWeight;
        LocalDate _expiryDate;
        int _qty;
        String _gender, _age, _personalCareCategory, _venueCode, _itemCode, _itemName;
        try {
            File myObj = new File("PersonalCare.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);

                while (readerFile.hasNextLine()) {
                    String[] dataInLine = readerFile.nextLine().split("#");
                    _itemCode = dataInLine[0];
                    _itemName = dataInLine[1];
                    _qty = Integer.parseInt(dataInLine[2]);
                    _netWeight = Double.parseDouble(dataInLine[3]);
                    _expiryDate = LocalDate.parse(dataInLine[4], dateFormat);
                    _gender = dataInLine[5];
                    _age = dataInLine[6];
                    _personalCareCategory = dataInLine[7];
                    _venueCode = dataInLine[8];
                    PersonalCare item = new PersonalCare(_netWeight, _expiryDate, _gender, _age, _personalCareCategory, _venueCode, _itemCode, _itemName, _qty);
                    itemList.add(item);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackPersonalCareFile(ArrayList<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("PersonalCare.txt");
            for (DonationItem item : itemList) {
                if (item instanceof PersonalCare) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#" + ((PersonalCare) item).getNetWeight() + "#" + ((PersonalCare) item).getExpiryDate() + "#" + ((PersonalCare) item).getGender() + "#" + ((PersonalCare) item).getAge() + "#" + ((PersonalCare) item).getPersonalCareCategory() + "#" + ((PersonalCare) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Medical Device
    public void loadIntoMedicalDevice(ArrayList<DonationItem> itemList) {
        int _qty;
        String _venueCode, _description, _itemCode, _itemName;
        try {
            File myObj = new File("MedicalDevice.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);

                while (readerFile.hasNextLine()) {
                    String[] dataInLine = readerFile.nextLine().split("#");
                    _itemCode = dataInLine[0];
                    _itemName = dataInLine[1];
                    _qty = Integer.parseInt(dataInLine[2]);
                    _venueCode = dataInLine[3];
                    _description = dataInLine[4];
                    MedicalDevice item = new MedicalDevice(_venueCode, _description, _itemCode, _itemName, _qty);
                    itemList.add(item);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackMedicalDeviceFile(ArrayList<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("MedicalDevice.txt");
            for (DonationItem item : itemList) {
                if (item instanceof MedicalDevice) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#" + ((MedicalDevice) item).getVenueCode() + "#" + ((MedicalDevice) item).getDescription() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Medicine
    public void loadIntoMedicine(ArrayList<DonationItem> itemList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double _netWeight;
        LocalDate _expiryDate;
        int _qty;
        String _gender, _age, _venueCode, _dosageForm, _description, _itemCode, _itemName;
        try {
            File myObj = new File("Medicine.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);

                while (readerFile.hasNextLine()) {
                    String[] dataInLine = readerFile.nextLine().split("#");
                    _itemCode = dataInLine[0];
                    _itemName = dataInLine[1];
                    _qty = Integer.parseInt(dataInLine[2]);
                    _netWeight = Double.parseDouble(dataInLine[3]);
                    _expiryDate = LocalDate.parse(dataInLine[4], dateFormat);
                    _gender = dataInLine[5];
                    _age = dataInLine[6];
                    _venueCode = dataInLine[7];
                    _dosageForm = dataInLine[8];
                    _description = dataInLine[9];
                    Medicine item = new Medicine(_netWeight, _expiryDate, _gender, _age, _venueCode, _dosageForm, _description, _itemCode, _itemName, _qty);
                    itemList.add(item);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackMedicineFile(ArrayList<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Medicine.txt");
            for (DonationItem item : itemList) {
                if (item instanceof Medicine) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#" + ((Medicine) item).getNetWeight() + "#" + ((Medicine) item).getExpiryDate() + "#" + ((Medicine) item).getGender() + "#" + ((Medicine) item).getAge() + "#" + ((Medicine) item).getVenueCode() + "#" + ((Medicine) item).getDosageForm() + "#" + ((Medicine) item).getDescription() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Money
    public void loadIntoMoney(ArrayList<DonationItem> itemList) {
        int _qty;
        String _source, _itemCode, _itemName;
        double _amount;
        try {
            File myObj = new File("Money.txt");
            myObj.createNewFile();

            try {
                Scanner readerFile = new Scanner(myObj);

                while (readerFile.hasNextLine()) {
                    String[] dataInLine = readerFile.nextLine().split("#");
                    _itemCode = dataInLine[0];
                    _itemName = dataInLine[1];
                    _qty = Integer.parseInt(dataInLine[2]);
                    _source = dataInLine[3];
                    _amount = Double.parseDouble(dataInLine[4]);
                    Money item = new Money(_amount, _source, _itemCode, _itemName, _qty);
                    itemList.add(item);
                }

                readerFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("An read file error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An create file error occurred.");
        }
    }

    public void loadBackMoneyFile(ArrayList<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Money.txt");
            for (DonationItem item : itemList) {
                if (item instanceof Money) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#" + ((Money) item).getSource() + "#" + ((Money) item).getAmount() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Donation Home Page
    public void donationHome() {


        int option;
        while (true) {
            displayMenuDonationHome();
            option = checkMenuDonationHomeOption();
            if (option == 9) {
                break;
            } else if (option == 8) {
                donationReport();
            } else if (option == 7) {
                fullDonation();
            } else if (option == 6) {
                donationDonor();
            } else if (option == 5) {
                trackDonation();
            } else if (option == 4) {
                editDonation();
            } else if (option == 3) {
                searchDonation();
            } else if (option == 2) {
                removeDonation();
            } else {
                addDonation();
            }
        }

        //Move back into file
    }

    //Display Donation Action Menu - Boundary
    public void displayMenuDonationHome() {
        System.out.println("\nDonation");
        System.out.println("1. Add Donation");
        System.out.println("2. Remove Donation");
        System.out.println("3. Search Donation");
        System.out.println("4. Amend Donation Details");
        System.out.println("5. Track Donation");
        System.out.println("6. Donation-Donor List");
        System.out.println("7. Full Donation List");
        System.out.println("8. Summary Report");
        System.out.println("9. Back to Support X Home");
    }

    //Display Donation Category - Boundary
    public void displayDonationCategory() {
        System.out.println("Donation Category");
        System.out.println("1. Food");
        System.out.println("2. Beverage");
        System.out.println("3. Clothing");
        System.out.println("4. Personal Care");
        System.out.println("5. Medical Device");
        System.out.println("6. Medicine");
        System.out.println("7. Money");
        System.out.println("8. Back to Donation");
    }

    //Check Donation Action Menu Option
    public int checkMenuDonationHomeOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-9): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 9) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-9).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-9).");
            }
        }
        return option;
    }

    //Check Donation Category Choice
    public int checkDonationCategoryOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-8): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 8) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Display Instruction for addDonation
    public void displayAddDonationInstruction() {
        System.out.println("\nDonor For This Donation");
        System.out.println("**Note that a donation can have one or many items.");
        System.out.println("**Leaving this page to go back Donation Page will end this session for this donation for this donor.\n");
    }
    
    public void displayVenueCode(){
        System.out.println("Venue Code");
        try {
            File myObj = new File("Venue.txt");
            Scanner readerFile = new Scanner(myObj);
            while (readerFile.hasNextLine()) {
                String[] venueList = readerFile.nextLine().split("#");
                    System.out.printf("%-10s %-20s\n",venueList[0],venueList[1]);
            }
            readerFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addItemToRecord(ArrayList<DonationItem> recordItem, ArrayList<Integer> recordQty, DonationItem item, int qty) {
        recordItem.add(item);
        recordQty.add(qty);
    }

    public void addItemToRecord(ArrayList<DonationItem> recordItem, ArrayList<Double> recordAmt, DonationItem item, double amt) {
        recordItem.add(item);
        recordAmt.add(amt);
    }

    //Add Donation
    public void addDonation() {
        int option;
        String reply;
        boolean continueDonationAdd = true;
        displayAddDonationInstruction();
        Donor donor = getDonorIDforDonation();
        ArrayList<DonationRecord> dRecordLIST = new ArrayList<DonationRecord>();
        if (donor != null) {
            DonationRecord donationRecord = new DonationRecord();
            donationRecord.setDonor(donor);
            while (true) {
                System.out.println("\nAdd Donation");
                displayDonationCategory();
                option = checkDonationCategoryOption();
                if (option == 8) {
                    if (!donationRecord.getItem().isEmpty()) {
                        //add record
                        dRecordLIST.add(donationRecord);
                        loadBackDRFile(dRecordLIST);
                        DonationRecord.setNextRecordID(DonationRecord.getNextRecordID() + 1);
                    }
                    break;
                } else if (option == 7) {
                    addMoney(donationRecord.getItem(), donationRecord.getAmount());
                } else if (option == 6) {
                    addMedicine(donationRecord.getItem(), donationRecord.getQty());
                } else if (option == 5) {
                    addMedicalDevice(donationRecord.getItem(), donationRecord.getQty());
                } else if (option == 4) {
                    addPersonalCare(donationRecord.getItem(), donationRecord.getQty());
                } else if (option == 3) {
                    addClothing(donationRecord.getItem(), donationRecord.getQty());
                } else if (option == 2) {
                    addBeverage(donationRecord.getItem(), donationRecord.getQty());
                } else {
                    addFood(donationRecord.getItem(), donationRecord.getQty());
                }
                while (true) {
                    try {
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("\nDo you wish to add other donation? (Y/N): ");
                        reply = scanner.nextLine();
                        if (reply.equalsIgnoreCase("N") || reply.equalsIgnoreCase("Y")) {
                            if (reply.equalsIgnoreCase("N")) {
                                continueDonationAdd = false;
                            }
                            break;
                        } else {
                            System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                        }
                    } catch (Exception ex) {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                }
                if (!continueDonationAdd) {
                    if (!donationRecord.getItem().isEmpty()) {
                        //add record
                        dRecordLIST.add(donationRecord);
                        loadBackDRFile(dRecordLIST);
                        DonationRecord.setNextRecordID(DonationRecord.getNextRecordID() + 1);
                    }
                    break;
                }
            }
        }
    }

    //Add Food
    public boolean addFood(ArrayList<DonationItem> recordItem, ArrayList<Integer> recordQty) {
        ArrayList<DonationItem> foodList = new ArrayList<DonationItem>();
        loadIntoFood(foodList);
        displayInstructionAddFood();

        //Choose to add amount or new item
        //Add New Item
        Scanner scanner = new Scanner(System.in);
        String _name, _foodType, _venueCode, _temp, _code;
        LocalDate _expiryDate;
        int _qty;
        double _netWeight;

        //Food Name
        _name = getNvalidateName();
        if (_name.equalsIgnoreCase("X")) {
            return false;
        }

        //Food Net Weight
        _netWeight = getNvalidateNetWeight("Food");
        if (_netWeight == 0) {
            return false;
        }

        //Food Expiry Date
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        _expiryDate = getNvalidateExpiryDate();
        if (_expiryDate.equals(LocalDate.parse("2000-12-30", dateFormat))) {
            return false;
        }

        //Food Type
        _foodType = getNvalidateFoodType();
        if (_foodType.equals("")) {
            return false;
        }

        //Food Quantity
        _qty = getNvalidateQuantity();
        if (_qty == 0) {
            return false;
        }

        //Venue Code
        _venueCode = getNvalidateVenueCode();
        if (_venueCode.equals("")) {
            return false;
        }

        //Check Existed
        boolean exist = checkExistBeforeFood(foodList, _name, _expiryDate, _netWeight, _foodType, _venueCode);
        //If exist, ask want to add to it?
        //Update File
        //If not existed, generate new Item Code
        //Write to File
        if (exist) {
            while (true) {
                try {
                    System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
                    _temp = scanner.nextLine();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            for (DonationItem item : foodList) {
                                if (item.getItemName().equals(_name) && ((Food) item).getExpiryDate().equals(_expiryDate) && ((Food) item).getNetWeight() == _netWeight && ((Food) item).getFoodType().equals(_foodType) && ((Food) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            System.out.println("Food quantity added successfully.");
                            loadBackFoodFile(foodList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Food", foodList);
            Food newItem = new Food(_netWeight, _expiryDate, _foodType, _venueCode, _code, _name, _qty);
            foodList.add(newItem);
            loadBackFoodFile(foodList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            System.out.println("New Food registered and added successfully.");
            return true;
        }

    }

    //Display Add Food Instruction - Boundary
    public void displayInstructionAddFood() {
        System.out.println("\nAdd Food\n");
        displayFoodType();
        displayVenueCode();
        System.out.println("\nInstruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Display Food Type Category - Boundary
    public void displayFoodType() {
        System.out.println("Food Category:\n1. Fruit\n2. Vegetable\n3. Protein\n4. Whole Grain\n5. Baby Food\n6. Dairy\n7. Fat\n8. Seasoning");
    }

    //Input Name - Boundary
    public String inputName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Item Name: ");
        return scanner.nextLine();
    }

    //Validate Name - Control
    public String getNvalidateName() {
        String _name;
        while (true) {
            _name = inputName();
            if (_name.isEmpty()) {
                System.out.println("Sorry! Invalid Item Name. Please do not leave the field empty.");
            } else {
                return _name;
            }
        }
    }

    //Input Description - Boundary
    public String inputDescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Description: ");
        return scanner.nextLine();
    }

    //Validate Description - Control
    public String getNvalidateDescription() {
        String _description;
        while (true) {
            _description = inputName();
            if (_description.isEmpty()) {
                System.out.println("Sorry! Invalid Description. Please do not leave the field empty.");
            } else {
                return _description;
            }
        }
    }

    //Input Food Net Weight - Boundary
    public String inputFoodNetWeight() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Food Net Weight (kilogram): ");
        return scanner.nextLine();
    }

    //Input Beverage Net Volume - Boundary
    public String inputBeverageNetVolume() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Beverage Net Volume (mililitre): ");
        return scanner.nextLine();
    }

    //Validate Net Volume - Control
    public double getNvalidateNetVolume() {
        String _temp;
        double _netVolume;
        while (true) {
            try {
                _temp = inputBeverageNetVolume();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _netVolume = Double.parseDouble(_temp);
                    if (_netVolume <= 0.0) {
                        System.out.println("Sorry! Invalid Beverage Net Volume. Please enter a net volume that is more than zero.");
                    } else {
                        return _netVolume;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Beverage Net Volume. Please enter a valid net volume.");
            }
        }
    }

    //Input Net Weight - Boundary
    public String inputNetWeight() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Net Weight (gram): ");
        return scanner.nextLine();
    }

    //Validate Net Weight - Control
    public double getNvalidateNetWeight(String donationCat) {
        String _temp;
        double _netWeight;
        while (true) {
            try {
                if (donationCat.equals("Food")) {
                    _temp = inputFoodNetWeight();
                } else {
                    _temp = inputNetWeight();
                }
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _netWeight = Double.parseDouble(_temp);
                    if (_netWeight <= 0.0 && _netWeight == -999) {
                        System.out.println("Sorry! Invalid Net Weight. Please enter a net weight that is more than zero.");
                    } else {
                        return _netWeight;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Net Weight. Please enter a valid net weight.");
            }
        }
    }

    //Input Expiry Date - Boundary
    public String inputExpiryDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Expiry Date (yyyy-mm-dd): ");
        return scanner.nextLine();
    }

    //Validate Expiry Date - Boundary
    public LocalDate getNvalidateExpiryDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String _temp;
        LocalDate _expiryDate;
        while (true) {
            try {
                _temp = inputExpiryDate();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    if (_temp.equals("-")) {
                        return LocalDate.parse("2000-12-31", dateFormat);
                    } else {
                        _expiryDate = LocalDate.parse(_temp, dateFormat);
                        if (!(_expiryDate.isAfter(LocalDate.now()))) {
                            System.out.println("Sorry! Invalid Expiry Date. Please enter a date that is after today.");
                        } else {
                            return _expiryDate;
                        }
                    }
                } else {
                    return LocalDate.parse("2000-12-30", dateFormat);
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Expiry Date. Please enter a valid expiry date in the form of (yyyy-mm-dd).");
            }

        }
    }

    //Input Food Type - Boundary
    public String inputFoodType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Food Type (1-8): ");
        return scanner.nextLine();
    }

    //Validate Food Type - Control
    public String getNvalidateFoodType() {
        String _temp, _foodType;
        int _tempOption;
        while (true) {
            try {
                _temp = inputFoodType();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 8)) {
                        System.out.println("Sorry! Invalid Food Type Option. Please enter a valid option (1-8).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Food Type Option. Please enter a valid option (1-8).");
            }
        }
        switch (_tempOption) {
            case 1:
                _foodType = "Fruit";
                break;
            case 2:
                _foodType = "Vegetable";
                break;
            case 3:
                _foodType = "Protein";
                break;
            case 4:
                _foodType = "Whole Grain";
                break;
            case 5:
                _foodType = "Baby Food";
                break;
            case 6:
                _foodType = "Dairy";
                break;
            case 7:
                _foodType = "Fat";
                break;
            default:
                _foodType = "Seasoning";
        }
        return _foodType;
    }

    //Input Clothing Category - Boundary
    public String inputClothingCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Clothing Category (1-9): ");
        return scanner.nextLine();
    }

    //Validate Clothing category - Control
    public String getNvalidateClothingCategory() {
        int _tempOption;
        String _temp, _clothCategory;
        while (true) {
            try {
                _temp = inputClothingCategory();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 9)) {
                        System.out.println("Sorry! Invalid Clothing Category Option. Please enter a valid option (1-8).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Clothing Category Option. Please enter a valid option (1-8).");
            }
        }
        switch (_tempOption) {
            case 1:
                _clothCategory = "Tops";
                break;
            case 2:
                _clothCategory = "Bottoms";
                break;
            case 3:
                _clothCategory = "Outerwear";
                break;
            case 4:
                _clothCategory = "Dress";
                break;
            case 5:
                _clothCategory = "Innerwear";
                break;
            case 6:
                _clothCategory = "Sportswear";
                break;
            case 7:
                _clothCategory = "Footwear";
                break;
            case 8:
                _clothCategory = "Accessories";
                break;
            default:
                _clothCategory = "Sleepwear";
        }
        return _clothCategory;
    }

    //Input Gender - Boundary
    public String inputGender() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Gender (1-3): ");
        return scanner.nextLine();
    }

    //Validate Gender - Control
    public String getNvalidateGender() {
        String _temp, _gender;
        int _tempOption;
        while (true) {
            try {
                _temp = inputGender();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 3)) {
                        System.out.println("Sorry! Invalid Gender Option. Please enter a valid option (1-3).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Gender Option. Please enter a valid option (1-3).");
            }
        }
        switch (_tempOption) {
            case 1:
                _gender = "Men";
                break;
            case 2:
                _gender = "Women";
                break;
            default:
                _gender = "Neutral";
        }
        return _gender;
    }

    //Input Age - Boundary
    public String inputAge() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Age Group(1-6): ");
        return scanner.nextLine();
    }

    //Validate Age - Control
    public String getNvalidateAge() {
        String _temp, _age;
        int _tempOption;
        while (true) {
            try {
                _temp = inputAge();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 6)) {
                        System.out.println("Sorry! Invalid Age Group Option. Please enter a valid option (1-8).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Age Group Option. Please enter a valid option (1-8).");
            }
        }
        switch (_tempOption) {
            case 1:
                _age = "Infants";
                break;
            case 2:
                _age = "Toddlers";
                break;
            case 3:
                _age = "Kids";
                break;
            case 4:
                _age = "Teens";
                break;
            case 5:
                _age = "Adults";
                break;
            default:
                _age = "Seniors";
        }
        return _age;
    }

    //Input Size - Boundary
    public String inputSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Clothing Size: ");
        return scanner.nextLine();
    }

    //Validate Size - Control
    public String getNvalidateSize(String _clothCategory, String _age, String _gender) {
        String _size;
        if (!_clothCategory.equals("Accessories") || (!_age.equals("Infants")) || (!_age.equals("Toddlers"))) {
            while (true) {
                try {
                    _size = inputSize();
                    if (_size.isEmpty()) {
                        System.out.println("Sorry! Invalid Clothing Size. Please do not leave the field empty.");
                    } else {
                        if (!_clothCategory.equals("Footwear")) {
                            if ((!_size.equals("XS")) && (!_size.equals("S")) && (!_size.equals("M")) && (!_size.equals("L")) && (!_size.equals("XL")) && (!_size.equals("XXL")) && (!_size.equals("XXXL"))) {
                                System.out.println("Sorry! Invalid Clothing Size for " + _clothCategory + ". Please enter a valid size.");
                            } else {
                                break;
                            }
                        }
                        if (_clothCategory.equals("Footwear")) {
                            if (_age.equals("Kids")) {
                                if ((Integer.parseInt(_size) >= 10 && Integer.parseInt(_size) >= 13.5) || (Integer.parseInt(_size) >= 1 && Integer.parseInt(_size) >= 6)) {
                                    break;
                                } else {
                                    System.out.println("Sorry! Invalid Clothing Size for " + _age + " " + _clothCategory + ". Please enter a valid size.");
                                }
                            } else if (_age.equals("Teens")) {
                                if (Integer.parseInt(_size) >= 6 && Integer.parseInt(_size) >= 8) {
                                    break;
                                } else {
                                    System.out.println("Sorry! Invalid Clothing Size for " + _age + " " + _clothCategory + ". Please enter a valid size.");
                                }
                            } else {
                                if (_gender.equals("Men")) {
                                    if (Integer.parseInt(_size) >= 5 && Integer.parseInt(_size) >= 14) {
                                        break;
                                    } else {
                                        System.out.println("Sorry! Invalid Clothing Size for " + _gender + " " + _age + " " + _clothCategory + ". Please enter a valid size.");
                                    }
                                } else if (_gender.equals("Women")) {
                                    if (Integer.parseInt(_size) >= 2 && Integer.parseInt(_size) >= 10) {
                                        break;
                                    } else {
                                        System.out.println("Sorry! Invalid Clothing Size for " + _gender + " " + _age + " " + _clothCategory + ". Please enter a valid size.");
                                    }
                                } else {
                                    if (Integer.parseInt(_size) >= 2 && Integer.parseInt(_size) >= 14) {
                                        break;
                                    } else {
                                        System.out.println("Sorry! Invalid Clothing Size for " + _gender + " " + _age + " " + _clothCategory + ". Please enter a valid size.");
                                    }
                                }

                            }
                        }
                        if (_size.equalsIgnoreCase("X")) {
                            return "";
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Clothing Size. Please enter a valid clothing size.");
                }
            }
        } else {
            System.out.println("Size: -");
            _size = "-";
        }
        return _size;
    }

    //Input Personal Care Category - Boundary
    public String inputPCCat() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Personal Care Category (1-5): ");
        return scanner.nextLine();
    }

    //Validate Personal Care Category - Control
    public String getNvalidatePCCat() {
        String _temp, _pcCategory;
        int _tempOption;
        while (true) {
            try {
                _temp = inputPCCat();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 9)) {
                        System.out.println("Sorry! Invalid Personal Care Category Option. Please enter a valid option (1-8).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Personal Care Category Option. Please enter a valid option (1-8).");
            }
        }
        switch (_tempOption) {
            case 1:
                _pcCategory = "Skin Care";
                break;
            case 2:
                _pcCategory = "Hair Care";
                break;
            case 3:
                _pcCategory = "Body Care";
                break;
            case 4:
                _pcCategory = "Oral Care";
                break;
            default:
                _pcCategory = "Personal Hygiene";
        }
        return _pcCategory;
    }

    //Input
    public String inputDosageForm() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Dosage Form (1-5): ");
        return scanner.nextLine();
    }

    //Validate
    public String getNvalidateDosageForm() {
        String _temp, _dosageForm;
        int _tempOption;
        while (true) {
            try {
                _temp = inputDosageForm();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 9)) {
                        System.out.println("Sorry! Invalid Dosage Form Option. Please enter a valid option (1-8).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Dosage Form Option. Please enter a valid option (1-8).");
            }
        }
        switch (_tempOption) {
            case 1:
                _dosageForm = "Solid";
                break;
            case 2:
                _dosageForm = "Liquid";
                break;
            case 3:
                _dosageForm = "Semi-Solid";
                break;
            case 4:
                _dosageForm = "Inhalation";
                break;
            default:
                _dosageForm = "Injectable";
        }
        return _dosageForm;
    }

    //Input Quantity - Boundary
    public String inputQty() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Quantity: ");
        return scanner.nextLine();
    }

    //Validate Quantity - Control
    public int getNvalidateQuantity() {
        String _temp;
        int _qty;
        while (true) {
            try {
                _temp = inputQty();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _qty = Integer.parseInt(_temp);
                    if (_qty <= 0) {
                        System.out.println("Sorry! Invalid Quantity. Please enter a quantity that is more than zero.");
                    } else {
                        return _qty;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Quantity. Please enter a valid quantity.");
            }
        }
    }

    //Input
    public String inputAmt() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Amount : RM ");
        return scanner.nextLine();
    }

    //Validate
    public double getNvalidateAmt() {
        String _temp;
        double _amount;
        while (true) {
            try {
                _temp = inputAmt();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _amount = Double.parseDouble(_temp);
                    if (_amount <= 0.0) {
                        System.out.println("Sorry! Invalid Money Amount. Please enter an amount that is more than zero.");
                    } else {
                        return _amount;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Money Amount. Please enter a valid amount.");
            }
        }
    }

    //Input Venue Code - Boundary
    public String inputVenueCode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Venue Code: ");
        return scanner.nextLine();
    }

    //Input Source - Boundary
    public String inputSource() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Source: ");
        return scanner.nextLine();
    }

    //Validate Source - Control
    public String getNvalidateSource() {
        String _source;
        while (true) {
            _source = inputSource();
            if (!(_source.equalsIgnoreCase("X"))) {
                if (!checkVenueCode(_source) && !checkEventCode(_source) && !_source.equals("E001")) {
                    System.out.println("Sorry! Invalid Source. Please enter a source.");
                } else {
                    return _source;
                }
            } else {
                return "";
            }
        }
    }

    //Validate Venue Code - Control
    public String getNvalidateVenueCode() {
        String _venueCode;
        while (true) {
            _venueCode = inputVenueCode();
            if (!(_venueCode.equalsIgnoreCase("X"))) {
                if (!checkVenueCode(_venueCode)) {
                    System.out.println("Sorry! Invalid Venue Code. Please enter a venue code.");
                } else {
                    return _venueCode;
                }
            } else {
                return "";
            }
        }
    }

    //Check Venue Code - Control
    public boolean checkVenueCode(String _vc) {
        try {
            File myObj = new File("Venue.txt");
            Scanner readerFile = new Scanner(myObj);
            while (readerFile.hasNextLine()) {
                String[] venueList = readerFile.nextLine().split("#");
                if (venueList[0].equals(_vc)) {
                    return true;
                }
            }
            readerFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    //Check Event Code - Control
    public boolean checkEventCode(String _ec) {
        if (searchEventByEventID(_ec) == null) {
            return false;
        } else {
            return true;
        }
    }

    //Check Food Exist - Control
    public boolean checkExistBeforeFood(ArrayList<DonationItem> foodList, String _name, LocalDate _expiryDate, double _netWeight, String _foodType, String _venueCode) {
        boolean exist = false;
        for (DonationItem item : foodList) {
            if (item.getItemName().equals(_name) && ((Food) item).getExpiryDate().equals(_expiryDate) && ((Food) item).getNetWeight() == _netWeight && ((Food) item).getFoodType().equals(_foodType) && ((Food) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Beverage Exist - Control
    public boolean checkExistBeforeBeverage(ArrayList<DonationItem> beverageList, String _name, LocalDate _expiryDate, double _netVolume, String _venueCode) {
        boolean exist = false;
        for (DonationItem item : beverageList) {
            if (item.getItemName().equals(_name) && ((Beverage) item).getExpiryDate().equals(_expiryDate) && ((Beverage) item).getNetVolume() == _netVolume && ((Beverage) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Clothing Exist - Control
    public boolean checkExistBeforeClothing(ArrayList<DonationItem> clothingList, String _name, String _size, String _clothCategory, String _age, String _gender, String _venueCode) {
        boolean exist = false;
        for (DonationItem item : clothingList) {
            if (item.getItemName().equals(_name) && ((Clothing) item).getSize().equals(_size) && ((Clothing) item).getClothingCategory().equals(_clothCategory) && ((Clothing) item).getAge().equals(_age) && ((Clothing) item).getGender().equals(_gender) && ((Clothing) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Personal Care Exist - Control
    public boolean checkExistBeforePersonalCare(ArrayList<DonationItem> personalCareList, String _name, LocalDate _expiryDate, double _netWeight, String _pcCategory, String _age, String _gender, String _venueCode) {
        boolean exist = false;
        for (DonationItem item : personalCareList) {
            if (item.getItemName().equals(_name) && ((PersonalCare) item).getExpiryDate().equals(_expiryDate) && ((PersonalCare) item).getNetWeight() == (_netWeight) && ((PersonalCare) item).getPersonalCareCategory().equals(_pcCategory) && ((PersonalCare) item).getAge().equals(_age) && ((PersonalCare) item).getGender().equals(_gender) && ((PersonalCare) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Medical Device Exist - Control
    public boolean checkExistBeforeMedicalDevice(ArrayList<DonationItem> medicalDeviceList, String _name, String _venueCode) {
        boolean exist = false;
        for (DonationItem item : medicalDeviceList) {
            if (item.getItemName().equals(_name) && ((MedicalDevice) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Medicine Exist - Control
    public boolean checkExistBeforeMedicine(ArrayList<DonationItem> medicineList, String _name, LocalDate _expiryDate, double _netWeight, String _dosageForm, String _age, String _gender, String _venueCode) {
        boolean exist = false;
        for (DonationItem item : medicineList) {

            if (item.getItemName().equals(_name) && ((Medicine) item).getExpiryDate().equals(_expiryDate) && ((Medicine) item).getNetWeight() == (_netWeight) && ((Medicine) item).getDosageForm().equals(_dosageForm) && ((Medicine) item).getAge().equals(_age) && ((Medicine) item).getGender().equals(_gender) && ((Medicine) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }

        }
        return exist;
    }

    //Check Money Exist - Control
    public boolean checkExistBeforeMoney(ArrayList<DonationItem> moneyList, String _source) {
        boolean exist = false;
        for (DonationItem item : moneyList) {
            if (((Money) item).getSource().equals(_source)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Generate Item Code
    public String generateItemCode(String itemCat, ArrayList<DonationItem> itemList) {
        String code = "";
        String backCode = fillZeroCode(generateRandNumber(itemList));
        if (itemCat.equals("Food")) {
            code += "FD";
        } else if (itemCat.equals("Beverage")) {
            code += "BG";
        } else if (itemCat.equals("Clothing")) {
            code += "CY";
        } else if (itemCat.equals("Personal Care")) {
            code += "PC";
        } else if (itemCat.equals("Medicine")) {
            code += "MN";
        } else if (itemCat.equals("Medical Device")) {
            code += "MD";
        } else {
            code += "MY";
        }
        code += backCode;
        return code;
    }

    public int generateRandNumber(ArrayList<DonationItem> itemList) {
        int code;
        boolean repeated = false;
        do {
            repeated = false;
            code = 1 + (int) (Math.random() * (999));
            if (!itemList.isEmpty()) {
                for (DonationItem item : itemList) {
                    if (Integer.parseInt(item.getItemCode().substring(2, 5)) == code) {
                        repeated = true;
                    }
                }
            }
            if (repeated == false) {
                break;
            }
        } while (repeated);
        return code;
    }

    //Create back part of code
    public String fillZeroCode(int num) {
        if (num < 10) {
            return "00" + num;
        } else if (num < 100) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    //Display Add Beverage Instruction - Boundary
    public void displayInstructionAddBeverage() {
        System.out.println("\nAdd Beverage");
        displayVenueCode();
        System.out.println("\nInstruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Add Beverage
    public boolean addBeverage(ArrayList<DonationItem> recordItem, ArrayList<Integer> recordQty) {
        ArrayList<DonationItem> beverageList = new ArrayList<DonationItem>();
        loadIntoBeverage(beverageList);
        displayInstructionAddBeverage();

        //Choose to add amount or new item
        //Add New Item
        Scanner scanner = new Scanner(System.in);
        String _name, _venueCode, _temp, _code;
        LocalDate _expiryDate;
        int _qty;
        double _netVolume;

        //Beverage Name
        _name = getNvalidateName();
        if (_name.equalsIgnoreCase("X")) {
            return false;
        }
        //Beverage Net Volume
        _netVolume = getNvalidateNetVolume();
        if (_netVolume == 0) {
            return false;
        }
        //Beverage Expiry Date
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        _expiryDate = getNvalidateExpiryDate();
        if (_expiryDate.equals(LocalDate.parse("2000-12-30", dateFormat))) {
            return false;
        }
        //Beverage Quantity
        _qty = getNvalidateQuantity();
        if (_qty == 0) {
            return false;
        }
        //Venue Code
        _venueCode = getNvalidateVenueCode();
        if (_venueCode.equals("")) {
            return false;
        }

        //Check Existed
        boolean exist = checkExistBeforeBeverage(beverageList, _name, _expiryDate, _netVolume, _venueCode);
        //If exist, ask want to add to it?
        //Update File
        //If not existed, generate new Item Code
        //Write to File
        if (exist) {
            while (true) {
                try {
                    System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
                    _temp = scanner.nextLine();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            for (DonationItem item : beverageList) {
                                if (item.getItemName().equals(_name) && ((Beverage) item).getExpiryDate().equals(_expiryDate) && ((Beverage) item).getNetVolume() == _netVolume && ((Beverage) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            System.out.println("Beverage quantity added successfully.");
                            loadBackBeverageFile(beverageList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Beverage", beverageList);
            Beverage newItem = new Beverage(_netVolume, _expiryDate, _venueCode, _code, _name, _qty);
            beverageList.add(newItem);
            loadBackBeverageFile(beverageList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            System.out.println("New Beverage registered and added successfully.");
            return true;
        }

    }

    //Display Add Clothing Instruction - Boundary
    public void displayInstructionAddClothing() {
        System.out.println("\nAdd Clothing\n");
        displayClothingCategory();
        displayGender();
        displayAge();
        displaySize();
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Display Clothing Category - Boundary
    public void displayClothingCategory() {
        System.out.println("Clothing Category:\n1. Tops\n2. Bottoms\n3. Outerwear\n4. Dress\n5. Innerwear\n6. Sportswear\n7. Footwear\n8. Accessories\n9. Sleepwear\n");
    }

    //Display Gender - Boundary
    public void displayGender() {
        System.out.println("Gender:\n1. Men\n2. Women\n3. Neutral\n");
    }

    //Display Age- Boundary
    public void displayAge() {
        System.out.println("Age Group:\n1. Infants\n2. Toddlers\n3. Kids\n4. Teens\n5. Adults\n6. Seniors\n");
    }

    //Display Size-Boundary
    public void displaySize() {
        System.out.println("Size: XS / S / M / L / XL / XXL / XXXL\n**This size is only available for Tops, Bottoms, Outerwear, Dress, Innerwear, Sportswear, Sleepwear\n**This sizes are only applicable for Kids, Teens, Adults and Seniors\n\nFootwear Size:\n1. Kids 10-13.5 1-6\n2. Teens 6-8\n3. Adults & Seniors - Men 6-14\n4. Adults & Seniors - Women 2-10\n5. Adults & Seniors - Neutral 2-14\n");
    }

    //Add Clothing
    public boolean addClothing(ArrayList<DonationItem> recordItem, ArrayList<Integer> recordQty) {
        ArrayList<DonationItem> clothingList = new ArrayList<DonationItem>();
        loadIntoClothing(clothingList);
        displayInstructionAddClothing();

        //Choose to add amount or new item
        //Add New Item
        Scanner scanner = new Scanner(System.in);
        String _name, _venueCode, _temp, _code, _clothCategory, _size, _gender, _age;
        int _qty;

        //Clothing Name
        _name = getNvalidateName();
        if (_name.equalsIgnoreCase("X")) {
            return false;
        }

        //Clothing Category
        _clothCategory = getNvalidateClothingCategory();
        if (_clothCategory.equals("")) {
            return false;
        }

        //Clothing Gender
        _gender = getNvalidateGender();
        if (_gender.equals("")) {
            return false;
        }
        //Clothing Age
        _age = getNvalidateAge();
        if (_age.equals("")) {
            return false;
        }

        //Clothing Size
        _size = getNvalidateSize(_clothCategory, _age, _gender);
        if (_size.equals("")) {
            return false;
        }
        //Clothing Quantity
        _qty = getNvalidateQuantity();
        if (_qty == 0) {
            return false;
        }

        //Venue Code
        _venueCode = getNvalidateVenueCode();
        if (_venueCode.equals("")) {
            return false;
        }

        //Check Existed
        boolean exist = checkExistBeforeClothing(clothingList, _name, _size, _clothCategory, _age, _gender, _venueCode);

        //If exist, ask want to add to it?
        //Update File
        //If not existed, generate new Item Code
        //Write to File
        if (exist) {
            while (true) {
                try {
                    System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
                    _temp = scanner.nextLine();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            for (DonationItem item : clothingList) {
                                if (item.getItemName().equals(_name) && ((Clothing) item).getSize().equals(_size) && ((Clothing) item).getClothingCategory().equals(_clothCategory) && ((Clothing) item).getAge().equals(_age) && ((Clothing) item).getGender().equals(_gender) && ((Clothing) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            System.out.println("Clothing quantity added successfully.");
                            loadBackClothingFile(clothingList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Clothing", clothingList);
            Clothing newItem = new Clothing(_size, _clothCategory, _gender, _age, _venueCode, _code, _name, _qty);
            clothingList.add(newItem);
            loadBackClothingFile(clothingList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            System.out.println("New Clothing registered and added successfully.");
            return true;
        }
    }

    //Display Add PersonalCare Instruction - Boundary
    public void displayInstructionAddPersonalCare() {
        System.out.println("\nAdd Personal Care\n");
        displayPCCat();
        displayGender();
        displayAge();
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter '-' for expiry date if the product does not have an expiry date.\nEnter '-999' for netweight if the product does not have a net weight.\nEnter 'X' to back to the previous page.\n");
    }

    //Display Personal care category - Boundary
    public void displayPCCat() {
        System.out.println("Personal Care Category:\n1. Skin care (Moisturizers, Cleansers, Toners)\n2. Hair Care (Shampoo, Conditioners, Hair Masks)\n3. Body Care (Body Wash, Soap, Body Scrubs)\n4. Oral Care (Toothpaste, Toothbrush, Mouthwash)\n5. Personal Hygiene (Sanitary Pads, Nail Clippers, Cotton Swabs)\n");
    }

    //Add Personal Care
    public boolean addPersonalCare(ArrayList<DonationItem> recordItem, ArrayList<Integer> recordQty) {
        ArrayList<DonationItem> personalCareList = new ArrayList<DonationItem>();
        loadIntoPersonalCare(personalCareList);
        displayInstructionAddPersonalCare();

        //Choose to add amount or new item
        //Add New Item
        Scanner scanner = new Scanner(System.in);
        String _name, _venueCode, _pcCategory, _gender, _age, _temp, _code;
        int _qty;
        LocalDate _expiryDate;
        double _netWeight;

        //Personal Care Name
        _name = getNvalidateName();
        if (_name.equalsIgnoreCase("X")) {
            return false;
        }

        //Personal Care Category
        _pcCategory = getNvalidatePCCat();
        if (_pcCategory.equalsIgnoreCase("")) {
            return false;
        }

        //Personal Care Gender
        _gender = getNvalidateGender();
        if (_gender.equals("")) {
            return false;
        }
        //Personal Care Age
        _age = getNvalidateAge();
        if (_age.equals("")) {
            return false;
        }

        //Personal Care Net Weight
        _netWeight = getNvalidateNetWeight("Personal Care");
        if (_netWeight == 0) {
            return false;
        }

        //Personal Care Expiry Date
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        _expiryDate = getNvalidateExpiryDate();
        if (_expiryDate.equals(LocalDate.parse("2000-12-30", dateFormat))) {
            return false;
        }

        //Personal Care Quantity
        _qty = getNvalidateQuantity();
        if (_qty == 0) {
            return false;
        }

        //Venue Code
        _venueCode = getNvalidateVenueCode();
        if (_venueCode.equals("")) {
            return false;
        }

        //Check Existed
        boolean exist = checkExistBeforePersonalCare(personalCareList, _name, _expiryDate, _netWeight, _pcCategory, _age, _gender, _venueCode);

        //If exist, ask want to add to it?
        //Update File
        //If not existed, generate new Item Code
        //Write to File
        if (exist) {
            while (true) {
                try {
                    System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
                    _temp = scanner.nextLine();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            for (DonationItem item : personalCareList) {
                                if (item.getItemName().equals(_name) && ((PersonalCare) item).getExpiryDate().equals(_expiryDate) && ((PersonalCare) item).getNetWeight() == (_netWeight) && ((PersonalCare) item).getPersonalCareCategory().equals(_pcCategory) && ((PersonalCare) item).getAge().equals(_age) && ((PersonalCare) item).getGender().equals(_gender) && ((PersonalCare) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            System.out.println("Personal Care quantity added successfully.");
                            loadBackPersonalCareFile(personalCareList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Personal Care", personalCareList);
            PersonalCare newItem = new PersonalCare(_netWeight, _expiryDate, _gender, _age, _pcCategory, _venueCode, _code, _name, _qty);
            personalCareList.add(newItem);
            loadBackPersonalCareFile(personalCareList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            System.out.println("New Personal Care registered and added successfully.");
            return true;
        }
    }

    //Display Add Medical Device Instruction - Boundary
    public void displayInstructionAddMedicalDevice() {
        System.out.println("\nAdd Medical Device\n");
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Add Medical Device
    public boolean addMedicalDevice(ArrayList<DonationItem> recordItem, ArrayList<Integer> recordQty) {
        ArrayList<DonationItem> medicalDeviceList = new ArrayList<DonationItem>();
        loadIntoMedicalDevice(medicalDeviceList);
        displayInstructionAddMedicalDevice();

        //Choose to add amount or new item
        //Add New Item
        Scanner scanner = new Scanner(System.in);
        String _name, _venueCode, _description, _temp, _code;
        int _qty;

        //Medical Device Name
        _name = getNvalidateName();
        if (_name.equalsIgnoreCase("X")) {
            return false;
        }

        //Medical Device Quantity
        _qty = getNvalidateQuantity();
        if (_qty == 0) {
            return false;
        }

        //Medical Device Description
        _description = getNvalidateDescription();
        if (_description.equalsIgnoreCase("X")) {
            return false;
        }

        //Venue Code
        _venueCode = getNvalidateVenueCode();
        if (_venueCode.equals("")) {
            return false;
        }

        //Check Existed
        boolean exist = checkExistBeforeMedicalDevice(medicalDeviceList, _name, _venueCode);

        //If exist, ask want to add to it?
        //Update File
        //If not existed, generate new Item Code
        //Write to File
        if (exist) {
            boolean loopErrorCheck = true;
            while (loopErrorCheck) {
                try {
                    System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
                    _temp = scanner.nextLine();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            while (true) {
                                System.out.print("Do you wanna \n1. Append Description \n2. Keep original version\n3. Keep the current version\nX. Stop this Addition of Donation\nPick an option(1-3, X): ");
                                _temp = scanner.nextLine();
                                if (_temp.equals("1") || _temp.equals("2") || _temp.equals("3")) {
                                    for (DonationItem item : medicalDeviceList) {
                                        if (item.getItemName().equals(_name) && ((MedicalDevice) item).getVenueCode().equals(_venueCode)) {
                                            if (_temp.equals("1")) {
                                                _description = ((MedicalDevice) item).getDescription() + _description;
                                            } else if (_temp.equals("2")) {
                                                _description = ((MedicalDevice) item).getDescription();
                                            }
                                            ((MedicalDevice) item).setDescription(_description);
                                            item.setQuantity(item.getQuantity() + _qty);
                                            addItemToRecord(recordItem, recordQty, item, _qty);
                                            loopErrorCheck = false;
                                            break;
                                        }
                                    }
                                    System.out.println("Medical Device quantity added successfully.");
                                    loadBackMedicalDeviceFile(medicalDeviceList);
                                    return true;
                                } else if (_temp.equalsIgnoreCase("X")) {
                                    loopErrorCheck = false;
                                    return false;
                                } else {
                                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                                }
                            }

                        } else {
                            loopErrorCheck = false;
                            return false;
                        }
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Medical Device", medicalDeviceList);
            MedicalDevice newItem = new MedicalDevice(_venueCode, _description, _code, _name, _qty);
            medicalDeviceList.add(newItem);
            loadBackMedicalDeviceFile(medicalDeviceList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            System.out.println("New Medical Device registered and added successfully.");
            return true;
        }
        return false;

    }

    //Display Add Medical Device Instruction - Boundary
    public void displayInstructionAddMedicine() {
        System.out.println("\nAdd Medicine\n");
        displayDSForm();
        displayGender();
        displayAge();
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Display Dosage Form - Boundary
    public void displayDSForm() {
        System.out.println("Dosage Form:\n1. Solid\n2. Liquid\n3. Semi-Solid(Cream, Gel, Paste, ...)\n4. Inhalation\n5. Injectable\n");
    }

    //Add Medicine
    public boolean addMedicine(ArrayList<DonationItem> recordItem, ArrayList<Integer> recordQty) {
        ArrayList<DonationItem> medicineList = new ArrayList<DonationItem>();
        loadIntoMedicine(medicineList);
        displayInstructionAddMedicine();
        //Choose to add amount or new item
        //Add New Item
        Scanner scanner = new Scanner(System.in);
        String _name, _venueCode, _dosageForm, _gender, _description, _age, _temp, _code;
        int _qty;
        LocalDate _expiryDate;
        double _netWeight;

        //Medicine Name
        _name = getNvalidateName();
        if (_name.equalsIgnoreCase("X")) {
            return false;
        }

        //Medicine Net Weight
        _netWeight = getNvalidateNetWeight("Medicine");
        if (_netWeight == 0) {
            return false;
        }

        //Medicine Expiry Date
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        _expiryDate = getNvalidateExpiryDate();
        if (_expiryDate.equals(LocalDate.parse("2000-12-30", dateFormat))) {
            return false;
        }

        //Dosage Form
        _dosageForm = getNvalidateDosageForm();
        if (_dosageForm.equals("")) {
            return false;
        }

        //Medicine Gender
        _gender = getNvalidateGender();
        if (_gender.equals("")) {
            return false;
        }
        //Medicine Age
        _age = getNvalidateAge();
        if (_age.equals("")) {
            return false;
        }

        //Medicine Quantity
        _qty = getNvalidateQuantity();
        if (_qty == 0) {
            return false;
        }

        //Medicine Description
        _description = getNvalidateDescription();
        if (_description.equalsIgnoreCase("X")) {
            return false;
        }

        //Venue Code
        _venueCode = getNvalidateVenueCode();
        if (_venueCode.equals("")) {
            return false;
        }

        //Check Existed
        boolean exist = checkExistBeforeMedicine(medicineList, _name, _expiryDate, _netWeight, _dosageForm, _age, _gender, _venueCode);

        //If exist, ask want to add to it?
        //Update File
        //If not existed, generate new Item Code
        //Write to File
        if (exist) {
            boolean loopErrorCheck = true;
            while (loopErrorCheck) {
                try {
                    System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
                    _temp = scanner.nextLine();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            while (true) {
                                System.out.print("Do you wanna \n1. Append Description \n2. Keep original version\n3. Keep the current version\nX. Stop this Addition of Donation\nPick an option(1-3, X): ");
                                _temp = scanner.nextLine();
                                if (_temp.equals("1") || _temp.equals("2") || _temp.equals("3")) {
                                    for (DonationItem item : medicineList) {
                                        if (item.getItemName().equals(_name) && ((Medicine) item).getExpiryDate().equals(_expiryDate) && ((Medicine) item).getNetWeight() == (_netWeight) && ((Medicine) item).getDosageForm().equals(_dosageForm) && ((Medicine) item).getAge().equals(_age) && ((Medicine) item).getGender().equals(_gender) && ((Medicine) item).getVenueCode().equals(_venueCode)) {
                                            if (_temp.equals("1")) {
                                                _description = ((Medicine) item).getDescription() + _description;
                                            } else if (_temp.equals("2")) {
                                                _description = ((Medicine) item).getDescription();
                                            }
                                            ((Medicine) item).setDescription(_description);
                                            item.setQuantity(item.getQuantity() + _qty);
                                            addItemToRecord(recordItem, recordQty, item, _qty);
                                            loopErrorCheck = false;
                                            break;
                                        }
                                    }
                                    System.out.println("Medicine quantity added successfully.");
                                    loadBackMedicineFile(medicineList);
                                    return true;
                                } else if (_temp.equalsIgnoreCase("X")) {
                                    loopErrorCheck = false;
                                    return false;
                                } else {
                                    System.out.println("Sorry! Invalid Input. Please enter 1, 2, 3 or X.");
                                }
                            }

                        } else {
                            return false;
                        }
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N2.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N." + ex);
                }
            }
        } else {
            _code = generateItemCode("Medicine", medicineList);
            Medicine newItem = new Medicine(_netWeight, _expiryDate, _gender, _age, _venueCode, _dosageForm, _description, _code, _name, _qty);
            medicineList.add(newItem);
            loadBackMedicineFile(medicineList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            System.out.println("New Medicine registered and added successfully.");
            return true;
        }
        return false;
    }

    //Display Source - Boundary
    public void displaySource() {
        System.out.println("Source: \nEnter 'E001' if the source is from e-banking.\nEnter venue code if the source is from a collection location. \nEnter event code if the source if from an event.\n");
    }

    //Display Add Money Instruction - Boundary
    public void displayInstructionAddMoney() {
        System.out.println("\nAdd Money\n");
        displaySource();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Add Money
    public boolean addMoney(ArrayList<DonationItem> recordItem, ArrayList<Double> recordAmt) {
        ArrayList<DonationItem> moneyList = new ArrayList<DonationItem>();
        loadIntoMoney(moneyList);
        Scanner scanner = new Scanner(System.in);
        displayInstructionAddMoney();
        String _temp, _name = "Funds", _source, _code;
        double _amount;
        //Money Amount
        _amount = getNvalidateAmt();
        if (_amount == 0) {
            return false;
        }
        //Source
        _source = getNvalidateSource();
        if (_source.equals("")) {
            return false;
        }

        //Check Existed
        boolean exist = checkExistBeforeMoney(moneyList, _source);

        //If exist, ask want to add to it?
        //Update File
        //If not existed, generate new Item Code
        //Write to File
        if (exist) {
            while (true) {
                try {
                    System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
                    _temp = scanner.nextLine();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            for (DonationItem item : moneyList) {
                                if (((Money) item).getSource().equals(_source)) {
                                    _amount = ((Money) item).getAmount() + _amount;
                                    ((Money) item).setAmount(_amount);
                                    addItemToRecord(recordItem, recordAmt, item, _amount);
                                    break;
                                }
                            }
                            System.out.println("Money Amount added successfully.");
                            loadBackMoneyFile(moneyList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Money", moneyList);
            Money newItem = new Money(_amount, _source, _code, _name, 1);
            moneyList.add(newItem);
            addItemToRecord(recordItem, recordAmt, newItem, _amount);
            loadBackMoneyFile(moneyList);
            System.out.println("New Money Item registered and added successfully.");
            return true;
        }
    }

    //Get donor name
    public Donor getDonorIDforDonation() {
        String reply = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter Donor ID: ");
                reply = scanner.nextLine();
                if (reply.equalsIgnoreCase("X")) {
                    return null;
                    //Implement validate donor code
                } else {
                    if (donorHandling.checkIfDonorExist(reply) != null) {
                        return donorHandling.checkIfDonorExist(reply);
                    } else {
                        System.out.println("Sorry! Invalid Donor ID. Please enter a valid Donor ID.");
                    }
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Donor ID. Please enter a valid Donor ID.");
            }
        }
    }

    //get item code
    public String inputItemCodeRemove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the item code to be removed or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    //get item code
    public String inputItemCodeEdit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the item code to be amended or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    //Remove Donation
    public void removeDonation() {
        ArrayList<DonationItem> itemlist = new ArrayList<DonationItem>();
        ArrayList<DonationRecord> recordlist = new ArrayList<DonationRecord>();
        ArrayList<DonationRecord> recordlist2 = new ArrayList<DonationRecord>();
        int option;
        String reply;
        boolean continueDonationAdd = true;
        while (true) {
            System.out.println("\n\nRemove Donation");
            displayDonationCategory();
            option = checkDonationCategoryOption();
            if (option == 8) {
                break;
            } else {
                itemlist = new ArrayList<DonationItem>();
                displayBasedOnCatOption(option, itemlist);
                loadIntoDR(recordlist);
                loadIntoDR(recordlist2);
                //ask to remove which
                if (!itemlist.isEmpty()) {
                    String _temp;
                    boolean removed = false;
                    while (removed == false) {
                        _temp = inputItemCodeRemove();
                        if (_temp.equalsIgnoreCase("X")) {
                            break;
                        } else {
                            for (DonationItem item : itemlist) {
                                if (item.getItemCode().equals(_temp)) {
                                    itemlist.remove(item);
                                    System.out.println("Item removed successfully!");
                                    for (DonationRecord record : recordlist2) {
                                        for (DonationItem recordItem : record.getItem()) {
                                            if (recordItem.getItemCode().equals(_temp)) {
                                                record.getItem().remove(item);
                                                if (record.getItem().isEmpty()) {
                                                    recordlist.remove(record);
                                                }
                                            }
                                        }
                                    }
                                    loadBackDRFile(recordlist);
                                    System.out.println("Records associated removed successfully!");
                                    removed = true;
                                    if (option == 7) {
                                        loadBackMoneyFile(itemlist);
                                    } else if (option == 6) {
                                        loadBackMedicineFile(itemlist);
                                    } else if (option == 5) {
                                        loadBackMedicalDeviceFile(itemlist);
                                    } else if (option == 4) {
                                        loadBackPersonalCareFile(itemlist);
                                    } else if (option == 3) {
                                        loadBackClothingFile(itemlist);
                                    } else if (option == 2) {
                                        loadBackBeverageFile(itemlist);
                                    } else {
                                        loadBackFoodFile(itemlist);
                                    }
                                    break;
                                }
                            }
                            if (removed == false) {
                                System.out.println("Sorry! Invalid Item Code. Please enter a valid item code." + _temp);
                            }
                        }
                    }
                } else {
                    enterContinue();
                    break;
                }
            }

            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("\nDo you wish to remove other donation? (Y/N): ");
                    reply = scanner.nextLine();
                    if (reply.equalsIgnoreCase("N") || reply.equalsIgnoreCase("Y")) {
                        if (reply.equalsIgnoreCase("N")) {
                            continueDonationAdd = false;
                        }
                        break;
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
            if (!continueDonationAdd) {
                break;
            }
        }
    }

    //Display Donation - Boundary
    public void displayAllDonation(ArrayList<DonationItem> fullList) {
        System.out.println("\nFull Donation List");
        if (!fullList.isEmpty()) {
            System.out.printf("%-10s %-30s %-20s %-30s\n", "Item Code", "Item Name", "Quantity/Amount", "Venue Code/ Event Code");
            for (DonationItem item : fullList) {
                if (item instanceof Money) {
                    System.out.printf("%-10s %-30s %-20s %-30s\n", item.getItemCode(), item.getItemName(), ((Money) item).getAmount(), ((Money) item).getSource());
                } else {
                    System.out.printf("%-10s %-30s %-20s ", item.getItemCode(), item.getItemName(), item.getQuantity());
                    if (item instanceof Food) {
                        System.out.printf("%-30s\n", ((Food) item).getVenueCode());
                    } else if (item instanceof Beverage) {
                        System.out.printf("%-30s\n", ((Beverage) item).getVenueCode());
                    } else if (item instanceof PersonalCare) {
                        System.out.printf("%-30s\n", ((PersonalCare) item).getVenueCode());
                    } else if (item instanceof Medicine) {
                        System.out.printf("%-30s\n", ((Medicine) item).getVenueCode());
                    } else if (item instanceof MedicalDevice) {
                        System.out.printf("%-30s\n", ((MedicalDevice) item).getVenueCode());
                    } else {
                        System.out.printf("%-30s\n", ((Clothing) item).getVenueCode());
                    }
                }
            }
        } else {
            displayEmptyList();
        }
    }

    //Display Food - Boundary
    public void displayFood(ArrayList<DonationItem> foodList) {
        System.out.printf("%-10s %-20s %-20s %-20s %-20s %-15s %-15s\n", "Item Code", "Item Name", "Net Weight (kg)", "Expiry Date", "Food Type", "Quantity", "Venue Code");
        for (DonationItem item : foodList) {
            System.out.printf("%-10s %-20s %-20s %-20s %-20s %-15s %-15s\n", item.getItemCode(), item.getItemName(), ((Food) item).getNetWeight(), ((Food) item).getExpiryDate(), ((Food) item).getFoodType(), item.getQuantity(), ((Food) item).getVenueCode());
        }
    }

    //Display Beverage - Boundary
    public void displayBeverage(ArrayList<DonationItem> beverageList) {
        System.out.printf("%-10s %-20s %-20s %-20s %-15s %-15s\n", "Item Code", "Item Name", "Net Volume (ml)", "Expiry Date", "Quantity", "Venue Code");
        for (DonationItem item : beverageList) {
            System.out.printf("%-10s %-20s %-20s %-20s %-15s %-15s\n", item.getItemCode(), item.getItemName(), ((Beverage) item).getNetVolume(), ((Beverage) item).getExpiryDate(), item.getQuantity(), ((Beverage) item).getVenueCode());
        }
    }

    //Display Clothing - Boundary
    public void displayClothing(ArrayList<DonationItem> clothingList) {
        System.out.printf("%-10s %-20s %-20s %-20s %-20s %-20s %-15s %-15s\n", "Item Code", "Item Name", "Clothing Category", "Gender", "Age Group", "Size", "Quantity", "Venue Code");
        for (DonationItem item : clothingList) {
            System.out.printf("%-10s %-20s %-20s %-20s %-20s %-20s %-15s %-15s\n", item.getItemCode(), item.getItemName(), ((Clothing) item).getClothingCategory(), ((Clothing) item).getGender(), ((Clothing) item).getAge(), ((Clothing) item).getSize(), item.getQuantity(), ((Clothing) item).getVenueCode());
        }
    }

    //Display Medical Device - Boundary
    public void displayMedicalDevice(ArrayList<DonationItem> medicalDeviceList) {
        System.out.printf("%-10s %-20s %-15s %-15s %-50s\n", "Item Code", "Item Name", "Quantity", "Venue Code", "Description");
        for (DonationItem item : medicalDeviceList) {
            System.out.printf("%-10s %-20s %-15s %-15s %-50s\n", item.getItemCode(), item.getItemName(), item.getQuantity(), ((MedicalDevice) item).getVenueCode(), ((MedicalDevice) item).getDescription());
        }
    }

    //Display Personal Care - Boundary
    public void displayPersonalCare(ArrayList<DonationItem> personalCareList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.printf("%-10s %-20s %-25s %-10s %-15s %-20s %-15s %-15s %-15s\n", "Item Code", "Item Name", "Personal Care Category", "Gender", "Age Group", "Net Weight (g)", "Expiry date", "Quantity", "Venue Code");
        for (DonationItem item : personalCareList) {
            if (((PersonalCare) item).getNetWeight() == -999) {
                System.out.printf("%-10s %-20s %-25s %-10s %-15s %-20s %-15s %-15s %-15s\n", item.getItemCode(), item.getItemName(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getAge(), "-", ((PersonalCare) item).getExpiryDate().format(dateFormat), item.getQuantity(), ((PersonalCare) item).getVenueCode());
            } else if (((PersonalCare) item).getNetWeight() == -999 && ((PersonalCare) item).getExpiryDate().format(dateFormat).equals("2000-12-31")) {
                System.out.printf("%-10s %-20s %-25s %-10s %-15s %-20s %-15s %-15s %-15s\n", item.getItemCode(), item.getItemName(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getAge(), "-", "-", item.getQuantity(), ((PersonalCare) item).getVenueCode());
            } else if (((PersonalCare) item).getExpiryDate().format(dateFormat).equals("2000-12-31")) {
                System.out.printf("%-10s %-20s %-25s %-10s %-15s %-20s %-15s %-15s %-15s\n", item.getItemCode(), item.getItemName(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getAge(), ((PersonalCare) item).getNetWeight(), "-", item.getQuantity(), ((PersonalCare) item).getVenueCode());
            } else {
                System.out.printf("%-10s %-20s %-25s %-10s %-15s %-20s %-15s %-15s %-15s\n", item.getItemCode(), item.getItemName(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getAge(), ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getExpiryDate().format(dateFormat), item.getQuantity(), ((PersonalCare) item).getVenueCode());
            }
        }
    }

    //Display Money - Boundary
    public void displayMoney(ArrayList<DonationItem> moneyList) {
        System.out.printf("%-10s %-20s %-20s %-30s\n", "Item Code", "Item Name", "Amount (RM)", "Source");
        for (DonationItem item : moneyList) {
            System.out.printf("%-10s %-20s %-20s %-30s\n", item.getItemCode(), item.getItemName(), ((Money) item).getAmount(), ((Money) item).getSource());
        }
    }

    //Display Medicine - Boundary
    public void displayMedicine(ArrayList<DonationItem> medicineList) {
        System.out.printf("%-10s %-20s %-15s %-15s %-15s %-20s %-20s %-15s %-15s %-50s\n", "Item Code", "Item Name", "Dosage Form", "Gender", "Age Group", "Net Weight (g)", "Expiry date", "Quantity", "Venue Code", "Description");
        for (DonationItem item : medicineList) {
            System.out.printf("%-10s %-20s %-15s %-15s %-15s %-20s %-20s %-15s %-15s %-50s\n", item.getItemCode(), item.getItemName(), ((Medicine) item).getDosageForm(), ((Medicine) item).getGender(), ((Medicine) item).getAge(), ((Medicine) item).getNetWeight(), ((Medicine) item).getExpiryDate(), item.getQuantity(), ((Medicine) item).getVenueCode(), ((Medicine) item).getDescription());
        }
    }

    //Display Empty File - Boundary
    public void displayEmptyList() {
        System.out.println("There is nothing in the list yet!");
    }

    //Display Based On Option - Boundary
    public void displayBasedOnCatOption(int option, ArrayList<DonationItem> itemlist) {
        if (option == 7) {
            System.out.println("\nMoney List");
            loadIntoMoney(itemlist);
            if (!itemlist.isEmpty()) {
                displayMoney(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 6) {
            System.out.println("\nMedicine List");
            loadIntoMedicine(itemlist);
            if (!itemlist.isEmpty()) {
                displayMedicine(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 5) {
            System.out.println("\nMedical Device List");
            loadIntoMedicalDevice(itemlist);
            if (!itemlist.isEmpty()) {
                displayMedicalDevice(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 4) {
            System.out.println("\nPersonal Care List");
            loadIntoPersonalCare(itemlist);
            if (!itemlist.isEmpty()) {
                displayPersonalCare(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 3) {
            System.out.println("\nClothing List");
            loadIntoClothing(itemlist);
            if (!itemlist.isEmpty()) {
                displayClothing(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 2) {
            System.out.println("\nBeverage List");
            loadIntoBeverage(itemlist);
            if (!itemlist.isEmpty()) {
                displayBeverage(itemlist);
            } else {
                displayEmptyList();
            }
        } else {
            System.out.println("\nFood List");
            loadIntoFood(itemlist);
            if (!itemlist.isEmpty()) {
                displayFood(itemlist);
            } else {
                displayEmptyList();
            }
        }
    }

    //Search Donation
    public void searchDonation() {
        ArrayList<DonationItem> fullList = new ArrayList<DonationItem>();
        loadIntoAll(fullList);
        ArrayList<DonationRecord> fullRecord = new ArrayList<DonationRecord>();
        loadIntoDR(fullRecord);
        DonationItem searchResultsItem = null;
        ArrayList<DonationRecord> searchResultsRecord = new ArrayList<DonationRecord>();
        String code;
        while (true) {
            code = inputSearchItemCode();
            if (code.isEmpty()) {
                System.out.println("Invalid input. Please do not leave the field blank.");
            } else {
                if (code.equals("X")) {
                    break;
                } else {
                    boolean valid = false;
                    for (DonationItem item : fullList) {
                        if (item.getItemCode().equals(code)) {
                            valid = true;
                        }
                    }
                    if (valid == true) {
                        break;
                    } else {
                        System.out.println("No results found.");
                    }
                }
            }
        }
        if (!code.equals("X")) {
            for (DonationItem item : fullList) {
                if (item.getItemCode().equals(code)) {
                    searchResultsItem = item;
                }
            }
            searchRecord(code, searchResultsRecord);
            String lineToDisplay;
            if (searchResultsItem instanceof Food) {
                lineToDisplay = ((Food) searchResultsItem).toString();
            } else if (searchResultsItem instanceof Beverage) {
                lineToDisplay = ((Beverage) searchResultsItem).toString();
            } else if (searchResultsItem instanceof Clothing) {
                lineToDisplay = ((Clothing) searchResultsItem).toString();
            } else if (searchResultsItem instanceof PersonalCare) {
                lineToDisplay = ((PersonalCare) searchResultsItem).toString();
            } else if (searchResultsItem instanceof MedicalDevice) {
                lineToDisplay = ((MedicalDevice) searchResultsItem).toString();
            } else if (searchResultsItem instanceof Medicine) {
                lineToDisplay = ((Medicine) searchResultsItem).toString();
            } else {
                lineToDisplay = ((Money) searchResultsItem).toString();
            }
            displaySingleItem(lineToDisplay);
            //display search results record
            displayDonationRecordAssociated(searchResultsRecord);
            enterContinue();
        }
    }

    //Display Single Item
    public void displaySingleItem(String lineToDisplay) {
        System.out.println(lineToDisplay);
    }

    //Display Donation Record
    public void displayDonationRecordAssociated(ArrayList<DonationRecord> dRecordList) {
        System.out.println("Donation Record Associated");
        if (dRecordList.isEmpty()) {
            displayEmptyList();
        } else {
            System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", "Donation Record ID", "Donor ID", "Donor Name", "Item Code", "Item Name", "Quantity", "Date Time");
            for (DonationRecord record : dRecordList) {
                displaySingleRecordWithDonorID(record);
            }
        }
    }

    public void displaySingleRecordWithDonorID(DonationRecord record) {
        int qtyIndex = 0;
        int amtIndex = 0;
        if (record.getItem().get(0) instanceof Money) {
            System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getDonor().getId(), record.getDonor().getName(), record.getItem().get(0).getItemCode(), record.getItem().get(0).getItemName(), record.getAmount().get(amtIndex), record.getDonationDateTime());
            amtIndex++;
        } else {
            System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getDonor().getId(), record.getDonor().getName(), record.getItem().get(0).getItemCode(), record.getItem().get(0).getItemName(), record.getQty().get(qtyIndex), record.getDonationDateTime());
            qtyIndex++;
        }
        if (record.getItem().size() > 1) {
            for (int i = 1; i < record.getItem().size(); i++) {
                if (record.getItem().get(i) instanceof Money) {
                    System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", "", "", "", record.getItem().get(i).getItemCode(), record.getItem().get(i).getItemName(), record.getAmount().get(amtIndex), "");
                    amtIndex++;
                } else {
                    System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", "", "", "", record.getItem().get(i).getItemCode(), record.getItem().get(i).getItemName(), record.getQty().get(qtyIndex), "");
                    qtyIndex++;
                }
            }
        }
    }

    public void displaySingleRecordWithoutDonorID(DonationRecord record) {
        int qtyIndex = 0;
        int amtIndex = 0;
        if (record.getItem().get(0) instanceof Money) {
            System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getItem().get(0).getItemCode(), record.getItem().get(0).getItemName(), record.getAmount().get(amtIndex), record.getDonationDateTime());
            amtIndex++;
        } else {
            System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getItem().get(0).getItemCode(), record.getItem().get(0).getItemName(), record.getQty().get(qtyIndex), record.getDonationDateTime());
            qtyIndex++;
        }
        if (record.getItem().size() > 1) {
            for (int i = 1; i < record.getItem().size(); i++) {
                if (record.getItem().get(i) instanceof Money) {
                    System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", "", record.getItem().get(i).getItemCode(), record.getItem().get(i).getItemName(), record.getAmount().get(amtIndex), "");
                    amtIndex++;
                } else {
                    System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", "", record.getItem().get(i).getItemCode(), record.getItem().get(i).getItemName(), record.getQty().get(qtyIndex), "");
                    qtyIndex++;
                }
            }
        }
    }

    public void displaySingleDonorRecords(ArrayList<DonationRecord> dRecordList, Donor donor) {
        System.out.println("\nDonor ID: " + donor.getId());
        System.out.println("Donor Name: " + donor.getName());
        System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", "Donation Record ID", "Item Code", "Item Name", "Quantity", "Date Time");
        for (DonationRecord record : dRecordList) {
            if (record.getDonor().getId().equals(donor.getId())) {
                displaySingleRecordWithoutDonorID(record);
            }
        }

    }

    //Input search item code
    public String inputSearchItemCode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Item Code to be searched (or X to return to Donation Page): ");
        return scanner.nextLine();
    }

    //Amend Donation
    public void editDonation() {
        int option;
        String reply;
        boolean continueDonationAdd = true;
        while (true) {
            System.out.println("\n\nAmend Donation");
            displayDonationCategory();
            option = checkDonationCategoryOption();
            if (option == 8) {
                break;
            } else {
                ArrayList<DonationItem> itemlist = new ArrayList<DonationItem>();
                displayBasedOnCatOption(option, itemlist);
                //ask to edit which
                if (!itemlist.isEmpty()) {
                    String _temp;
                    int amdOption;
                    boolean amend = false;
                    while (amend == false) {
                        _temp = inputItemCodeEdit();
                        if (_temp.equalsIgnoreCase("X")) {
                            break;
                        } else {
                            for (DonationItem item : itemlist) {
                                if (item.getItemCode().equals(_temp)) {
                                    //if it exists, ask to amend which info
                                    //display amend option
                                    displayAmendOption(option);
                                    amdOption = checkAmendOption(option);
                                    if ((option == 7 && amdOption == 3) || (option == 6 && amdOption == 10) || (option == 5 && amdOption == 5) || (option == 4 && amdOption == 9) || (option == 3 && amdOption == 8) || (option == 2 && amdOption == 6) || (option == 1 && amdOption == 7)) {
                                        amend = true;
                                        break;
                                    } else {
                                        if (amendDetails(_temp, option, amdOption, itemlist)) {
                                            amend = true;
                                            //amend finished, load back
                                            if (option == 7) {
                                                loadBackMoneyFile(itemlist);
                                            } else if (option == 6) {
                                                loadBackMedicineFile(itemlist);
                                            } else if (option == 5) {
                                                loadBackMedicalDeviceFile(itemlist);
                                            } else if (option == 4) {
                                                loadBackPersonalCareFile(itemlist);
                                            } else if (option == 3) {
                                                loadBackClothingFile(itemlist);
                                            } else if (option == 2) {
                                                loadBackBeverageFile(itemlist);
                                            } else {
                                                loadBackFoodFile(itemlist);
                                            }
                                            break;
                                        } else {
                                            amend = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (amend == false) {
                                System.out.println("Sorry! Invalid Item Code. Please enter a valid item code." + _temp);
                            }
                        }
                    }
                } else {
                    enterContinue();
                    break;
                }
            }
            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("\nDo you wish to amend other donation? (Y/N): ");
                    reply = scanner.nextLine();
                    if (reply.equalsIgnoreCase("N") || reply.equalsIgnoreCase("Y")) {
                        if (reply.equalsIgnoreCase("N")) {
                            continueDonationAdd = false;
                        }
                        break;
                    } else {
                        System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    System.out.println("Sorry! Invalid Input. Please enter Y or N.");
                }
            }
            if (!continueDonationAdd) {
                break;
            }
        }
    }

    //search for existing record
    public void searchRecord(String code, ArrayList<DonationRecord> searchResultsRecord) {
        ArrayList<DonationRecord> fullRecord = new ArrayList<DonationRecord>();
        loadIntoDR(fullRecord);
        for (DonationRecord record : fullRecord) {
            for (DonationItem item2 : record.getItem()) {
                if (item2.getItemCode().equals(code)) {
                    searchResultsRecord.add(record);
                    break;
                }
            }
        }
    }

    //Amend Item
    public boolean amendDetails(String itemCode, int option, int amdOption, ArrayList<DonationItem> itemlist) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String _temp;
        int _temp2;
        double _temp3;
        LocalDate _temp4;
        //Name
        if (amdOption == 1 && option >= 1 && option <= 6) {
            _temp = getNvalidateName();
            if (_temp.equals("X")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, _temp, ((Food) item).getExpiryDate(), ((Food) item).getNetWeight(), ((Food) item).getFoodType(), ((Food) item).getVenueCode());
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, _temp, ((Beverage) item).getExpiryDate(), ((Beverage) item).getNetVolume(), ((Beverage) item).getVenueCode());
                                break;
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, _temp, ((Clothing) item).getSize(), ((Clothing) item).getClothingCategory(), ((Clothing) item).getAge(), ((Clothing) item).getGender(), ((Clothing) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, _temp, ((PersonalCare) item).getExpiryDate(), ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            case 5:
                                exist = checkExistBeforeMedicalDevice(itemlist, _temp, ((MedicalDevice) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, _temp, ((Medicine) item).getExpiryDate(), ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), ((Medicine) item).getGender(), ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            item.setItemName(_temp);
                            return true;
                        }
                    }
                }
            }
        //Quantity
        } else if (amdOption == 2 && option >= 1 && option <= 6) {
            ArrayList<DonationRecord> searchResultsRecord = new ArrayList<DonationRecord>();
            ArrayList<DonationRecord> recordlist = new ArrayList<DonationRecord>();
            ArrayList<DonationRecord> recordlist2 = new ArrayList<DonationRecord>();
            loadIntoDR(recordlist);
            loadIntoDR(recordlist2);
            searchRecord(itemCode, searchResultsRecord);
            displayDonationRecordAssociated(searchResultsRecord);
            System.out.println("Action:\n1. Remove a record\n2.Amend a record quantity\nX. Stop this modification.");
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Your choice (1-2, X): ");
                _temp = scanner.nextLine();
                if (_temp.equalsIgnoreCase("X")) {
                    return false;
                } else {
                    if (!_temp.equals("1") && !_temp.equals("2")) {
                        System.out.println("Sorry! Invalid Option. Please enter a valid option (1-2, X).");
                    } else {
                        break;
                    }
                }
            }
            if (_temp.equals("1")) {
                boolean removed = false;
                _temp2 = 0;
                while (removed == false) {
                    _temp = inputRecordIDRemove();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //remove record
                        int qtyIndex = 0;
                        for (DonationRecord record : searchResultsRecord) {
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                for (DonationItem item : record.getItem()) {
                                    if (item.getItemCode().equals(itemCode)) {
                                        _temp2 = record.getQty().get(qtyIndex);
                                    }
                                    if (!(item instanceof Money)) {
                                        qtyIndex++;
                                    }
                                }
                                recordlist.remove(record);
                                removed = true;
                                break;
                            }
                        }
                        //reduce quantity, if 0 then remove item
                        for (DonationItem item : itemlist) {
                            if (item.getItemCode().equals(itemCode)) {
                                item.setQuantity(item.getQuantity() - _temp2);
                                if (item.getQuantity() == 0) {
                                    itemlist.remove(item);
                                }
                                loadBackDRFile(recordlist);
                                if (option == 7) {
                                    loadBackMoneyFile(itemlist);
                                } else if (option == 6) {
                                    loadBackMedicineFile(itemlist);
                                } else if (option == 5) {
                                    loadBackMedicalDeviceFile(itemlist);
                                } else if (option == 4) {
                                    loadBackPersonalCareFile(itemlist);
                                } else if (option == 3) {
                                    loadBackClothingFile(itemlist);
                                } else if (option == 2) {
                                    loadBackBeverageFile(itemlist);
                                } else {
                                    loadBackFoodFile(itemlist);
                                }
                                break;
                            }
                        }
                        if (removed == false) {
                            System.out.println("Sorry! Invalid Record ID. Please enter a valid record ID.");
                        } else {
                            System.out.println("Modification Successful!");
                        }
                    }
                }
            } else {
                while (true) {
                    _temp = inputRecordIDAmend();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //remove record
                        int qtyIndex = 0;
                        for (DonationRecord record : searchResultsRecord) {
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                for (DonationItem item2 : record.getItem()) {
                                    if (item2.getItemCode().equals(itemCode)) {
                                        _temp2 = getNvalidateQuantity();
                                        if (_temp2 == 0) {
                                            return false;
                                        } else {
                                            //System.out.println("Modification Successful!");
                                            int changes = _temp2 - record.getQty().get(qtyIndex);
                                            for (DonationItem item : itemlist) {
                                                if (item.getItemCode().equals(itemCode)) {
                                                    item.setQuantity(item.getQuantity() + changes);
                                                    if (item.getQuantity() == 0) {
                                                        itemlist.remove(item);
                                                    }
                                                    loadBackDRFile(recordlist);
                                                    if (option == 6) {
                                                        loadBackMedicineFile(itemlist);
                                                    } else if (option == 5) {
                                                        loadBackMedicalDeviceFile(itemlist);
                                                    } else if (option == 4) {
                                                        loadBackPersonalCareFile(itemlist);
                                                    } else if (option == 3) {
                                                        loadBackClothingFile(itemlist);
                                                    } else if (option == 2) {
                                                        loadBackBeverageFile(itemlist);
                                                    } else {
                                                        loadBackFoodFile(itemlist);
                                                    }
                                                    System.out.println("Modification Successful!");
                                                    break;
                                                }
                                            }
                                            return true;
                                        }
                                    }
                                    if (!(item2 instanceof Money)) {
                                        qtyIndex++;
                                    }
                                }
                            }
                        }
                        System.out.println("Sorry! Invalid Record ID. Please enter a valid record ID.");
                    }
                }
            }

        //Net Weight & Net Volume
        } else if (amdOption == 3 && (option == 1 || option == 6 || option == 2 || option == 4)) {
            if (option == 2) {
                _temp3 = getNvalidateNetVolume();
            } else {
                if (option == 1) {
                    _temp3 = getNvalidateNetWeight("Food");
                } else {
                    _temp3 = getNvalidateNetWeight("");
                }
            }
            if (_temp3 == 0) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, item.getItemName(), ((Food) item).getExpiryDate(), _temp3, ((Food) item).getFoodType(), ((Food) item).getVenueCode());
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, item.getItemName(), ((Beverage) item).getExpiryDate(), _temp3, ((Beverage) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(), _temp3, ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(), _temp3, ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), ((Medicine) item).getGender(), ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            switch (option) {
                                case 1:
                                    ((Food) item).setNetWeight(_temp3);
                                    break;
                                case 2:
                                    ((Beverage) item).setNetVolume(_temp3);
                                    break;
                                case 4:
                                    ((PersonalCare) item).setNetWeight(_temp3);
                                    break;
                                default:
                                    ((Medicine) item).setNetWeight(_temp3);
                                    break;
                            }
                            return true;
                        }
                    }
                }
            }
        //Description
        } else if ((amdOption == 4 && option == 5) || (amdOption == 9 && option == 6)) {
            _temp = getNvalidateDescription();
            if (_temp.equalsIgnoreCase("X")) {
                return false;
            } else {
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        System.out.println("Modification Successful!");
                        if (option == 5) {
                            ((MedicalDevice) item).setDescription(_temp);
                        } else {
                            ((Medicine) item).setDescription(_temp);
                        }
                        return true;
                    }
                }
            }
        //Expiry Date
        } else if (amdOption == 4 && (option == 1 || option == 6 || option == 2 || option == 4)) {
            _temp4 = getNvalidateExpiryDate();
            if (_temp4.equals(LocalDate.parse("2000-12-30", dateFormat))) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, item.getItemName(), _temp4, ((Food) item).getNetWeight(), ((Food) item).getFoodType(), ((Food) item).getVenueCode());
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, item.getItemName(), _temp4, ((Beverage) item).getNetVolume(), ((Beverage) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), _temp4, ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), _temp4, ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), ((Medicine) item).getGender(), ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            switch (option) {
                                case 1:
                                    ((Food) item).setExpiryDate(_temp4);
                                    break;
                                case 2:
                                    ((Beverage) item).setExpiryDate(_temp4);
                                    break;
                                case 4:
                                    ((PersonalCare) item).setExpiryDate(_temp4);
                                    break;
                                default:
                                    ((Medicine) item).setExpiryDate(_temp4);
                                    break;
                            }
                            return true;
                        }
                    }
                }
            }
        //Age
        } else if ((amdOption == 6 && (option == 4 || option == 6)) || (amdOption == 5 && option == 3)) {
            displayAge();
            _temp = getNvalidateAge();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, item.getItemName(), ((Clothing) item).getSize(), ((Clothing) item).getClothingCategory(), _temp, ((Clothing) item).getGender(), ((Clothing) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(), ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(), _temp, ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(), ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), _temp, ((Medicine) item).getGender(), ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            switch (option) {
                                case 3:
                                    ((Clothing) item).setAge(_temp);
                                    break;
                                case 4:
                                    ((PersonalCare) item).setAge(_temp);
                                    break;
                                default:
                                    ((Medicine) item).setAge(_temp);
                                    break;
                            }
                            return true;
                        }
                    }
                }
            }
        //Gender
        } else if ((amdOption == 5 && (option == 4 || option == 6)) || (amdOption == 4 && option == 3)) {
            displayGender();
            _temp = getNvalidateGender();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, item.getItemName(), ((Clothing) item).getSize(), ((Clothing) item).getClothingCategory(), ((Clothing) item).getAge(), _temp, ((Clothing) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(), ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(), _temp, ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(), ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), _temp, ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            switch (option) {
                                case 3:
                                    ((Clothing) item).setGender(_temp);
                                    break;
                                case 4:
                                    ((PersonalCare) item).setGender(_temp);
                                    break;
                                default:
                                    ((Medicine) item).setGender(_temp);
                                    break;
                            }
                            return true;
                        }
                    }
                }
            }
        //Venue Code
        } else if ((amdOption == 7 && (option == 3 || option == 6)) || (amdOption == 6 && option == 1) || (amdOption == 5 && option == 2) || (amdOption == 8 && option == 4) || (amdOption == 3 && option == 5)) {
            displayVenueCode();
            _temp = getNvalidateVenueCode();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, item.getItemName(), ((Food) item).getExpiryDate(), ((Food) item).getNetWeight(), ((Food) item).getFoodType(), _temp);
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, item.getItemName(), ((Beverage) item).getExpiryDate(), ((Beverage) item).getNetVolume(), _temp);
                                break;
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, item.getItemName(), ((Clothing) item).getSize(), ((Clothing) item).getClothingCategory(), ((Clothing) item).getAge(), ((Clothing) item).getGender(), _temp);
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(), ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(), ((PersonalCare) item).getGender(), _temp);
                                break;
                            case 5:
                                exist = checkExistBeforeMedicalDevice(itemlist, item.getItemName(), _temp);
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(), ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), ((Medicine) item).getGender(), _temp);
                                break;
                        }
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            switch (option) {
                                case 1:
                                    ((Food) item).setVenueCode(_temp);
                                    break;
                                case 2:
                                    ((Beverage) item).setVenueCode(_temp);
                                    break;
                                case 3:
                                    ((Clothing) item).setVenueCode(_temp);
                                    break;
                                case 4:
                                    ((PersonalCare) item).setVenueCode(_temp);
                                    break;
                                case 5:
                                    ((MedicalDevice) item).setVenueCode(_temp);
                                    break;
                                default:
                                    ((Medicine) item).setVenueCode(_temp);
                                    break;
                            }
                            return true;
                        }
                    }
                }
            }
        //Food Type
        } else if (amdOption == 5 && option == 1) {
            displayFoodType();
            _temp = getNvalidateFoodType();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeFood(itemlist, item.getItemName(), ((Food) item).getExpiryDate(), ((Food) item).getNetWeight(), _temp, ((Food) item).getVenueCode());
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            ((Food) item).setFoodType(_temp);
                            return true;
                        }
                    }
                }
            }
        //Clothing category
        } else if (amdOption == 3 && option == 3) {
            displayClothingCategory();
            _temp = getNvalidateClothingCategory();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeClothing(itemlist, item.getItemName(), ((Clothing) item).getSize(), _temp, ((Clothing) item).getAge(), ((Clothing) item).getGender(), ((Clothing) item).getVenueCode());
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            ((Clothing) item).setClothingCategory(_temp);
                            return true;
                        }
                    }
                }
            }
        //Personal Care Category
        } else if (amdOption == 7 && option == 4) {
            displayPCCat();
            _temp = getNvalidatePCCat();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(), ((PersonalCare) item).getNetWeight(), _temp, ((PersonalCare) item).getAge(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            ((PersonalCare) item).setPersonalCareCategory(_temp);
                            return true;
                        }
                    }
                }
            }
        //Dosage Form
        } else if (amdOption == 8 && option == 6) {
            displayDSForm();
            _temp = getNvalidateDosageForm();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(), ((Medicine) item).getNetWeight(), _temp, ((Medicine) item).getAge(), ((Medicine) item).getGender(), ((Medicine) item).getVenueCode());
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            ((Medicine) item).setDosageForm(_temp);
                            return true;
                        }
                    }
                }
            }
        //Size
        } else if (amdOption == 6 && option == 3) {
            displaySize();
            String _clothCat = "", _age = "", _gender = "";
            for (DonationItem item2 : itemlist) {
                if (item2.getItemCode().equals(itemCode)) {
                    _clothCat = ((Clothing) item2).getClothingCategory();
                    _age = ((Clothing) item2).getAge();
                    _gender = ((Clothing) item2).getGender();
                }
            }
            _temp = getNvalidateSize(_clothCat, _age, _gender);
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeClothing(itemlist, item.getItemName(), _temp, ((Clothing) item).getClothingCategory(), ((Clothing) item).getAge(), ((Clothing) item).getGender(), ((Clothing) item).getVenueCode());
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            ((Clothing) item).setSize(_temp);
                            return true;
                        }
                    }
                }
            }
        //amount
        } else if (amdOption == 1 && option == 7) {
            ArrayList<DonationRecord> searchResultsRecord = new ArrayList<DonationRecord>();
            ArrayList<DonationRecord> recordlist = new ArrayList<DonationRecord>();
            ArrayList<DonationRecord> recordlist2 = new ArrayList<DonationRecord>();
            loadIntoDR(recordlist);
            loadIntoDR(recordlist2);
            searchRecord(itemCode, searchResultsRecord);
            displayDonationRecordAssociated(searchResultsRecord);
            System.out.println("Action:\n1. Remove a record\n2.Amend a record quantity\nX. Stop this modification.");
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Your choice (1-2, X): ");
                _temp = scanner.nextLine();
                if (_temp.equalsIgnoreCase("X")) {
                    return false;
                } else {
                    if (!_temp.equals("1") && !_temp.equals("2")) {
                        System.out.println("Sorry! Invalid Option. Please enter a valid option (1-2, X).");
                    } else {
                        break;
                    }
                }
            }
            if (_temp.equals("1")) {
                boolean removed = false;
                _temp3 = 0;
                while (removed == false) {
                    _temp = inputRecordIDRemove();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //remove record
                        int amtIndex = 0;
                        for (DonationRecord record : searchResultsRecord) {
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                for (DonationItem item : record.getItem()) {
                                    if (item.getItemCode().equals(itemCode)) {
                                        _temp3 = record.getAmount().get(amtIndex);
                                    }
                                    if (item instanceof Money) {
                                        amtIndex++;
                                    }
                                }
                                recordlist.remove(record);
                                removed = true;
                                break;
                            }
                        }
                        //reduce quantity, if 0 then remove item
                        for (DonationItem item : itemlist) {
                            if (item.getItemCode().equals(itemCode)) {
                                ((Money)item).setAmount(((Money)item).getAmount() - _temp3);
                                if (((Money)item).getAmount() == 0) {
                                    itemlist.remove(item);
                                }
                                loadBackDRFile(recordlist);
                                loadBackMoneyFile(itemlist);
                                break;
                            }
                        }
                        if (removed == false) {
                            System.out.println("Sorry! Invalid Record ID. Please enter a valid record ID.");
                        } else {
                            System.out.println("Modification Successful!");
                        }
                    }
                }
            } else {
                while (true) {
                    _temp = inputRecordIDAmend();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //remove record
                        int amtIndex = 0;
                        for (DonationRecord record : searchResultsRecord) {
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                for (DonationItem item2 : record.getItem()) {
                                    if (item2.getItemCode().equals(itemCode)) {
                                        _temp3 = getNvalidateAmt();
                                        if (_temp3 == 0) {
                                            return false;
                                        } else {
                                            System.out.println("Modification Successful!");
                                            double changes = _temp3 - record.getAmount().get(amtIndex);
                                            for (DonationItem item : itemlist) {
                                                if (item.getItemCode().equals(itemCode)) {
                                                    ((Money)item).setAmount(((Money)item).getAmount() + changes);
                                                    if (((Money)item).getAmount() == 0) {
                                                        itemlist.remove(item);
                                                    }
                                                    loadBackDRFile(recordlist);
                                                    loadBackMoneyFile(itemlist);
                                                   
                                                    break;
                                                }
                                            }
                                            return true;
                                        }
                                    }
                                    if (item2 instanceof Money) {
                                        amtIndex++;
                                    }
                                }
                            }
                        }
                        System.out.println("Sorry! Invalid Record ID. Please enter a valid record ID.");
                    }
                }
            }

            //source
        } else {
            displaySource();
            _temp = getNvalidateSource();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                for (DonationItem item : itemlist) {
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeMoney(itemlist, _temp);
                        if (exist) {
                            System.out.println("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            System.out.println("Modification Successful!");
                            ((Money) item).setSource(_temp);
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }
    //get item code

    public String inputRecordIDRemove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the record ID to be removed or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    public String inputRecordIDAmend() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the record ID to be amended or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    //Display Amend Option - Boundary
    public void displayAmendOption(int option) {
        if (option == 7) {
            displayAmendMoneyOption();
        } else if (option == 6) {
            displayAmendMedicineOption();
        } else if (option == 5) {
            displayAmendMedicalDeviceOption();
        } else if (option == 4) {
            displayAmendPersonalCareOption();
        } else if (option == 3) {
            displayAmendClothingOption();
        } else if (option == 2) {
            displayAmendBeverageOption();
        } else {
            displayAmendFoodOption();
        }
    }

    //Check Amend Option
    public int checkAmendOption(int option) {
        int amdOption;
        if (option == 7) {
            amdOption = checkAmendMoneyOption();
        } else if (option == 6) {
            amdOption = checkAmendMedicineOption();
        } else if (option == 5) {
            amdOption = checkAmendMedicalDeviceOption();
        } else if (option == 4) {
            amdOption = checkAmendPersonalCareOption();
        } else if (option == 3) {
            amdOption = checkAmendClothingOption();
        } else if (option == 2) {
            amdOption = checkAmendBeverageOption();
        } else {
            amdOption = checkAmendFoodOption();
        }
        return amdOption;
    }

    //Amend Food Option Menu - Boundary
    public void displayAmendFoodOption() {
        System.out.println("\nAmend Food Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Weight");
        System.out.println("4. Expiry Date");
        System.out.println("5. Food Type");
        System.out.println("6. Venue Code");
        System.out.println("7. Back to Previous Page");
    }

    //Amend Beverage Option Menu - Boundary
    public void displayAmendBeverageOption() {
        System.out.println("\nAmend Beverage Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Volume");
        System.out.println("4. Expiry Date");
        System.out.println("5. Venue Code");
        System.out.println("6. Back to Previous Page");
    }

    //Amend Clothing Option Menu - Boundary
    public void displayAmendClothingOption() {
        System.out.println("\nAmend Clothing Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Clothing Category");
        System.out.println("4. Gender");
        System.out.println("5. Age");
        System.out.println("6. Size");
        System.out.println("7. Venue Code");
        System.out.println("8. Back to Previous Page");
    }

    //Amend Personal Care Option Menu - Boundary
    public void displayAmendPersonalCareOption() {
        System.out.println("\nAmend Personal Care Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Weight");
        System.out.println("4. Expiry Date");
        System.out.println("5. Gender");
        System.out.println("6. Age");
        System.out.println("7. Personal Care Category");
        System.out.println("8. Venue Code");
        System.out.println("9. Back to Previous Page");
    }

    //Amend Medical Device Option Menu - Boundary
    public void displayAmendMedicalDeviceOption() {
        System.out.println("\nAmend Medical Device Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Venue Code");
        System.out.println("4. Description");
        System.out.println("5. Back to Previous Page");
    }

    //Amend Medicine Option Menu - Boundary
    public void displayAmendMedicineOption() {
        System.out.println("\nAmend Medicine Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Weight");
        System.out.println("4. Expiry Date");
        System.out.println("5. Gender");
        System.out.println("6. Age");
        System.out.println("7. Venue Code");
        System.out.println("8. Dosage Form");
        System.out.println("9. Description");
        System.out.println("10. Back to Previous Page");
    }

    //Amend Money Option Menu - Boundary
    public void displayAmendMoneyOption() {
        System.out.println("\nAmend Money Option");
        System.out.println("1. Amount");
        System.out.println("2. Source");
        System.out.println("3. Back to Previous Page");
    }

    //Check Amend Food Option Menu
    public int checkAmendFoodOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-7): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 7) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Check Amend Beverage Option Menu
    public int checkAmendBeverageOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-6): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 6) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Check Amend Clothing Option Menu
    public int checkAmendClothingOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-8): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 8) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Check Amend Personal Care Option Menu
    public int checkAmendPersonalCareOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-9): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 9) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Check Amend Medical Device Option Menu
    public int checkAmendMedicalDeviceOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-5): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 5) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Check Amend Medicine Option Menu
    public int checkAmendMedicineOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-10): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 10) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Check Amend Money Option Menu
    public int checkAmendMoneyOption() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-3): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 3) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-8).");
            }
        }
        return option;
    }

    //Display Track Donation  - Boundary
    public void displayTrackDonation() {
        System.out.println("\nTrack Donation by Category\n");
        ArrayList<DonationItem> itemlist = new ArrayList<DonationItem>();
        for (int i = 1; i <= 7; i++) {
            displayBasedOnCatOption(i, itemlist);
            itemlist = new ArrayList<DonationItem>();
        }
    }

    //Track Donation
    public void trackDonation() {
        displayTrackDonation();
        enterContinue();
    }

    //Donation-Donor List with Filter & Sorting
    public void donationDonor() {
        ArrayList<DonationRecord> fullRecord = new ArrayList<DonationRecord>();
        ArrayList<DonationRecord> fullRecord2 = new ArrayList<DonationRecord>();
        ArrayList<DonationRecord> checkedList = new ArrayList<DonationRecord>();
        loadIntoDR(fullRecord);
        loadIntoDR(fullRecord2);
        System.out.println("\nDonation by Different Donor");
        if(fullRecord.isEmpty()){
            displayEmptyList();
        }else{
            for (DonationRecord record : fullRecord) {
                checkedList = new ArrayList<DonationRecord>();
                for (DonationRecord record2 : fullRecord2) {
                    if (record.getDonor().getId().equals(record2.getDonor().getId())) {
                        checkedList.add(record);
                    }
                }
                for (DonationRecord record3 : fullRecord) {
                    if (record.getDonor().getId().equals(record3.getDonor().getId())) {
                        fullRecord2.remove(record3);
                    }
                }
                displaySingleDonorRecords(checkedList, record.getDonor());
            }
        }
        enterContinue();
    }

    //Enter to Continue - Boundary
    public void enterContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter to back to Donation Page...");
        scanner.nextLine();
    }

    //Full Donation List with Filter & Sorting
    public void fullDonation() {
        ArrayList<DonationItem> itemlist = new ArrayList<DonationItem>();
        ArrayList<DonationItem> fullList = new ArrayList<DonationItem>();
        loadIntoAll(fullList);
        displayAllDonation(fullList);
        int filter = 0, sort = 0, option = 0;
        if(fullList.isEmpty()){
            enterContinue();
        }else{
            while (true) {
                if (filter == 0 && sort == 0) {
                    displayFullDonationActionMenu();
                    option = checkActionMenuWith7Op();
                    //implement what happen
                    if (option == 7) {
                        break;
                    } else if (option == 1) {
                        filter = 1;
                    } else if (option == 2) {
                        filter = 2;
                    } else if (option == 3) {
                        sort = 1;
                    } else if (option == 4) {
                        sort = 2;
                    } else if (option == 5) {
                        sort = 3;
                    } else {
                        sort = 4;
                    }
                } else if ((filter == 1 || filter == 2) && sort == 0) {
                    displayFullDonationActionMenuAftFilter();
                    option = checkActionMenuWith6Op();
                    if (option == 1) {
                        filter = 0;
                    } else if (option == 2) {
                        sort = 1;
                    } else if (option == 3) {
                        sort = 2;
                    } else if (option == 4) {
                        sort = 3;
                    } else if (option == 5) {
                        sort = 4;
                    } else {
                        break;
                    }
                } else {
                    if (filter == 0 && (sort >= 1 || sort <= 4)) {
                        displayFullDonationActionMenuAftSort();
                        option = checkActionMenuWith4Op();
                        if (option == 1) {
                            filter = 1;
                        } else if (option == 2) {
                            filter = 2;
                        } else if (option == 3) {
                            sort = 0;
                        }
                    } else {
                        displayFullDonationActionMenuAftFilterAftSort();
                        option = checkActionMenuWith4Op();
                        if (option == 1) {
                            filter = 0;
                        } else if (option == 2) {
                            sort = 0;
                        } else if (option == 3) {
                            sort = 0;
                            filter = 0;
                        }
                    }
                    if (option == 4) {
                        break;
                    }
                }
                if (filter == 1) {
                    itemlist = new ArrayList<DonationItem>();
                    int fcOption;
                    displayDonationCategory();
                    fcOption = checkDonationCategoryOption();
                    if (fcOption != 8) {
                        displayBasedOnCatOptionSort(fcOption, itemlist, sort);
                    } else {
                        break;
                    }
                } else if (filter == 0) {
                    fullList = new ArrayList<DonationItem>();
                    loadIntoAll(fullList);
                    sortBeforeDisplay(fullList, sort);
                    displayAllDonation(fullList);
                    System.out.println("yett2");
                } else {
                    //let user filter by quantity
                    int fqOption;
                    displayFilterQtyOp();
                    fqOption = checkActionMenuWith4Op();
                    if (fqOption != 4) {
                        filterByQuantity(fullList, sort, fqOption);
                        displayAllDonation(fullList);
                        System.out.println("yett");
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void sortBeforeDisplay(ArrayList<DonationItem> itemlist, int sort) {
        if (sort == 1) {
            itemlist.sort(Comparator.comparing(DonationItem::getItemCode));
        }
        if (sort == 2) {
            itemlist.sort(Comparator.comparing(DonationItem::getItemCode, (ic1, ic2) -> {
                return ic2.compareTo(ic1);
            }));
        }
        if (sort == 3) {
            itemlist.sort(Comparator.comparing(DonationItem::getQuantity));
            ArrayList<DonationItem> itemlist2 = new ArrayList<DonationItem>();
            for (DonationItem item : itemlist) {
                itemlist2.add(item);
            }
            for (DonationItem item : itemlist2) {
                if (item instanceof Money) {
                    int index = 0;
                    for (DonationItem item2 : itemlist2) {
                        if (!(item2 instanceof Money)) {
                            System.out.println(index + "yeet" + (Double.parseDouble(String.valueOf(item2.getQuantity())) > ((Money) item).getAmount()));
                            if (Double.parseDouble(String.valueOf(item2.getQuantity())) > ((Money) item).getAmount()) {
                                itemlist.remove(item);
                                itemlist.add(index - 1, item);
                                break;
                            }
                        } else {
                            if (((Money) item2).getAmount() > ((Money) item).getAmount()) {
                                itemlist.remove(item);
                                itemlist.add(index - 1, item);
                                break;
                            }

                        }
                        index++;
                        if (index == itemlist2.size() + 1) {
                            itemlist.remove(item);
                            itemlist.add(item);
                        }
                    }
                }
            }

        }
        if (sort == 4) {
            itemlist.sort(Comparator.comparing(DonationItem::getQuantity, (ic1, ic2) -> {
                return ic2.compareTo(ic1);
            }));

            ArrayList<DonationItem> itemlist2 = new ArrayList<DonationItem>();
            for (DonationItem item : itemlist) {
                itemlist2.add(item);
            }
            for (DonationItem item : itemlist2) {
                if (item instanceof Money) {
                    int index = 0;
                    for (DonationItem item2 : itemlist2) {
                        if (!(item2 instanceof Money)) {
                            if (Double.parseDouble(String.valueOf(item2.getQuantity())) < ((Money) item).getAmount()) {
                                itemlist.remove(item);
                                itemlist.add(index, item);
                                break;
                            }
                        } else {
                            if (((Money) item2).getAmount() < ((Money) item).getAmount()) {
                                itemlist.remove(item);
                                itemlist.add(index, item);
                                break;
                            }
                        }
                        index++;
                        if (index == itemlist2.size() + 1) {
                            itemlist.remove(item);
                            itemlist.add(item);
                        }
                    }
                }
            }
        }
    }
//Display Based On Option - Boundary

    public void displayBasedOnCatOptionSort(int option, ArrayList<DonationItem> itemlist, int sort) {
        if (option == 7) {
            System.out.println("\nMoney List");
            loadIntoMoney(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                displayMoney(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 6) {
            System.out.println("\nMedicine List");
            loadIntoMedicine(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                displayMedicine(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 5) {
            System.out.println("\nMedical Device List");
            loadIntoMedicalDevice(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                displayMedicalDevice(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 4) {
            System.out.println("\nPersonal Care List");
            loadIntoPersonalCare(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                displayPersonalCare(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 3) {
            System.out.println("\nClothing List");
            loadIntoClothing(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                displayClothing(itemlist);
            } else {
                displayEmptyList();
            }
        } else if (option == 2) {
            System.out.println("\nBeverage List");
            loadIntoBeverage(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                displayBeverage(itemlist);
            } else {
                displayEmptyList();
            }
        } else {
            System.out.println("\nFood List");
            loadIntoFood(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                displayFood(itemlist);
            } else {
                displayEmptyList();
            }
        }
    }

    public void displayFullDonationActionMenuAftFilter() {
        System.out.println("\nAction:");
        System.out.println("1. Cancel Filter");
        System.out.println("2. Sort by Item Code in Ascending Order");
        System.out.println("3. Sort by Item Code in Descending Order");
        System.out.println("4. Sort by Quantity in Ascending Order");
        System.out.println("5. Sort by Quantity in Descending Order");
        System.out.println("6. Back to Support X Home");
    }

    public void displayFullDonationActionMenuAftFilterAftSort() {
        System.out.println("\nAction:");
        System.out.println("1. Cancel Filter");
        System.out.println("2. Cancel Sort");
        System.out.println("3. Cancel Sort and Filter");
        System.out.println("4. Back to Support X Home");
    }

    public void displayFilterQtyOp() {
        System.out.println("\nFilter Action:");
        System.out.println("1. Larger Than a");
        System.out.println("2. Between Range (Inclusive) a<=Quantity<=b");
        System.out.println("3. Smaller Than a");
        System.out.println("4. Back to Support X Home");
    }

    public String inputNumA() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a: ");
        return scanner.nextLine();
    }

    public String inputNumB() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter b: ");
        return scanner.nextLine();
    }

    public boolean checkNumA(String num) {
        try {
            if (Integer.parseInt(num) > 0) {
                return true;
            } else {
                System.out.println("Invalid Number Entered! Number must be more than 0. ");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Invalid Number Entered! Number must be integer. ");
            return false;
        }
    }

    public boolean checkNumB(String num, int a) {
        try {
            if (Integer.parseInt(num) > 0 && Integer.parseInt(num) <= a) {
                return true;
            } else {
                System.out.println("Invalid Number Entered! Number must be more than 0. ");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Invalid Number Entered! Number must be integer. ");
            return false;
        }
    }

    public void filterByQuantity(ArrayList<DonationItem> itemlist, int sort, int fq) {
        ArrayList<DonationItem> itemlist2 = new ArrayList<DonationItem>();
        for (DonationItem item : itemlist) {
            itemlist2.add(item);
        }
        int a = 0, b = 0;
        String _temp;
        while (true) {
            _temp = inputNumA();
            if (checkNumA(_temp)) {
                a = Integer.parseInt(_temp);
                break;
            }
        }
        if (fq == 2) {
            while (true) {
                _temp = inputNumB();
                if (checkNumB(_temp, a)) {
                    b = Integer.parseInt(_temp);
                    break;
                }
            }
        }
        for (DonationItem item : itemlist2) {
            if (fq == 1) {
                if (item.getQuantity() <= a) {
                    itemlist.remove(item);
                }
            } else if (fq == 2) {
                if (item.getQuantity() < a || item.getQuantity() > b) {
                    itemlist.remove(item);
                }
            } else {
                if (item.getQuantity() >= a) {
                    itemlist.remove(item);
                }
            }
        }
        sortBeforeDisplay(itemlist, sort);

    }

    public void displayFullDonationActionMenuAftSort() {
        System.out.println("\nAction:");
        System.out.println("1. Filter by Category");
        System.out.println("2. Filter by Quantity");
        System.out.println("3. Cancel Sort");
        System.out.println("4. Back to Support X Home");
    }

    public void displayFullDonationActionMenu() {
        System.out.println("\nAction:");
        System.out.println("1. Filter by Category");
        System.out.println("2. Filter by Quantity");
        System.out.println("3. Sort by Item Code in Ascending Order");
        System.out.println("4. Sort by Item Code in Descending Order");
        System.out.println("5. Sort by Quantity in Ascending Order");
        System.out.println("6. Sort by Quantity in Descending Order");
        System.out.println("7. Back to Support X Home");
    }

    public int checkActionMenuWith6Op() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-6): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 6) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-6).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-6).");
            }
        }
        return option;
    }

    public int checkActionMenuWith7Op() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-7): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 7) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-7).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-7).");
            }
        }
        return option;
    }

    public int checkActionMenuWith4Op() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-4): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 4) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-4).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-4).");
            }
        }
        return option;
    }

    //Summary Report
    public void donationReport() {
        displaySummaryReportChoices();
        int option = checkActionMenuWith3Op();
        if (option == 1) {
            displayReport1();
            enterContinue();
        }
        if (option == 2) {
            displayReport2();
            enterContinue();
        }
    }

    //Display Report Choice - Boundary
    public void displaySummaryReportChoices() {
        System.out.println("\nView Summary Report");
        System.out.println("1. Report of Expired Items by Category as of " + LocalDate.now());
        System.out.println("2. Top 3 Most Donated Non-Money Item Category Report as of " + LocalDate.now());
        System.out.println("3. Back to Donation Page");
    }

    //Report 1 - Report of Expired Items by Category as of today
    public void displayReport1() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //display expired item by category
        //display Topic
        displayReport1Header();
        //food
        ArrayList<DonationItem> foodList = new ArrayList<DonationItem>();
        loadIntoFood(foodList);
        //beverage
        ArrayList<DonationItem> beverageList = new ArrayList<DonationItem>();
        loadIntoBeverage(beverageList);
        //personal care
        ArrayList<DonationItem> pcList = new ArrayList<DonationItem>();
        loadIntoPersonalCare(pcList);
        //medicine
        ArrayList<DonationItem> medList = new ArrayList<DonationItem>();
        loadIntoMedicine(medList);
        ArrayList<DonationItem> expiredItemList1 = new ArrayList<DonationItem>();
        ArrayList<DonationItem> expiredItemList2 = new ArrayList<DonationItem>();
        ArrayList<DonationItem> expiredItemList3 = new ArrayList<DonationItem>();
        ArrayList<DonationItem> expiredItemList4 = new ArrayList<DonationItem>();

        for (DonationItem food : foodList) {
            if ((((Food) food).getExpiryDate().isBefore(LocalDate.now()))) {
                expiredItemList1.add(food);
            }
        }
        for (DonationItem bg : beverageList) {
            if ((((Beverage) bg).getExpiryDate().isBefore(LocalDate.now()))) {
                expiredItemList2.add(bg);
            }
        }
        for (DonationItem pc : pcList) {
            if ((((PersonalCare) pc).getExpiryDate().isBefore(LocalDate.now())) && !(((PersonalCare) pc).getExpiryDate().equals(LocalDate.parse("2000-12-31", dateFormat)))) {
                expiredItemList3.add(pc);
            }
        }
        for (DonationItem med : medList) {
            if ((((Medicine) med).getExpiryDate().isBefore(LocalDate.now()))) {
                expiredItemList4.add(med);
            }
        }
        if (!expiredItemList1.isEmpty()) {
            displayReport1Food(expiredItemList1);
        }
        if (!expiredItemList2.isEmpty()) {
            displayReport1Bg(expiredItemList2);
        }
        if (!expiredItemList3.isEmpty()) {
            displayReport1Pc(expiredItemList3);
        }
        if (!expiredItemList4.isEmpty()) {
            displayReport1Med(expiredItemList4);
        }
        if (expiredItemList1.isEmpty() && expiredItemList2.isEmpty() && expiredItemList3.isEmpty() && expiredItemList4.isEmpty()) {
            displayReport1Ntg();
        }

    }

    public void displayReport1Ntg() {
        System.out.println("No item expired as of " + LocalDate.now());
        System.out.print("\n");
    }

    public void displayReport1Food(ArrayList<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Food");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        for (DonationItem food : expiredItemList) {
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", food.getItemCode(), food.getItemName(), ((Food) food).getExpiryDate(), food.getQuantity());
            total += food.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Bg(ArrayList<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Beverage");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        for (DonationItem bg : expiredItemList) {
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", bg.getItemCode(), bg.getItemName(), ((Beverage) bg).getExpiryDate(), bg.getQuantity());
            total += bg.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Pc(ArrayList<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Personal Care");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        for (DonationItem pc : expiredItemList) {
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", pc.getItemCode(), pc.getItemName(), ((PersonalCare) pc).getExpiryDate(), pc.getQuantity());
            total += pc.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Med(ArrayList<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Medicine");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        for (DonationItem med : expiredItemList) {
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", med.getItemCode(), med.getItemName(), ((Medicine) med).getExpiryDate(), med.getQuantity());
            total += med.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Header() {
        System.out.print("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print(" ");
        }
        System.out.println("Report of Expired Items by Category as of " + LocalDate.now());
        System.out.print("\n");
    }

    //Report 2 - Top 3 Most Donated Non-Money Item Category Report in 2024
    public void displayReport2() {
        ArrayList<DonationItem> fullList = new ArrayList<DonationItem>();
        loadIntoAll(fullList);
        int total[] = new int[6];
        int max[] = {0, 0, 0};
        int maxIndex[] = {0, 0, 0};
        total[0] = 0;
        total[1] = 0;
        total[2] = 0;
        total[3] = 0;
        total[4] = 0;
        total[5] = 0;
        for (DonationItem item : fullList) {
            if (item instanceof Food) {
                total[0] += item.getQuantity();
            }
            if (item instanceof Beverage) {
                total[1] += item.getQuantity();
            }
            if (item instanceof Clothing) {
                total[2] += item.getQuantity();
            }
            if (item instanceof PersonalCare) {
                total[3] += item.getQuantity();
            }
            if (item instanceof MedicalDevice) {
                total[4] += item.getQuantity();
            }
            if (item instanceof Medicine) {
                total[5] += item.getQuantity();
            }
        }
        for (int i = 0; i < 6; i++) {
            if (total[i] > max[0]) {
                max[2] = max[1];
                max[1] = max[0];
                max[0] = total[i];
                maxIndex[2] = maxIndex[1];
                maxIndex[1] = maxIndex[0];
                maxIndex[0] = i;
            } else if (total[i] > max[1]||total[i] == max[0]) {
                max[2] = max[1];
                max[1] = total[i];
                maxIndex[2] = maxIndex[1];
                maxIndex[1] = i;
            } else if (total[i] > max[2]||total[i] ==max[1]) {
                max[2] = total[i];
                maxIndex[2] = i;
            }
        }
        displayReport2Header();
        displayReport2Content(max, maxIndex);
    }

    public void displayReport2Header() {
        System.out.print("\n");
        for (int i = 0; i < 11; i++) {
            System.out.print(" ");
        }
        System.out.println("Top 3 Most Donated Non-Money Item Category Report");
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.println("as of " + LocalDate.now());
    }

    public void displayReport2Content(int max[], int maxIndex[]) {
        for (int i = 0; i < 71; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-5s |%-30s |%-30s|\n", "No.", "Item Category", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 6; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 31; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 30; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        for (int i = 0; i < 3; i++) {
            switch (maxIndex[i]) {
                case 0:
                    System.out.printf("|%-5s |%-30s |%-30s|\n", i + 1, "Food", max[i]);
                    break;
                case 1:
                    System.out.printf("|%-5s |%-30s |%-30s|\n", i + 1, "Beverage", max[i]);
                    break;
                case 2:
                    System.out.printf("|%-5s |%-30s |%-30s|\n", i + 1, "Clothing", max[i]);
                    break;
                case 3:
                    System.out.printf("|%-5s |%-30s |%-30s|\n", i + 1, "Personal Care", max[i]);
                    break;
                case 4:
                    System.out.printf("|%-5s |%-30s |%-30s|\n", i + 1, "Medical Device", max[i]);
                    break;
                default:
                    System.out.printf("|%-5s |%-30s |%-30s|\n", i + 1, "Medicine", max[i]);
            }
        }
        System.out.print("|");
        for (int i = 0; i < 6; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 31; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 30; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        System.out.print("\n\n");
    }

    public int checkActionMenuWith3Op() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            try {
                System.out.print("\nPlease select an option (1-3): ");
                option = Integer.parseInt(scanner.nextLine());
                if (option >= 1 && option <= 3) {
                    break;
                } else {
                    System.out.println("Sorry! Invalid Option. Please enter a valid option (1-3).");
                }
            } catch (Exception ex) {
                System.out.println("Sorry! Invalid Option. Please enter a valid option (1-3).");
            }
        }
        return option;
    }
}
