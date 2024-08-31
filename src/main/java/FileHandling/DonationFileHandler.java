package FileHandling;
/*
 *  author: Ko Jie Qi
 *  ID: 2307589
 * */
import Control.DonorFunctions;
import Entity.Beverage;
import Entity.Clothing;
import Entity.DonationItem;
import Entity.DonationRecord;
import Entity.Donor;
import Entity.Food;
import Entity.MedicalDevice;
import Entity.Medicine;
import Entity.Money;
import Entity.PersonalCare;
import Libraries.ArrayList;
import Libraries.ListInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

public class DonationFileHandler {

    private DonorFunctions donorHandling = new DonorFunctions();

    //Load Files
    //Load Donation Record File
    public void loadIntoDR(ListInterface<DonationRecord> recordList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int recordID;
        Donor donor;
        ListInterface<DonationItem> itemList = new ArrayList<>();
        loadIntoAll(itemList);
        ListInterface<DonationItem> recordItemList;
        ListInterface<Integer> qty;
        ListInterface<Double> amt;
        LocalDateTime donationDateTime;
        int nextRecordID = 1;
        try {
            File myObj = new File("DonationRecord.txt");
            myObj.createNewFile();
            DonationRecord.setNextRecordID(1);

            try {
                Scanner readerFile = new Scanner(myObj);
                int index = 0;
                while (readerFile.hasNextLine()) {
                    recordItemList = new ArrayList<>();
                    qty = new ArrayList<>();
                    amt = new ArrayList<>();
                    String[] dataInLine = readerFile.nextLine().split("#");
                    recordID = Integer.parseInt(dataInLine[0]);
                    String _donorID = dataInLine[1];
                    String[] item2 = dataInLine[2].split(",");
                    String[] qty2 = dataInLine[3].split(",");
                    String[] amt2 = dataInLine[4].split(",");
                    donationDateTime = LocalDateTime.parse(dataInLine[5], dateFormat);
                    if (!qty2[0].equals("")) {
                        for (String qty3 : qty2) {
                            qty.add(Integer.valueOf(qty3));
                        }
                    }
                    if (!amt2[0].equals("")) {
                        for (String amt3 : amt2) {
                            amt.add(Double.valueOf(amt3));
                        }
                    }
                    for (String code : item2) {
                        Iterator<DonationItem> iterator = itemList.iterator();
                        while (iterator.hasNext()) {
                            DonationItem item = iterator.next();
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
                    DonationRecord.setNextRecordID(recordID + 1);
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

    public void loadBackDRFile(ListInterface<DonationRecord> recordList) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            FileWriter writeFile = new FileWriter("DonationRecord.txt");
            Iterator<DonationRecord> iterator = recordList.iterator();
            while (iterator.hasNext()) {
                DonationRecord record = iterator.next();
                String line = record.getRecordID() + "#" + record.getDonor().getId() + "#";
                if (record.getItem().size() != 0) {
                    for (int i = 0; i < record.getItem().size(); i++) {
                        if (i != record.getItem().size() - 1) {
                            line += record.getItem().get(i).getItemCode() + ",";
                        } else {
                            line += record.getItem().get(i).getItemCode() + "#";
                        }
                    }
                }else{
                    line += "#";
                }
                if (record.getQty().size() != 0) {
                    for (int i = 0; i < record.getQty().size(); i++) {
                        if (i != record.getQty().size() - 1) {
                            line += record.getQty().get(i) + ",";
                        } else {
                            line += record.getQty().get(i) + "#";
                        }
                    }
                }else{
                    line += "#";
                }
                if (record.getAmount().size() != 0) {
                    for (int i = 0; i < record.getAmount().size(); i++) {
                        if (i != record.getAmount().size() - 1) {
                            line += record.getAmount().get(i) + ",";
                        } else {
                            line += record.getAmount().get(i) + "#";
                        }
                    }
                }else{
                    line += "#";
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
    public void loadIntoAll(ListInterface<DonationItem> itemList) {
        loadIntoFood(itemList);
        loadIntoBeverage(itemList);
        loadIntoClothing(itemList);
        loadIntoMedicalDevice(itemList);
        loadIntoMedicine(itemList);
        loadIntoPersonalCare(itemList);
        loadIntoMoney(itemList);
    }

    //Food
    public void loadIntoFood(ListInterface<DonationItem> itemList) {
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

    public void loadBackFoodFile(ListInterface<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Food.txt");
            Iterator<DonationItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof Food) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity()
                            + "#" + ((Food) item).getNetWeight() + "#" + ((Food) item).getExpiryDate() + "#"
                            + ((Food) item).getFoodType() + "#" + ((Food) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Beverage
    public void loadIntoBeverage(ListInterface<DonationItem> itemList) {
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

    public void loadBackBeverageFile(ListInterface<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Beverage.txt");
            Iterator<DonationItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof Beverage) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity()
                            + "#" + ((Beverage) item).getNetVolume() + "#" + ((Beverage) item).getExpiryDate()
                            + "#" + ((Beverage) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Clothing
    public void loadIntoClothing(ListInterface<DonationItem> itemList) {
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

    public void loadBackClothingFile(ListInterface<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Clothing.txt");
            Iterator<DonationItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof Clothing) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity()
                            + "#" + ((Clothing) item).getSize() + "#" + ((Clothing) item).getClothingCategory()
                            + "#" + ((Clothing) item).getGender() + "#" + ((Clothing) item).getAge() + "#"
                            + ((Clothing) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Personal Care
    public void loadIntoPersonalCare(ListInterface<DonationItem> itemList) {
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

    public void loadBackPersonalCareFile(ListInterface<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("PersonalCare.txt");
            Iterator<DonationItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof PersonalCare) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#"
                            + ((PersonalCare) item).getNetWeight() + "#" + ((PersonalCare) item).getExpiryDate() + "#"
                            + ((PersonalCare) item).getGender() + "#" + ((PersonalCare) item).getAge() + "#"
                            + ((PersonalCare) item).getPersonalCareCategory() + "#" + ((PersonalCare) item).getVenueCode() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Medical Device
    public void loadIntoMedicalDevice(ListInterface<DonationItem> itemList) {
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

    public void loadBackMedicalDeviceFile(ListInterface<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("MedicalDevice.txt");
            Iterator<DonationItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof MedicalDevice) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#"
                            + ((MedicalDevice) item).getVenueCode() + "#" + ((MedicalDevice) item).getDescription() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Medicine
    public void loadIntoMedicine(ListInterface<DonationItem> itemList) {
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

    public void loadBackMedicineFile(ListInterface<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Medicine.txt");
            Iterator<DonationItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof Medicine) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#"
                            + ((Medicine) item).getNetWeight() + "#" + ((Medicine) item).getExpiryDate() + "#"
                            + ((Medicine) item).getGender() + "#" + ((Medicine) item).getAge() + "#" + ((Medicine) item).getVenueCode()
                            + "#" + ((Medicine) item).getDosageForm() + "#" + ((Medicine) item).getDescription() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Money
    public void loadIntoMoney(ListInterface<DonationItem> itemList) {
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

    public void loadBackMoneyFile(ListInterface<DonationItem> itemList) {
        try {
            FileWriter writeFile = new FileWriter("Money.txt");
            Iterator<DonationItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof Money) {
                    writeFile.write(item.getItemCode() + "#" + item.getItemName() + "#" + item.getQuantity() + "#"
                            + ((Money) item).getSource() + "#" + ((Money) item).getAmount() + "\n");
                }
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public void loadVenueCode(String[] _vc){
        int index=0;
        try {
            File myObj = new File("Venue.txt");
            Scanner readerFile = new Scanner(myObj);
            while (readerFile.hasNextLine()) {
                String[] venueList = readerFile.nextLine().split("#");
                _vc[index]=venueList[0];
                index++;
            }
            readerFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void loadVenueCodeWithPlace(String[][] _vc){
        int rowindex=0;
        try {
            File myObj = new File("Venue.txt");
            Scanner readerFile = new Scanner(myObj);
            while (readerFile.hasNextLine()) {
                String[] venueList = readerFile.nextLine().split("#");
                _vc[rowindex][0]=venueList[0];
                _vc[rowindex][1]=venueList[1];
                rowindex++;
            }
            readerFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
