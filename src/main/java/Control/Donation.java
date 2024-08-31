/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
//import entity

import Boundary.DonationUI;
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
import FileHandling.DonationFileHandler;

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
import Libraries.GeneralFunction;
import Libraries.ListInterface;
import static Main.Event.EventHandler.searchEventByEventID;
import Utilities.Message;
import java.util.Iterator;
//will be removed for boundary
import java.util.Scanner;

/**
 *
 * @author vivia
 */
public class Donation {

    public Donation() {
    }

    private DonorFunctions donorHandling = new DonorFunctions();
    private DonationFileHandler fileHandler = new DonationFileHandler();
    private Message msgHandling = new Message();
    private DonationUI donationUI = new DonationUI();
    private GeneralFunction generalFunc = new GeneralFunction();

    //Donation Home Page
    public void donationHome() {

        int option;
        while (true) {
            donationUI.displayMenuDonationHome();
            option = checkMenuWithOp(9);
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

    public void addItemToRecord(ListInterface<DonationItem> recordItem, ListInterface<Integer> recordQty, DonationItem item, int qty) {
        recordItem.add(item);
        recordQty.add(qty);
    }

    public void addItemToRecord(ListInterface<DonationItem> recordItem, ListInterface<Double> recordAmt, DonationItem item, double amt) {
        recordItem.add(item);
        recordAmt.add(amt);
    }

    //Add Donation
    public void addDonation() {
        int option;
        String reply;
        boolean continueDonationAdd = true;
        donationUI.displayAddDonationInstruction();
        Donor donor = getDonorIDforDonation();
        ListInterface<DonationRecord> dRecordLIST = new ArrayList<>();
        fileHandler.loadIntoDR(dRecordLIST);
        if (donor != null) {
            DonationRecord donationRecord = new DonationRecord();
            donationRecord.setDonor(donor);
            while (true) {
                donationUI.displayAddPageHeader();
                donationUI.displayDonationCategory();
                option = checkMenuWithOp(8);
                if (option == 8) {
                    if (!donationRecord.getItem().isEmpty()) {
                        //add record
                        dRecordLIST.add(donationRecord);
                        fileHandler.loadBackDRFile(dRecordLIST);
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
                        reply = donationUI.inputAskConAdd();
                        if (reply.equalsIgnoreCase("N") || reply.equalsIgnoreCase("Y")) {
                            if (reply.equalsIgnoreCase("N")) {
                                continueDonationAdd = false;
                            }
                            break;
                        } else {
                            msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                        }
                    } catch (Exception ex) {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                }
                if (!continueDonationAdd) {
                    if (!donationRecord.getItem().isEmpty()) {
                        //add record
                        dRecordLIST.add(donationRecord);
                        fileHandler.loadBackDRFile(dRecordLIST);
                        DonationRecord.setNextRecordID(DonationRecord.getNextRecordID() + 1);
                    }
                    break;
                }
            }
        }
    }

    //Add Food
    public boolean addFood(ListInterface<DonationItem> recordItem, ListInterface<Integer> recordQty) {
        ListInterface<DonationItem> foodList = new ArrayList<>();
        fileHandler.loadIntoFood(foodList);
        donationUI.displayInstructionAddFood();

        //Choose to add amount or new item
        //Add New Item
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
                    _temp = donationUI.inputDetectedSame();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            Iterator<DonationItem> iterator = foodList.iterator();
                            while (iterator.hasNext()) {
                                DonationItem item = iterator.next();
                                if (item.getItemName().equals(_name) && ((Food) item).getExpiryDate().equals(_expiryDate)
                                        && ((Food) item).getNetWeight() == _netWeight && ((Food) item).getFoodType().equals(_foodType)
                                        && ((Food) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            msgHandling.displaySuccessMessage("Food quantity added successfully.");
                            fileHandler.loadBackFoodFile(foodList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Food", foodList);
            Food newItem = new Food(_netWeight, _expiryDate, _foodType, _venueCode, _code, _name, _qty);
            foodList.add(newItem);
            fileHandler.loadBackFoodFile(foodList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            msgHandling.displaySuccessMessage("New Food registered and added successfully.");
            return true;
        }

    }

    //Validate Name - Control
    public String getNvalidateName() {
        String _name;
        while (true) {
            _name = donationUI.inputName();
            if (_name.isEmpty()) {
                msgHandling.displayGeneralErrorMsg("Invalid Item Name. Please do not leave the field empty.");
            } else {
                return _name;
            }
        }
    }

    //Validate Description - Control
    public String getNvalidateDescription() {
        String _description;
        while (true) {
            _description = donationUI.inputDescription();
            if (_description.isEmpty()) {
                msgHandling.displayGeneralErrorMsg("Invalid Description. Please do not leave the field empty.");
            } else {
                return _description;
            }
        }
    }

    //Validate Net Volume - Control
    public double getNvalidateNetVolume() {
        String _temp;
        double _netVolume;
        while (true) {
            try {
                _temp = donationUI.inputBeverageNetVolume();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _netVolume = Double.parseDouble(_temp);
                    if (_netVolume <= 0.0) {
                        msgHandling.displayGeneralErrorMsg("Invalid Beverage Net Volume. Please enter a net volume that is more than zero.");
                    } else {
                        return _netVolume;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Beverage Net Volume. Please enter a valid net volume.");
            }
        }
    }

    //Validate Net Weight - Control
    public double getNvalidateNetWeight(String donationCat) {
        String _temp;
        double _netWeight;
        while (true) {
            try {
                if (donationCat.equals("Food")) {
                    _temp = donationUI.inputFoodNetWeight();
                } else {
                    _temp = donationUI.inputNetWeight();
                }
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _netWeight = Double.parseDouble(_temp);
                    if (_netWeight <= 0.0 && _netWeight == -999) {
                        msgHandling.displayGeneralErrorMsg("Invalid Net Weight. Please enter a net weight that is more than zero.");
                    } else {
                        return _netWeight;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Net Weight. Please enter a valid net weight.");
            }
        }
    }

    //Validate Expiry Date - Boundary
    public LocalDate getNvalidateExpiryDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String _temp;
        LocalDate _expiryDate;
        while (true) {
            try {
                _temp = donationUI.inputExpiryDate();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    if (_temp.equals("-")) {
                        return LocalDate.parse("2000-12-31", dateFormat);
                    } else {
                        _expiryDate = LocalDate.parse(_temp, dateFormat);
                        if (!(_expiryDate.isAfter(LocalDate.now()))) {
                            msgHandling.displayGeneralErrorMsg("Invalid Expiry Date. Please enter a date that is after today.");
                        } else {
                            return _expiryDate;
                        }
                    }
                } else {
                    return LocalDate.parse("2000-12-30", dateFormat);
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Expiry Date. Please enter a valid expiry date in the form of (yyyy-mm-dd).");
            }

        }
    }

    //Validate Food Type - Control
    public String getNvalidateFoodType() {
        String _temp, _foodType;
        int _tempOption;
        while (true) {
            try {
                _temp = donationUI.inputFoodType();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 8)) {
                        msgHandling.displayGeneralErrorMsg("Invalid Food Type Option. Please enter a valid option (1-8).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Food Type Option. Please enter a valid option (1-8).");
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

    //Validate Clothing category - Control
    public String getNvalidateClothingCategory() {
        int _tempOption;
        String _temp, _clothCategory;
        while (true) {
            try {
                _temp = donationUI.inputClothingCategory();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 9)) {
                        msgHandling.displayGeneralErrorMsg("Invalid Clothing Category Option. Please enter a valid option (1-9).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Clothing Category Option. Please enter a valid option (1-9).");
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

    //Validate Gender - Control
    public String getNvalidateGender() {
        String _temp, _gender;
        int _tempOption;
        while (true) {
            try {
                _temp = donationUI.inputGender();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 3)) {
                        msgHandling.displayGeneralErrorMsg("Invalid Gender Option. Please enter a valid option (1-3).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Gender Option. Please enter a valid option (1-3).");
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

    //Validate Age - Control
    public String getNvalidateAge() {
        String _temp, _age;
        int _tempOption;
        while (true) {
            try {
                _temp = donationUI.inputAge();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 6)) {
                        msgHandling.displayGeneralErrorMsg("Invalid Age Group Option. Please enter a valid option (1-6).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Age Group Option. Please enter a valid option (1-6).");
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

    //Validate Size - Control
    public String getNvalidateSize(String _clothCategory, String _age, String _gender) {
        String _size;
        if (!_clothCategory.equals("Accessories") && (!_age.equals("Infants")) && (!_age.equals("Toddlers"))) {
            while (true) {
                try {
                    _size = donationUI.inputSize();
                    if (_size.isEmpty()) {
                        msgHandling.displayGeneralErrorMsg("Invalid Clothing Size. Please do not leave the field empty.");
                    } else {
                        if (!_clothCategory.equals("Footwear")) {
                            if ((!_size.equals("XS")) && (!_size.equals("S")) && (!_size.equals("M"))
                                    && (!_size.equals("L")) && (!_size.equals("XL")) && (!_size.equals("XXL")) && (!_size.equals("XXXL"))) {
                                msgHandling.displayGeneralErrorMsg("Invalid Clothing Size for " + _clothCategory + ". Please enter a valid size.");
                            } else {
                                break;
                            }
                        }
                        if (_clothCategory.equals("Footwear")) {
                            if (_age.equals("Kids")) {
                                if ((Integer.parseInt(_size) >= 10 && Integer.parseInt(_size) >= 13.5)
                                        || (Integer.parseInt(_size) >= 1 && Integer.parseInt(_size) >= 6)) {
                                    break;
                                } else {
                                    msgHandling.displayGeneralErrorMsg("Invalid Clothing Size for " + _age + " " + _clothCategory + ". Please enter a valid size.");
                                }
                            } else if (_age.equals("Teens")) {
                                if (Integer.parseInt(_size) >= 6 && Integer.parseInt(_size) >= 8) {
                                    break;
                                } else {
                                    msgHandling.displayGeneralErrorMsg("Invalid Clothing Size for " + _age + " " + _clothCategory + ". Please enter a valid size.");
                                }
                            } else {
                                if (_gender.equals("Men")) {
                                    if (Integer.parseInt(_size) >= 5 && Integer.parseInt(_size) >= 14) {
                                        break;
                                    } else {
                                        msgHandling.displayGeneralErrorMsg("Invalid Clothing Size for " + _gender + " " + _age + " "
                                                + _clothCategory + ". Please enter a valid size.");
                                    }
                                } else if (_gender.equals("Women")) {
                                    if (Integer.parseInt(_size) >= 2 && Integer.parseInt(_size) >= 10) {
                                        break;
                                    } else {
                                        msgHandling.displayGeneralErrorMsg("Invalid Clothing Size for " + _gender + " " + _age + " "
                                                + _clothCategory + ". Please enter a valid size.");
                                    }
                                } else {
                                    if (Integer.parseInt(_size) >= 2 && Integer.parseInt(_size) >= 14) {
                                        break;
                                    } else {
                                        msgHandling.displayGeneralErrorMsg("Invalid Clothing Size for " + _gender + " " + _age + " "
                                                + _clothCategory + ". Please enter a valid size.");
                                    }
                                }

                            }
                        }
                        if (_size.equalsIgnoreCase("X")) {
                            return "";
                        }
                    }
                } catch (Exception ex) {
                    msgHandling.displayGeneralErrorMsg("Invalid Clothing Size. Please enter a valid clothing size.");
                }
            }
        } else {
            donationUI.displayEmptySize();
            _size = "-";
        }
        return _size;
    }

    //Validate Personal Care Category - Control
    public String getNvalidatePCCat() {
        String _temp, _pcCategory;
        int _tempOption;
        while (true) {
            try {
                _temp = donationUI.inputPCCat();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 5)) {
                        msgHandling.displayGeneralErrorMsg("Invalid Personal Care Category Option. Please enter a valid option (1-5).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Personal Care Category Option. Please enter a valid option (1-5).");
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

    //Validate
    public String getNvalidateDosageForm() {
        String _temp, _dosageForm;
        int _tempOption;
        while (true) {
            try {
                _temp = donationUI.inputDosageForm();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _tempOption = Integer.parseInt(_temp);
                    if (!(_tempOption >= 1 && _tempOption <= 5)) {
                        msgHandling.displayGeneralErrorMsg("Invalid Dosage Form Option. Please enter a valid option (1-5).");
                    } else {
                        break;
                    }
                } else {
                    return "";
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Dosage Form Option. Please enter a valid option (1-5).");
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

    //Validate Quantity - Control
    public int getNvalidateQuantity() {
        String _temp;
        int _qty;
        while (true) {
            try {
                _temp = donationUI.inputQty();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _qty = Integer.parseInt(_temp);
                    if (_qty <= 0) {
                        msgHandling.displayGeneralErrorMsg("Invalid Quantity. Please enter a quantity that is more than zero.");
                    } else {
                        return _qty;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Quantity. Please enter a valid quantity.");
            }
        }
    }

    //Validate
    public double getNvalidateAmt() {
        String _temp;
        double _amount;
        while (true) {
            try {
                _temp = donationUI.inputAmt();
                if (!(_temp.equalsIgnoreCase("X"))) {
                    _amount = Double.parseDouble(_temp);
                    if (_amount <= 0.0) {
                        msgHandling.displayGeneralErrorMsg("Invalid Money Amount. Please enter an amount that is more than zero.");
                    } else {
                        return _amount;
                    }
                } else {
                    return 0;
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Money Amount. Please enter a valid amount.");
            }
        }
    }

    //Validate Source - Control
    public String getNvalidateSource() {
        String _source;
        while (true) {
            _source = donationUI.inputSource();
            if (!(_source.equalsIgnoreCase("X"))) {
                if (!checkVenueCode(_source) && !checkEventCode(_source) && !_source.equals("E001")) {
                    msgHandling.displayGeneralErrorMsg("Invalid Source. Please enter a source.");
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
            _venueCode = donationUI.inputVenueCode();
            if (!(_venueCode.equalsIgnoreCase("X"))) {
                if (!checkVenueCode(_venueCode)) {
                    msgHandling.displayGeneralErrorMsg("Invalid Venue Code. Please enter a venue code.");
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
        String[] venueList = new String[3];
        fileHandler.loadVenueCode(venueList);
        for (String venue : venueList) {
            if (_vc.equals(venue)) {
                return true;
            }
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
    public boolean checkExistBeforeFood(ListInterface<DonationItem> foodList, String _name, LocalDate _expiryDate, double _netWeight,
            String _foodType, String _venueCode) {
        boolean exist = false;
        Iterator<DonationItem> iterator = foodList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            if (item.getItemName().equals(_name) && ((Food) item).getExpiryDate().equals(_expiryDate)
                    && ((Food) item).getNetWeight() == _netWeight && ((Food) item).getFoodType().equals(_foodType)
                    && ((Food) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Beverage Exist - Control
    public boolean checkExistBeforeBeverage(ListInterface<DonationItem> beverageList, String _name, LocalDate _expiryDate, double _netVolume, String _venueCode) {
        boolean exist = false;
        Iterator<DonationItem> iterator = beverageList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            if (item.getItemName().equals(_name) && ((Beverage) item).getExpiryDate().equals(_expiryDate)
                    && ((Beverage) item).getNetVolume() == _netVolume && ((Beverage) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Clothing Exist - Control
    public boolean checkExistBeforeClothing(ListInterface<DonationItem> clothingList, String _name, String _size,
            String _clothCategory, String _age, String _gender, String _venueCode) {
        boolean exist = false;
        Iterator<DonationItem> iterator = clothingList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            if (item.getItemName().equals(_name) && ((Clothing) item).getSize().equals(_size) && ((Clothing) item).getClothingCategory().equals(_clothCategory)
                    && ((Clothing) item).getAge().equals(_age) && ((Clothing) item).getGender().equals(_gender)
                    && ((Clothing) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Personal Care Exist - Control
    public boolean checkExistBeforePersonalCare(ListInterface<DonationItem> personalCareList, String _name,
            LocalDate _expiryDate, double _netWeight, String _pcCategory, String _age, String _gender, String _venueCode) {
        boolean exist = false;
        Iterator<DonationItem> iterator = personalCareList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            if (item.getItemName().equals(_name) && ((PersonalCare) item).getExpiryDate().equals(_expiryDate)
                    && ((PersonalCare) item).getNetWeight() == (_netWeight) && ((PersonalCare) item).getPersonalCareCategory().equals(_pcCategory)
                    && ((PersonalCare) item).getAge().equals(_age) && ((PersonalCare) item).getGender().equals(_gender)
                    && ((PersonalCare) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Medical Device Exist - Control
    public boolean checkExistBeforeMedicalDevice(ListInterface<DonationItem> medicalDeviceList, String _name, String _venueCode) {
        boolean exist = false;
        Iterator<DonationItem> iterator = medicalDeviceList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            if (item.getItemName().equals(_name) && ((MedicalDevice) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Check Medicine Exist - Control
    public boolean checkExistBeforeMedicine(ListInterface<DonationItem> medicineList, String _name, LocalDate _expiryDate,
            double _netWeight, String _dosageForm, String _age, String _gender, String _venueCode) {
        boolean exist = false;
        Iterator<DonationItem> iterator = medicineList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            if (item.getItemName().equals(_name) && ((Medicine) item).getExpiryDate().equals(_expiryDate)
                    && ((Medicine) item).getNetWeight() == (_netWeight) && ((Medicine) item).getDosageForm().equals(_dosageForm)
                    && ((Medicine) item).getAge().equals(_age) && ((Medicine) item).getGender().equals(_gender)
                    && ((Medicine) item).getVenueCode().equals(_venueCode)) {
                exist = true;
                break;
            }

        }
        return exist;
    }

    //Check Money Exist - Control
    public boolean checkExistBeforeMoney(ListInterface<DonationItem> moneyList, String _source) {
        boolean exist = false;
        Iterator<DonationItem> iterator = moneyList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            if (((Money) item).getSource().equals(_source)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    //Generate Item Code
    public String generateItemCode(String itemCat, ListInterface<DonationItem> itemList) {
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

    public int generateRandNumber(ListInterface<DonationItem> itemList) {
        int code;
        boolean repeated = false;
        do {
            repeated = false;
            code = 1 + (int) (Math.random() * (999));
            if (!itemList.isEmpty()) {
                Iterator<DonationItem> iterator = itemList.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
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

    //Add Beverage
    public boolean addBeverage(ListInterface<DonationItem> recordItem, ListInterface<Integer> recordQty) {
        ListInterface<DonationItem> beverageList = new ArrayList<>();
        fileHandler.loadIntoBeverage(beverageList);
        donationUI.displayInstructionAddBeverage();

        //Choose to add amount or new item
        //Add New Item
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
                    _temp = donationUI.inputDetectedSame();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            Iterator<DonationItem> iterator = beverageList.iterator();
                            while (iterator.hasNext()) {
                                DonationItem item = iterator.next();
                                if (item.getItemName().equals(_name) && ((Beverage) item).getExpiryDate().equals(_expiryDate)
                                        && ((Beverage) item).getNetVolume() == _netVolume && ((Beverage) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            msgHandling.displaySuccessMessage("Beverage quantity added successfully.");
                            fileHandler.loadBackBeverageFile(beverageList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Beverage", beverageList);
            Beverage newItem = new Beverage(_netVolume, _expiryDate, _venueCode, _code, _name, _qty);
            beverageList.add(newItem);
            fileHandler.loadBackBeverageFile(beverageList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            msgHandling.displaySuccessMessage("New Beverage registered and added successfully.");
            return true;
        }

    }

    //Add Clothing
    public boolean addClothing(ListInterface<DonationItem> recordItem, ListInterface<Integer> recordQty) {
        ListInterface<DonationItem> clothingList = new ArrayList<>();
        fileHandler.loadIntoClothing(clothingList);
        donationUI.displayInstructionAddClothing();

        //Choose to add amount or new item
        //Add New Item
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
                    _temp = donationUI.inputDetectedSame();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            Iterator<DonationItem> iterator = clothingList.iterator();
                            while (iterator.hasNext()) {
                                DonationItem item = iterator.next();
                                if (item.getItemName().equals(_name) && ((Clothing) item).getSize().equals(_size)
                                        && ((Clothing) item).getClothingCategory().equals(_clothCategory)
                                        && ((Clothing) item).getAge().equals(_age) && ((Clothing) item).getGender().equals(_gender)
                                        && ((Clothing) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            msgHandling.displaySuccessMessage("Clothing quantity added successfully.");
                            fileHandler.loadBackClothingFile(clothingList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Clothing", clothingList);
            Clothing newItem = new Clothing(_size, _clothCategory, _gender, _age, _venueCode, _code, _name, _qty);
            clothingList.add(newItem);
            fileHandler.loadBackClothingFile(clothingList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            msgHandling.displaySuccessMessage("New Clothing registered and added successfully.");
            return true;
        }
    }

    //Add Personal Care
    public boolean addPersonalCare(ListInterface<DonationItem> recordItem, ListInterface<Integer> recordQty) {
        ListInterface<DonationItem> personalCareList = new ArrayList<>();
        fileHandler.loadIntoPersonalCare(personalCareList);
        donationUI.displayInstructionAddPersonalCare();

        //Choose to add amount or new item
        //Add New Item
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
                    _temp = donationUI.inputDetectedSame();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            Iterator<DonationItem> iterator = personalCareList.iterator();
                            while (iterator.hasNext()) {
                                DonationItem item = iterator.next();
                                if (item.getItemName().equals(_name) && ((PersonalCare) item).getExpiryDate().equals(_expiryDate)
                                        && ((PersonalCare) item).getNetWeight() == (_netWeight)
                                        && ((PersonalCare) item).getPersonalCareCategory().equals(_pcCategory)
                                        && ((PersonalCare) item).getAge().equals(_age) && ((PersonalCare) item).getGender().equals(_gender)
                                        && ((PersonalCare) item).getVenueCode().equals(_venueCode)) {
                                    item.setQuantity(item.getQuantity() + _qty);
                                    addItemToRecord(recordItem, recordQty, item, _qty);
                                    break;
                                }
                            }
                            msgHandling.displaySuccessMessage("Personal Care quantity added successfully.");
                            fileHandler.loadBackPersonalCareFile(personalCareList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Personal Care", personalCareList);
            PersonalCare newItem = new PersonalCare(_netWeight, _expiryDate, _gender, _age, _pcCategory, _venueCode, _code, _name, _qty);
            personalCareList.add(newItem);
            fileHandler.loadBackPersonalCareFile(personalCareList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            msgHandling.displaySuccessMessage("New Personal Care registered and added successfully.");
            return true;
        }
    }

    //Add Medical Device
    public boolean addMedicalDevice(ListInterface<DonationItem> recordItem, ListInterface<Integer> recordQty) {
        ListInterface<DonationItem> medicalDeviceList = new ArrayList<>();
        fileHandler.loadIntoMedicalDevice(medicalDeviceList);
        donationUI.displayInstructionAddMedicalDevice();

        //Choose to add amount or new item
        //Add New Item
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
                    _temp = donationUI.inputDetectedSame();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            while (true) {
                                _temp = donationUI.inputActionToDescription();
                                if (_temp.equals("1") || _temp.equals("2") || _temp.equals("3")) {
                                    Iterator<DonationItem> iterator = medicalDeviceList.iterator();
                                    while (iterator.hasNext()) {
                                        DonationItem item = iterator.next();
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
                                    msgHandling.displaySuccessMessage("Medical Device quantity added successfully.");
                                    fileHandler.loadBackMedicalDeviceFile(medicalDeviceList);
                                    return true;
                                } else if (_temp.equalsIgnoreCase("X")) {
                                    loopErrorCheck = false;
                                    return false;
                                } else {
                                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                                }
                            }

                        } else {
                            loopErrorCheck = false;
                            return false;
                        }
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Medical Device", medicalDeviceList);
            MedicalDevice newItem = new MedicalDevice(_venueCode, _description, _code, _name, _qty);
            medicalDeviceList.add(newItem);
            fileHandler.loadBackMedicalDeviceFile(medicalDeviceList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            msgHandling.displaySuccessMessage("New Medical Device registered and added successfully.");
            return true;
        }
        return false;

    }

    //Add Medicine
    public boolean addMedicine(ListInterface<DonationItem> recordItem, ListInterface<Integer> recordQty) {
        ListInterface<DonationItem> medicineList = new ArrayList<>();
        fileHandler.loadIntoMedicine(medicineList);
        donationUI.displayInstructionAddMedicine();
        //Choose to add amount or new item
        //Add New Item

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
                    _temp = donationUI.inputDetectedSame();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            while (true) {
                                _temp = donationUI.inputActionToDescription();
                                if (_temp.equals("1") || _temp.equals("2") || _temp.equals("3")) {
                                    Iterator<DonationItem> iterator = medicineList.iterator();
                                    while (iterator.hasNext()) {
                                        DonationItem item = iterator.next();
                                        if (item.getItemName().equals(_name) && ((Medicine) item).getExpiryDate().equals(_expiryDate)
                                                && ((Medicine) item).getNetWeight() == (_netWeight)
                                                && ((Medicine) item).getDosageForm().equals(_dosageForm)
                                                && ((Medicine) item).getAge().equals(_age) && ((Medicine) item).getGender().equals(_gender)
                                                && ((Medicine) item).getVenueCode().equals(_venueCode)) {
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
                                    msgHandling.displaySuccessMessage("Medicine quantity added successfully.");
                                    fileHandler.loadBackMedicineFile(medicineList);
                                    return true;
                                } else if (_temp.equalsIgnoreCase("X")) {
                                    loopErrorCheck = false;
                                    return false;
                                } else {
                                    msgHandling.displayInvalidInputMessage("Please enter 1, 2, 3 or X.");
                                }
                            }

                        } else {
                            return false;
                        }
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N." + ex);
                }
            }
        } else {
            _code = generateItemCode("Medicine", medicineList);
            Medicine newItem = new Medicine(_netWeight, _expiryDate, _gender, _age, _venueCode, _dosageForm, _description, _code, _name, _qty);
            medicineList.add(newItem);
            fileHandler.loadBackMedicineFile(medicineList);
            addItemToRecord(recordItem, recordQty, newItem, _qty);
            msgHandling.displaySuccessMessage("New Medicine registered and added successfully.");
            return true;
        }
        return false;
    }

    //Add Money
    public boolean addMoney(ListInterface<DonationItem> recordItem, ListInterface<Double> recordAmt) {
        ListInterface<DonationItem> moneyList = new ArrayList<>();
        fileHandler.loadIntoMoney(moneyList);

        donationUI.displayInstructionAddMoney();
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
                    _temp = donationUI.inputDetectedSameAmt();
                    if (_temp.equalsIgnoreCase("N") || _temp.equalsIgnoreCase("Y")) {
                        if (_temp.equalsIgnoreCase("Y")) {
                            Iterator<DonationItem> iterator = moneyList.iterator();
                            while (iterator.hasNext()) {
                                DonationItem item = iterator.next();
                                if (((Money) item).getSource().equals(_source)) {
                                    addItemToRecord(recordItem, recordAmt, item, _amount);
                                    _amount = ((Money) item).getAmount() + _amount;
                                    ((Money) item).setAmount(_amount);
                                    break;
                                }
                            }
                            msgHandling.displaySuccessMessage("Money Amount added successfully.");
                            fileHandler.loadBackMoneyFile(moneyList);
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
        } else {
            _code = generateItemCode("Money", moneyList);
            Money newItem = new Money(_amount, _source, _code, _name, 1);
            moneyList.add(newItem);
            addItemToRecord(recordItem, recordAmt, newItem, _amount);
            fileHandler.loadBackMoneyFile(moneyList);
            msgHandling.displaySuccessMessage("New Money Item registered and added successfully.");
            return true;
        }
    }

    //Get donor name
    public Donor getDonorIDforDonation() {
        String reply = "";

        while (true) {
            try {
                reply = donationUI.inputDonorID();
                if (reply.equalsIgnoreCase("X")) {
                    return null;
                    //Implement validate donor code
                } else {
                    if (donorHandling.checkIfDonorExist(reply) != null) {
                        return donorHandling.checkIfDonorExist(reply);
                    } else {
                        msgHandling.displayGeneralErrorMsg("Invalid Donor ID. Please enter a valid Donor ID.");
                    }
                }
            } catch (Exception ex) {
                msgHandling.displayGeneralErrorMsg("Invalid Donor ID. Please enter a valid Donor ID.");
            }
        }
    }

    //Remove Donation
    public void removeDonation() {
        ListInterface<DonationItem> itemlist = new ArrayList<>();
        ListInterface<DonationRecord> recordlist = new ArrayList<>();
        ListInterface<DonationRecord> recordlist2 = new ArrayList<>();
        int option;
        String reply;
        boolean continueDonationAdd = true;
        while (true) {
            donationUI.displayRemoveDonationHeader();
            donationUI.displayDonationCategory();
            option = checkMenuWithOp(8);
            if (option == 8) {
                break;
            } else {
                itemlist = new ArrayList<>();
                displayBasedOnCatOption(option, itemlist);
                fileHandler.loadIntoDR(recordlist);
                //ask to remove which
                if (!itemlist.isEmpty()) {
                    String _temp;
                    boolean removed = false;
                    while (removed == false) {
                        _temp = donationUI.inputItemCodeRemove();
                        if (_temp.equalsIgnoreCase("X")) {
                            break;
                        } else {
                            Iterator<DonationItem> iterator = itemlist.iterator();
                            while (iterator.hasNext()) {
                                DonationItem item = iterator.next();
                                if (item.getItemCode().equals(_temp)) {
                                    itemlist.remove(item);
                                    msgHandling.displaySuccessMessage("Item removed successfully!");
                                    Iterator<DonationRecord> iterator3 = recordlist.iterator();
                                    while (iterator3.hasNext()) {
                                        DonationRecord record = iterator3.next();
                                        Iterator<DonationItem> iterator2 = record.getItem().iterator();
                                        while (iterator2.hasNext()) {
                                            DonationItem recordItem = iterator2.next();
                                            if (recordItem.getItemCode().equals(_temp)) {
                                                record.getItem().remove(recordItem);
                                            }
                                        }
                                        if (!record.getItem().isEmpty()) {
                                            recordlist2.add(record);
                                        }
                                    }
                                    fileHandler.loadBackDRFile(recordlist2);
                                    msgHandling.displaySuccessMessage("Records associated removed successfully!");
                                    removed = true;
                                    if (option == 7) {
                                        fileHandler.loadBackMoneyFile(itemlist);
                                    } else if (option == 6) {
                                        fileHandler.loadBackMedicineFile(itemlist);
                                    } else if (option == 5) {
                                        fileHandler.loadBackMedicalDeviceFile(itemlist);
                                    } else if (option == 4) {
                                        fileHandler.loadBackPersonalCareFile(itemlist);
                                    } else if (option == 3) {
                                        fileHandler.loadBackClothingFile(itemlist);
                                    } else if (option == 2) {
                                        fileHandler.loadBackBeverageFile(itemlist);
                                    } else {
                                        fileHandler.loadBackFoodFile(itemlist);
                                    }
                                    break;
                                }
                            }
                            if (removed == false) {
                                msgHandling.displayGeneralErrorMsg("Invalid Item Code. Please enter a valid item code." + _temp);
                            }
                        }
                    }
                } else {
                    generalFunc.enterToContinue();
                    break;
                }
            }

            while (true) {
                try {
                    reply = donationUI.inputAskConRemove();
                    if (reply.equalsIgnoreCase("N") || reply.equalsIgnoreCase("Y")) {
                        if (reply.equalsIgnoreCase("N")) {
                            continueDonationAdd = false;
                        }
                        break;
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
            if (!continueDonationAdd) {
                break;
            }
        }
    }

    //Display Donation
    public void displayAllDonation(ListInterface<DonationItem> fullList) {
        if (!fullList.isEmpty()) {
            donationUI.displayFullDonationListTableHeader();
            Iterator<DonationItem> iterator = fullList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                if (item instanceof Money) {
                    donationUI.displayFullDonationItemMoney(item);
                } else {
                    donationUI.displayFullDonationItemNotMoney(item);
                    if (item instanceof Food) {
                        donationUI.displayFullDonationItemNotMoneyFood(item);
                    } else if (item instanceof Beverage) {
                        donationUI.displayFullDonationItemNotMoneyBg(item);
                    } else if (item instanceof PersonalCare) {
                        donationUI.displayFullDonationItemNotMoneyPC(item);
                    } else if (item instanceof Medicine) {
                        donationUI.displayFullDonationItemNotMoneyMed(item);
                    } else if (item instanceof MedicalDevice) {
                        donationUI.displayFullDonationItemNotMoneyMD(item);
                    } else {
                        donationUI.displayFullDonationItemNotMoneyClothing(item);
                    }
                }
            }
            generalFunc.repeatPrint("-", 93);
            //generalFunc.printEmptyEmptyLine();
            generalFunc.printEmptyLine();
        } else {
            donationUI.displayEmptyList();
        }
    }

    //Display Based On Option
    public void displayBasedOnCatOption(int option, ListInterface<DonationItem> itemlist) {
        if (option == 7) {
            donationUI.displayMoneyListHeader();
            fileHandler.loadIntoMoney(itemlist);
            if (!itemlist.isEmpty()) {
                donationUI.displayMoney(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 6) {
            donationUI.displayMedicineListHeader();
            fileHandler.loadIntoMedicine(itemlist);
            if (!itemlist.isEmpty()) {
                donationUI.displayMedicine(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 5) {
            donationUI.displayMedicalDeviceListHeader();
            fileHandler.loadIntoMedicalDevice(itemlist);
            if (!itemlist.isEmpty()) {
                donationUI.displayMedicalDevice(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 4) {
            donationUI.displayPersonalCareListHeader();
            fileHandler.loadIntoPersonalCare(itemlist);
            if (!itemlist.isEmpty()) {
                donationUI.displayPersonalCare(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 3) {
            donationUI.displayClothingListHeader();
            fileHandler.loadIntoClothing(itemlist);
            if (!itemlist.isEmpty()) {
                donationUI.displayClothing(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 2) {
            donationUI.displayBeverageListHeader();
            fileHandler.loadIntoBeverage(itemlist);
            if (!itemlist.isEmpty()) {
                donationUI.displayBeverage(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else {
            donationUI.displayFoodListHeader();
            fileHandler.loadIntoFood(itemlist);
            if (!itemlist.isEmpty()) {
                donationUI.displayFood(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        }
    }

    //Search Donation
    public void searchDonation() {
        donationUI.displaySearchDonationHeader();
        ListInterface<DonationItem> fullList = new ArrayList<>();
        fileHandler.loadIntoAll(fullList);
        displayAllDonation(fullList);
        ListInterface<DonationRecord> fullRecord = new ArrayList<>();
        fileHandler.loadIntoDR(fullRecord);
        DonationItem searchResultsItem = null;
        ListInterface<DonationRecord> searchResultsRecord = new ArrayList<>();
        String code;
        while (true) {
            code = donationUI.inputSearchItemCode();
            if (code.isEmpty()) {
                msgHandling.displayInvalidInputMessage("Please do not leave the field blank.");
            } else {
                if (code.equalsIgnoreCase("X")) {
                    break;
                } else {
                    boolean valid = false;
                    Iterator<DonationItem> iterator = fullList.iterator();
                    while (iterator.hasNext()) {
                        DonationItem item = iterator.next();
                        if (item.getItemCode().equals(code)) {
                            valid = true;
                        }
                    }
                    if (valid == true) {
                        break;
                    } else {
                        msgHandling.displayGeneralErrorMsg("No results found.");
                    }
                }
            }
        }
        if (!code.equalsIgnoreCase("X")) {
            Iterator<DonationItem> iterator = fullList.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
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
            displaySearchResults(lineToDisplay, searchResultsRecord);
            generalFunc.enterToContinue();
        }
    }

    public void displaySearchResults(String lineToDisplay, ListInterface<DonationRecord> searchResultsRecord) {
        donationUI.displaySearchResultsHeaderIfFound();
        donationUI.displaySingleItem(lineToDisplay);
        //display search results record
        displayDonationRecordAssociated(searchResultsRecord);
    }

    //Display Donation Record
    public void displayDonationRecordAssociated(ListInterface<DonationRecord> dRecordList) {
        donationUI.displayDonationRecordHeader();
        if (dRecordList.isEmpty()) {
            donationUI.displayEmptyList();
        } else {
            displayDonationRecordAssociatedNotEmpty(dRecordList);
        }
    }

    public void displayDonationRecordAssociatedNotEmpty(ListInterface<DonationRecord> dRecordList) {
        donationUI.displayDonationRecordTableHeader();
        Iterator<DonationRecord> iterator = dRecordList.iterator();
        while (iterator.hasNext()) {
            DonationRecord record = iterator.next();
            displaySingleRecordWithDonorID(record);
        }
    }

    public void displaySingleRecordWithDonorID(DonationRecord record) {
        int qtyIndex = 0;
        int amtIndex = 0;
        if (record.getItem().get(0) instanceof Money) {
            donationUI.displaySingleRecordWithDonorIDMoney(record, amtIndex);
            amtIndex++;
        } else {
            donationUI.displaySingleRecordWithDonorIDNotMoney(record, qtyIndex);
            qtyIndex++;
        }
        if (record.getItem().size() > 1) {
            for (int i = 1; i < record.getItem().size(); i++) {
                if (record.getItem().get(i) instanceof Money) {
                    donationUI.displaySingleRecordWithDonorIDMoney(record, i, amtIndex);
                    amtIndex++;
                } else {
                    donationUI.displaySingleRecordWithDonorIDNotMoney(record, i, qtyIndex);
                    qtyIndex++;
                }
            }
        }
        generalFunc.repeatPrint("-", 121);
        generalFunc.printEmptyLine();
    }

    public void displaySingleDonorRecords(ListInterface<DonationRecord> dRecordList, Donor donor) {
        donationUI.displaySingleDonorRecordsHeader(donor);
        Iterator<DonationRecord> iterator = dRecordList.iterator();
        while (iterator.hasNext()) {
            DonationRecord record = iterator.next();
            displaySingleRecordWithoutDonorID(record);
        }
        generalFunc.repeatPrint("-", 89);
        generalFunc.printEmptyLine();
    }

    public void displaySingleRecordWithoutDonorID(DonationRecord record) {
        int qtyIndex = 0;
        int amtIndex = 0;
        if (record.getItem().get(0) instanceof Money) {
            donationUI.displaySingleRecordWithoutDonorIDMoney(record, amtIndex);
            amtIndex++;
        } else {
            donationUI.displaySingleRecordWithoutDonorIDNotMoney(record, qtyIndex);
            qtyIndex++;
        }
        if (record.getItem().size() > 1) {
            for (int i = 1; i < record.getItem().size(); i++) {
                if (record.getItem().get(i) instanceof Money) {
                    donationUI.displaySingleRecordWithoutDonorIDMoney(record, i, amtIndex);
                    amtIndex++;
                } else {
                    donationUI.displaySingleRecordWithoutDonorIDNotMoney(record, i, qtyIndex);
                    qtyIndex++;
                }
            }
        }
    }
    //Display Amend Option

    public void displayAmendOption(int option) {
        if (option == 7) {
            donationUI.displayAmendMoneyOption();
        } else if (option == 6) {
            donationUI.displayAmendMedicineOption();
        } else if (option == 5) {
            donationUI.displayAmendMedicalDeviceOption();
        } else if (option == 4) {
            donationUI.displayAmendPersonalCareOption();
        } else if (option == 3) {
            donationUI.displayAmendClothingOption();
        } else if (option == 2) {
            donationUI.displayAmendBeverageOption();
        } else {
            donationUI.displayAmendFoodOption();
        }
    }

    //Amend Donation
    public void editDonation() {
        int option;
        String reply;
        boolean continueDonationAdd = true;
        while (true) {
            donationUI.displayAmendDetailsHeader();
            donationUI.displayDonationCategory();
            option = checkMenuWithOp(8);
            if (option == 8) {
                break;
            } else {
                ListInterface<DonationItem> itemlist = new ArrayList<>();
                displayBasedOnCatOption(option, itemlist);
                //ask to edit which
                if (!itemlist.isEmpty()) {
                    String _temp;
                    int amdOption;
                    boolean amend = false;
                    while (amend == false) {
                        _temp = donationUI.inputItemCodeEdit();
                        if (_temp.equalsIgnoreCase("X")) {
                            break;
                        } else {
                            Iterator<DonationItem> iterator = itemlist.iterator();
                            while (iterator.hasNext()) {
                                DonationItem item = iterator.next();
                                if (item.getItemCode().equals(_temp)) {
                                    //if it exists, ask to amend which info
                                    //display amend option
                                    displayAmendOption(option);
                                    amdOption = checkAmendOption(option);
                                    if ((option == 7 && amdOption == 3) || (option == 6 && amdOption == 10) || (option == 5 && amdOption == 5)
                                            || (option == 4 && amdOption == 9) || (option == 3 && amdOption == 8) || (option == 2 && amdOption == 6)
                                            || (option == 1 && amdOption == 7)) {
                                        amend = true;
                                        break;
                                    } else {
                                        if (amendDetails(_temp, option, amdOption, itemlist)) {
                                            amend = true;
                                            //amend finished, load back
                                            if (option == 7) {
                                                fileHandler.loadBackMoneyFile(itemlist);
                                            } else if (option == 6) {
                                                fileHandler.loadBackMedicineFile(itemlist);
                                            } else if (option == 5) {
                                                fileHandler.loadBackMedicalDeviceFile(itemlist);
                                            } else if (option == 4) {
                                                fileHandler.loadBackPersonalCareFile(itemlist);
                                            } else if (option == 3) {
                                                fileHandler.loadBackClothingFile(itemlist);
                                            } else if (option == 2) {
                                                fileHandler.loadBackBeverageFile(itemlist);
                                            } else {
                                                fileHandler.loadBackFoodFile(itemlist);
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
                                msgHandling.displayGeneralErrorMsg("Invalid Item Code. Please enter a valid item code." + _temp);
                            }
                        }
                    }
                } else {
                    generalFunc.enterToContinue();
                    break;
                }
            }
            while (true) {
                try {
                    reply = donationUI.inputAskConAmend();
                    if (reply.equalsIgnoreCase("N") || reply.equalsIgnoreCase("Y")) {
                        if (reply.equalsIgnoreCase("N")) {
                            continueDonationAdd = false;
                        }
                        break;
                    } else {
                        msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                    }
                } catch (Exception ex) {
                    msgHandling.displayInvalidInputMessage("Please enter Y or N.");
                }
            }
            if (!continueDonationAdd) {
                break;
            }
        }
    }

    //search for existing record
    public void searchRecord(String code, ListInterface<DonationRecord> searchResultsRecord) {
        ListInterface<DonationRecord> fullRecord = new ArrayList<>();
        fileHandler.loadIntoDR(fullRecord);
        Iterator<DonationRecord> iterator = fullRecord.iterator();
        while (iterator.hasNext()) {
            DonationRecord record = iterator.next();
            Iterator<DonationItem> iterator2 = record.getItem().iterator();
            while (iterator2.hasNext()) {
                DonationItem item2 = iterator2.next();
                if (item2.getItemCode().equals(code)) {
                    searchResultsRecord.add(record);
                    break;
                }
            }
        }
    }

    //Amend Item
    public boolean amendDetails(String itemCode, int option, int amdOption, ListInterface<DonationItem> itemlist) {
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
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, _temp, ((Food) item).getExpiryDate(), ((Food) item).getNetWeight(),
                                        ((Food) item).getFoodType(), ((Food) item).getVenueCode());
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, _temp, ((Beverage) item).getExpiryDate(), ((Beverage) item).getNetVolume(),
                                        ((Beverage) item).getVenueCode());
                                break;
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, _temp, ((Clothing) item).getSize(), ((Clothing) item).getClothingCategory(),
                                        ((Clothing) item).getAge(), ((Clothing) item).getGender(), ((Clothing) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, _temp, ((PersonalCare) item).getExpiryDate(),
                                        ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(),
                                        ((PersonalCare) item).getAge(), ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            case 5:
                                exist = checkExistBeforeMedicalDevice(itemlist, _temp, ((MedicalDevice) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, _temp, ((Medicine) item).getExpiryDate(), ((Medicine) item).getNetWeight(),
                                        ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), ((Medicine) item).getGender(),
                                        ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                            item.setItemName(_temp);
                            return true;
                        }
                    }
                }
            }
            //Quantity
        } else if (amdOption == 2 && option >= 1 && option <= 6) {
            ListInterface<DonationRecord> searchResultsRecord = new ArrayList<>();
            ListInterface<DonationRecord> recordlist = new ArrayList<>();
            ListInterface<DonationRecord> recordlist2 = new ArrayList<>();
            fileHandler.loadIntoDR(recordlist);
            searchRecord(itemCode, searchResultsRecord);
            displayDonationRecordAssociated(searchResultsRecord);
            donationUI.displayAmendQtyRecord();
            while (true) {
                _temp = donationUI.inputChoice();
                if (_temp.equalsIgnoreCase("X")) {
                    return false;
                } else {
                    if (!_temp.equals("1") && !_temp.equals("2")) {
                        msgHandling.displayInvalidOptionMessage("Please enter a valid option (1-2, X).");
                    } else {
                        break;
                    }
                }
            }
            if (_temp.equals("1")) {
                boolean removed = false;
                _temp2 = 0;
                while (removed == false) {
                    _temp = donationUI.inputRecordIDRemove();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //Check is it valid record ID
                        Iterator<DonationRecord> iterator = searchResultsRecord.iterator();
                        while (iterator.hasNext()) {
                            DonationRecord record = iterator.next();
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                removed = true;
                                break;
                            }
                        }
                        if (removed == true) {//remove record
                            Iterator<DonationRecord> iterator3 = recordlist.iterator();
                            DonationItem itemToBeRemoved = null;
                            while (iterator3.hasNext()) {
                                DonationRecord record = iterator3.next();
                                if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                    int qtyIndex = 0;
                                    Iterator<DonationItem> iterator2 = record.getItem().iterator();
                                    while (iterator2.hasNext()) {
                                        DonationItem item = iterator2.next();
                                        if (item.getItemCode().equals(itemCode)) {
                                            _temp2 = record.getQty().get(qtyIndex);
                                            itemToBeRemoved = item;
                                            break;
                                        }
                                        if (!(item instanceof Money)) {
                                            qtyIndex++;
                                        }
                                    }
                                    record.getItem().remove(itemToBeRemoved);
                                    record.getQty().remove(qtyIndex);
                                    if (!record.getItem().isEmpty()) {
                                        DonationRecord afterAmendRecord = record;
                                        recordlist2.add(afterAmendRecord);
                                    }
                                    removed = true;
                                } else {
                                    recordlist2.add(record);
                                }
                            }
                            fileHandler.loadBackDRFile(recordlist2);
                            //reduce quantity, if 0 then remove item
                            Iterator<DonationItem> iterator2 = itemlist.iterator();
                            while (iterator2.hasNext()) {
                                DonationItem item = iterator2.next();
                                if (item.getItemCode().equals(itemCode)) {
                                    item.setQuantity(item.getQuantity() - _temp2);
                                    if (item.getQuantity() == 0) {
                                        itemlist.remove(item);
                                    }
                                    if (option == 7) {
                                        fileHandler.loadBackMoneyFile(itemlist);
                                    } else if (option == 6) {
                                        fileHandler.loadBackMedicineFile(itemlist);
                                    } else if (option == 5) {
                                        fileHandler.loadBackMedicalDeviceFile(itemlist);
                                    } else if (option == 4) {
                                        fileHandler.loadBackPersonalCareFile(itemlist);
                                    } else if (option == 3) {
                                        fileHandler.loadBackClothingFile(itemlist);
                                    } else if (option == 2) {
                                        fileHandler.loadBackBeverageFile(itemlist);
                                    } else {
                                        fileHandler.loadBackFoodFile(itemlist);
                                    }
                                    break;
                                }
                            }
                        }

                        if (removed == false) {
                            msgHandling.displayGeneralErrorMsg("Invalid Record ID. Please enter a valid record ID.");
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                        }
                    }
                }
            } else {
                while (true) {
                    _temp = donationUI.inputRecordIDAmend();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //remove record
                        int qtyIndex = 0;
                        boolean validRecordID = false;
                        Iterator<DonationRecord> iterator = searchResultsRecord.iterator();
                        while (iterator.hasNext()) {
                            DonationRecord record = iterator.next();
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                validRecordID = true;
                                break;
                            }
                        }
                        if (validRecordID == true) {
                            Iterator<DonationRecord> iterator4 = recordlist.iterator();
                            while (iterator4.hasNext()) {
                                DonationRecord record = iterator4.next();
                                if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                    Iterator<DonationItem> iterator2 = record.getItem().iterator();
                                    while (iterator2.hasNext()) {
                                        DonationItem item2 = iterator2.next();
                                        if (item2.getItemCode().equals(itemCode)) {
                                            _temp2 = getNvalidateQuantity();
                                            if (_temp2 == 0) {
                                                return false;
                                            } else {
                                                int changes = _temp2 - record.getQty().get(qtyIndex);
                                                record.getQty().set(qtyIndex, record.getQty().get(qtyIndex) + changes);
                                                fileHandler.loadBackDRFile(recordlist);
                                                Iterator<DonationItem> iterator3 = itemlist.iterator();
                                                while (iterator3.hasNext()) {
                                                    DonationItem item = iterator3.next();
                                                    if (item.getItemCode().equals(itemCode)) {
                                                        item.setQuantity(item.getQuantity() + changes);
                                                        if (item.getQuantity() == 0) {
                                                            itemlist.remove(item);
                                                        }
                                                        if (option == 6) {
                                                            fileHandler.loadBackMedicineFile(itemlist);
                                                        } else if (option == 5) {
                                                            fileHandler.loadBackMedicalDeviceFile(itemlist);
                                                        } else if (option == 4) {
                                                            fileHandler.loadBackPersonalCareFile(itemlist);
                                                        } else if (option == 3) {
                                                            fileHandler.loadBackClothingFile(itemlist);
                                                        } else if (option == 2) {
                                                            fileHandler.loadBackBeverageFile(itemlist);
                                                        } else {
                                                            fileHandler.loadBackFoodFile(itemlist);
                                                        }
                                                        msgHandling.displaySuccessMessage("Modification Successful!");
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
                        }
                        msgHandling.displayGeneralErrorMsg("Invalid Record ID. Please enter a valid record ID.");
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
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, item.getItemName(), ((Food) item).getExpiryDate(), _temp3,
                                        ((Food) item).getFoodType(), ((Food) item).getVenueCode());
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, item.getItemName(), ((Beverage) item).getExpiryDate(),
                                        _temp3, ((Beverage) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(),
                                        _temp3, ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(),
                                        ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(), _temp3,
                                        ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), ((Medicine) item).getGender(),
                                        ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
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
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        msgHandling.displaySuccessMessage("Modification Successful!");
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
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, item.getItemName(), _temp4, ((Food) item).getNetWeight(),
                                        ((Food) item).getFoodType(), ((Food) item).getVenueCode());
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, item.getItemName(), _temp4, ((Beverage) item).getNetVolume(),
                                        ((Beverage) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), _temp4, ((PersonalCare) item).getNetWeight(),
                                        ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(),
                                        ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), _temp4, ((Medicine) item).getNetWeight(),
                                        ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(), ((Medicine) item).getGender(),
                                        ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
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
            donationUI.displayAge();
            _temp = getNvalidateAge();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, item.getItemName(), ((Clothing) item).getSize(),
                                        ((Clothing) item).getClothingCategory(), _temp, ((Clothing) item).getGender(), ((Clothing) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(),
                                        ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(), _temp,
                                        ((PersonalCare) item).getGender(), ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(),
                                        ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), _temp, ((Medicine) item).getGender(),
                                        ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
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
            donationUI.displayGender();
            _temp = getNvalidateGender();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, item.getItemName(), ((Clothing) item).getSize(),
                                        ((Clothing) item).getClothingCategory(), ((Clothing) item).getAge(), _temp, ((Clothing) item).getVenueCode());
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(),
                                        ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(),
                                        ((PersonalCare) item).getAge(), _temp, ((PersonalCare) item).getVenueCode());
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(),
                                        ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(),
                                        _temp, ((Medicine) item).getVenueCode());
                                break;
                        }
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
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
        } else if ((amdOption == 7 && (option == 3 || option == 6)) || (amdOption == 6 && option == 1) || (amdOption == 5 && option == 2)
                || (amdOption == 8 && option == 4) || (amdOption == 3 && option == 5)) {
            donationUI.displayVenueCode();
            _temp = getNvalidateVenueCode();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        switch (option) {
                            case 1:
                                exist = checkExistBeforeFood(itemlist, item.getItemName(), ((Food) item).getExpiryDate(), ((Food) item).getNetWeight(),
                                        ((Food) item).getFoodType(), _temp);
                                break;
                            case 2:
                                exist = checkExistBeforeBeverage(itemlist, item.getItemName(), ((Beverage) item).getExpiryDate(), ((Beverage) item).getNetVolume(),
                                        _temp);
                                break;
                            case 3:
                                exist = checkExistBeforeClothing(itemlist, item.getItemName(), ((Clothing) item).getSize(), ((Clothing) item).getClothingCategory(),
                                        ((Clothing) item).getAge(), ((Clothing) item).getGender(), _temp);
                                break;
                            case 4:
                                exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(),
                                        ((PersonalCare) item).getNetWeight(), ((PersonalCare) item).getPersonalCareCategory(), ((PersonalCare) item).getAge(),
                                        ((PersonalCare) item).getGender(), _temp);
                                break;
                            case 5:
                                exist = checkExistBeforeMedicalDevice(itemlist, item.getItemName(), _temp);
                                break;
                            default:
                                exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(),
                                        ((Medicine) item).getNetWeight(), ((Medicine) item).getDosageForm(), ((Medicine) item).getAge(),
                                        ((Medicine) item).getGender(), _temp);
                                break;
                        }
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
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
            donationUI.displayFoodType();
            _temp = getNvalidateFoodType();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeFood(itemlist, item.getItemName(), ((Food) item).getExpiryDate(),
                                ((Food) item).getNetWeight(), _temp, ((Food) item).getVenueCode());
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                            ((Food) item).setFoodType(_temp);
                            return true;
                        }
                    }
                }
            }
            //Clothing category
        } else if (amdOption == 3 && option == 3) {
            donationUI.displayClothingCategory();
            _temp = getNvalidateClothingCategory();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeClothing(itemlist, item.getItemName(),
                                ((Clothing) item).getSize(), _temp, ((Clothing) item).getAge(), ((Clothing) item).getGender(),
                                ((Clothing) item).getVenueCode());
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                            ((Clothing) item).setClothingCategory(_temp);
                            return true;
                        }
                    }
                }
            }
            //Personal Care Category
        } else if (amdOption == 7 && option == 4) {
            donationUI.displayPCCat();
            _temp = getNvalidatePCCat();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforePersonalCare(itemlist, item.getItemName(), ((PersonalCare) item).getExpiryDate(),
                                ((PersonalCare) item).getNetWeight(), _temp, ((PersonalCare) item).getAge(), ((PersonalCare) item).getGender(),
                                ((PersonalCare) item).getVenueCode());
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                            ((PersonalCare) item).setPersonalCareCategory(_temp);
                            return true;
                        }
                    }
                }
            }
            //Dosage Form
        } else if (amdOption == 8 && option == 6) {
            donationUI.displayDSForm();
            _temp = getNvalidateDosageForm();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeMedicine(itemlist, item.getItemName(), ((Medicine) item).getExpiryDate(), ((Medicine) item).getNetWeight(),
                                _temp, ((Medicine) item).getAge(), ((Medicine) item).getGender(), ((Medicine) item).getVenueCode());
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                            ((Medicine) item).setDosageForm(_temp);
                            return true;
                        }
                    }
                }
            }
            //Size
        } else if (amdOption == 6 && option == 3) {
            donationUI.displaySize();
            String _clothCat = "", _age = "", _gender = "";
            Iterator<DonationItem> iterator = itemlist.iterator();
            while (iterator.hasNext()) {
                DonationItem item2 = iterator.next();
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
                Iterator<DonationItem> iterator2 = itemlist.iterator();
                while (iterator2.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeClothing(itemlist, item.getItemName(), _temp, ((Clothing) item).getClothingCategory(),
                                ((Clothing) item).getAge(), ((Clothing) item).getGender(), ((Clothing) item).getVenueCode());
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                            ((Clothing) item).setSize(_temp);
                            return true;
                        }
                    }
                }
            }
            //amount
        } else if (amdOption == 1 && option == 7) {
            ListInterface<DonationRecord> searchResultsRecord = new ArrayList<>();
            ListInterface<DonationRecord> recordlist = new ArrayList<>();
            ListInterface<DonationRecord> recordlist2 = new ArrayList<>();
            fileHandler.loadIntoDR(recordlist);
            searchRecord(itemCode, searchResultsRecord);
            displayDonationRecordAssociated(searchResultsRecord);
            donationUI.displayAmendOptionAmt();
            while (true) {
                _temp = donationUI.inputChoice();
                if (_temp.equalsIgnoreCase("X")) {
                    return false;
                } else {
                    if (!_temp.equals("1") && !_temp.equals("2")) {
                        msgHandling.displayInvalidOptionMessage("Please enter a valid option (1-2, X).");
                    } else {
                        break;
                    }
                }
            }
            if (_temp.equals("1")) {
                boolean removed = false;
                _temp3 = 0;
                while (removed == false) {
                    _temp = donationUI.inputRecordIDRemove();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //Check is it valid record ID
                        Iterator<DonationRecord> iterator = searchResultsRecord.iterator();
                        while (iterator.hasNext()) {
                            DonationRecord record = iterator.next();
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                removed = true;
                                break;
                            }
                        }
                        if (removed == true) {//remove record
                            Iterator<DonationRecord> iterator3 = recordlist.iterator();
                            DonationItem itemToBeRemoved = null;
                            //TODO
                            while (iterator3.hasNext()) {
                                DonationRecord record = iterator3.next();
                                if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                    int amtIndex = 0;
                                    Iterator<DonationItem> iterator2 = record.getItem().iterator();
                                    while (iterator2.hasNext()) {
                                        DonationItem item = iterator2.next();
                                        if (item.getItemCode().equals(itemCode)) {
                                            _temp3 = record.getAmount().get(amtIndex);
                                            itemToBeRemoved = item;
                                            break;
                                        }
                                        if (!(item instanceof Money)) {
                                            amtIndex++;
                                        }
                                    }
                                    record.getItem().remove(itemToBeRemoved);
                                    record.getAmount().remove(amtIndex);
                                    if (!record.getItem().isEmpty()) {
                                        DonationRecord afterAmendRecord = record;
                                        recordlist2.add(afterAmendRecord);
                                    }
                                    removed = true;
                                } else {
                                    recordlist2.add(record);
                                }
                            }
                            fileHandler.loadBackDRFile(recordlist2);
                            //reduce amount, if 0 then remove item

                            Iterator<DonationItem> iterator4 = itemlist.iterator();
                            while (iterator4.hasNext()) {
                                DonationItem item = iterator4.next();
                                if (item.getItemCode().equals(itemCode)) {
                                    ((Money) item).setAmount(((Money) item).getAmount() - _temp3);
                                    if (((Money) item).getAmount() == 0) {
                                        itemlist.remove(item);
                                    }
                                    fileHandler.loadBackMoneyFile(itemlist);
                                    break;
                                }
                            }
                        }
                        if (removed == false) {
                            msgHandling.displayGeneralErrorMsg("Invalid Record ID. Please enter a valid record ID.");
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                        }
                    }
                }
            } else {
                while (true) {
                    _temp = donationUI.inputRecordIDAmend();
                    if (_temp.equalsIgnoreCase("X")) {
                        return false;
                    } else {
                        //remove record
                        int amtIndex = 0;
                        boolean validRecordID = false;
                        Iterator<DonationRecord> iterator = searchResultsRecord.iterator();
                        while (iterator.hasNext()) {
                            DonationRecord record = iterator.next();
                            if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                validRecordID = true;
                                break;
                            }
                        }
                        if (validRecordID == true) {
                            Iterator<DonationRecord> iterator4 = recordlist.iterator();
                            while (iterator4.hasNext()) {
                                DonationRecord record = iterator4.next();
                                if (String.valueOf(record.getRecordID()).equals(_temp)) {
                                    Iterator<DonationItem> iterator2 = record.getItem().iterator();
                                    while (iterator2.hasNext()) {
                                        DonationItem item2 = iterator2.next();
                                        if (item2.getItemCode().equals(itemCode)) {
                                            _temp3 = getNvalidateAmt();
                                            if (_temp3 == 0) {
                                                return false;
                                            } else {
                                                double changes = _temp3 - record.getAmount().get(amtIndex);
                                                record.getAmount().set(amtIndex, record.getAmount().get(amtIndex) + changes);
                                                fileHandler.loadBackDRFile(recordlist);
                                                Iterator<DonationItem> iterator5 = itemlist.iterator();
                                                while (iterator5.hasNext()) {
                                                    DonationItem item = iterator5.next();
                                                    if (item.getItemCode().equals(itemCode)) {
                                                        ((Money) item).setAmount(((Money) item).getAmount() + changes);
                                                        if (((Money) item).getAmount() == 0) {
                                                            itemlist.remove(item);
                                                        }
                                                        fileHandler.loadBackMoneyFile(itemlist);

                                                        break;
                                                    }
                                                }
                                                msgHandling.displaySuccessMessage("Modification Successful!");
                                                return true;
                                            }
                                        }
                                        if (item2 instanceof Money) {
                                            amtIndex++;
                                        }
                                    }
                                }
                            }
                            msgHandling.displayGeneralErrorMsg("Invalid Record ID. Please enter a valid record ID.");
                        }
                    }
                }
            }

            //source
        } else {
            donationUI.displayVenueCode();
            donationUI.displaySource();
            _temp = getNvalidateSource();
            if (_temp.equals("")) {
                return false;
            } else {
                boolean exist;
                Iterator<DonationItem> iterator = itemlist.iterator();
                while (iterator.hasNext()) {
                    DonationItem item = iterator.next();
                    if (item.getItemCode().equals(itemCode)) {
                        exist = checkExistBeforeMoney(itemlist, _temp);
                        if (exist) {
                            msgHandling.displayGeneralErrorMsg("Modification Failed! The same record exists.");
                            return false;
                        } else {
                            msgHandling.displaySuccessMessage("Modification Successful!");
                            ((Money) item).setSource(_temp);
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }
    //Check Amend Option

    public int checkAmendOption(int option) {
        int amdOption;
        if (option == 7) {
            amdOption = checkMenuWithOp(3);
        } else if (option == 6) {
            amdOption = checkMenuWithOp(10);
        } else if (option == 5) {
            amdOption = checkMenuWithOp(5);
        } else if (option == 4) {
            amdOption = checkMenuWithOp(9);
        } else if (option == 3) {
            amdOption = checkMenuWithOp(8);
        } else if (option == 2) {
            amdOption = checkMenuWithOp(6);
        } else {
            amdOption = checkMenuWithOp(7);
        }
        return amdOption;
    }

    public int checkMenuWithOp(int n) {
        int option;
        while (true) {
            try {
                option = Integer.parseInt(donationUI.inputOption(n));
                if (option >= 1 && option <= n) {
                    break;
                } else {
                    msgHandling.displayInvalidOptionMessage("Please enter a valid option (1-" + n + ").");
                }
            } catch (Exception ex) {
                msgHandling.displayInvalidOptionMessage("Please enter a valid option (1-" + n + ").");
            }
        }
        return option;
    }

    //Display Track Donation
    public void displayTrackDonation() {
        donationUI.displayTrackDonationHeader();
        ListInterface<DonationItem> itemlist = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            displayBasedOnCatOption(i, itemlist);
            itemlist = new ArrayList<>();
        }
    }

    //Track Donation
    public void trackDonation() {
        displayTrackDonation();
        generalFunc.enterToContinue();
    }

    //Donation-Donor List with Filter & Sorting
    public void donationDonor() {
        ListInterface<DonationRecord> fullRecord = new ArrayList<>();
        ListInterface<DonationRecord> fullRecord2 = new ArrayList<>();
        ListInterface<DonationRecord> checkedList = new ArrayList<>();
        ListInterface<String> usedIDList = new ArrayList<>();
        fileHandler.loadIntoDR(fullRecord);
        fileHandler.loadIntoDR(fullRecord2);
        donationUI.displayDonationDonorHeader();
        if (fullRecord.isEmpty()) {
            donationUI.displayEmptyList();
        } else {
            Iterator<DonationRecord> iterator = fullRecord.iterator();
            while (iterator.hasNext()) {
                DonationRecord record = iterator.next();
                //check if existed
                boolean notRepeated = true;
                Iterator<String> iterator3 = usedIDList.iterator();
                while (iterator3.hasNext()) {
                    String currentID = iterator3.next();
                    if (currentID.equals(record.getDonor().getId())) {
                        notRepeated = false;
                    }
                }
                if (notRepeated) {
                    checkedList.clear();
                    Iterator<DonationRecord> iterator2 = fullRecord2.iterator();
                    while (iterator2.hasNext()) {
                        DonationRecord record2 = iterator2.next();
                        if (record.getDonor().getId().equals(record2.getDonor().getId())) {
                            checkedList.add(record2);
                            usedIDList.add(record.getDonor().getId());
                        }
                    }
                    displaySingleDonorRecords(checkedList, record.getDonor());
                }

            }
            generalFunc.enterToContinue();
        }
    }

    public ListInterface<DonationRecord> findRecordsForADonor(String donorID) {
        ListInterface<DonationRecord> fullRecord = new ArrayList<>();
        ListInterface<DonationRecord> checkedList = new ArrayList<>();
        fileHandler.loadIntoDR(fullRecord);
        Iterator<DonationRecord> iterator2 = fullRecord.iterator();
        while (iterator2.hasNext()) {
            DonationRecord record2 = iterator2.next();
            if (donorID.equals(record2.getDonor().getId())) {
                checkedList.add(record2);
            }
        }
        return checkedList;
    }
    //Full Donation List with Filter & Sorting

    public void fullDonation() {
        ListInterface<DonationItem> itemlist = new ArrayList<>();
        ListInterface<DonationItem> fullList = new ArrayList<>();
        fileHandler.loadIntoAll(fullList);
        donationUI.displayFullDonationListHeader();
        displayAllDonation(fullList);
        int filter = 0, sort = 0, option = 0;
        if (fullList.isEmpty()) {
            generalFunc.enterToContinue();
        } else {
            while (true) {
                if (filter == 0 && sort == 0) {
                    donationUI.displayFullDonationActionMenu();
                    option = checkMenuWithOp(7);
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
                    donationUI.displayFullDonationActionMenuAftFilter();
                    option = checkMenuWithOp(6);
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
                        donationUI.displayFullDonationActionMenuAftSort();
                        option = checkMenuWithOp(4);
                        if (option == 1) {
                            filter = 1;
                        } else if (option == 2) {
                            filter = 2;
                        } else if (option == 3) {
                            sort = 0;
                        }
                    } else {
                        donationUI.displayFullDonationActionMenuAftFilterAftSort();
                        option = checkMenuWithOp(4);
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
                    itemlist = new ArrayList<>();
                    int fcOption;
                    donationUI.displayDonationCategory();
                    fcOption = checkMenuWithOp(8);
                    if (fcOption != 8) {
                        displayBasedOnCatOptionSort(fcOption, itemlist, sort);
                    } else {
                        break;
                    }
                } else if (filter == 0) {
                    fullList = new ArrayList<>();
                    fileHandler.loadIntoAll(fullList);
                    sortBeforeDisplay(fullList, sort);
                    donationUI.displayFullDonationListHeader();
                    displayAllDonation(fullList);
                } else {
                    //let user filter by quantity
                    int fqOption;
                    donationUI.displayFilterQtyOp();
                    fqOption = checkMenuWithOp(4);
                    if (fqOption != 4) {
                        filterByQuantity(fullList, sort, fqOption);
                        donationUI.displayFullDonationListHeader();
                        displayAllDonation(fullList);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void sortBeforeDisplay(ListInterface<DonationItem> itemlist, int sort) {
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
            ListInterface<DonationItem> itemlist2 = new ArrayList<>();
            Iterator<DonationItem> iterator = itemlist.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                itemlist2.add(item);
            }
            Iterator<DonationItem> iterator2 = itemlist2.iterator();
            while (iterator2.hasNext()) {
                DonationItem item = iterator2.next();
                if (item instanceof Money) {
                    int index = 0;
                    Iterator<DonationItem> iterator3 = itemlist2.iterator();
                    while (iterator3.hasNext()) {
                        DonationItem item2 = iterator3.next();
                        if (!(item2 instanceof Money)) {
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

            ListInterface<DonationItem> itemlist2 = new ArrayList<>();
            Iterator<DonationItem> iterator = itemlist.iterator();
            while (iterator.hasNext()) {
                DonationItem item = iterator.next();
                itemlist2.add(item);
            }
            Iterator<DonationItem> iterator2 = itemlist2.iterator();
            while (iterator2.hasNext()) {
                DonationItem item = iterator2.next();
                if (item instanceof Money) {
                    int index = 0;
                    Iterator<DonationItem> iterator3 = itemlist2.iterator();
                    while (iterator3.hasNext()) {
                        DonationItem item2 = iterator3.next();
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

    public void displayBasedOnCatOptionSort(int option, ListInterface<DonationItem> itemlist, int sort) {
        if (option == 7) {
            donationUI.displayMoneyListHeader();
            fileHandler.loadIntoMoney(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                donationUI.displayMoney(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 6) {
            donationUI.displayMedicineListHeader();
            fileHandler.loadIntoMedicine(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                donationUI.displayMedicine(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 5) {
            donationUI.displayMedicalDeviceListHeader();
            fileHandler.loadIntoMedicalDevice(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                donationUI.displayMedicalDevice(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 4) {
            donationUI.displayPersonalCareListHeader();
            fileHandler.loadIntoPersonalCare(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                donationUI.displayPersonalCare(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 3) {
            donationUI.displayClothingListHeader();
            fileHandler.loadIntoClothing(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                donationUI.displayClothing(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else if (option == 2) {
            donationUI.displayBeverageListHeader();
            fileHandler.loadIntoBeverage(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                donationUI.displayBeverage(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        } else {
            donationUI.displayFoodListHeader();
            fileHandler.loadIntoFood(itemlist);
            sortBeforeDisplay(itemlist, sort);
            if (!itemlist.isEmpty()) {
                donationUI.displayFood(itemlist);
            } else {
                donationUI.displayEmptyList();
            }
        }
    }

    public boolean checkNumA(String num) {
        try {
            if (Integer.parseInt(num) > 0) {
                return true;
            } else {
                msgHandling.displayGeneralErrorMsg("Invalid Number Entered! Number must be more than 0. ");
                return false;
            }
        } catch (Exception ex) {
            msgHandling.displayGeneralErrorMsg("Invalid Number Entered! Number must be integer. ");
            return false;
        }
    }

    public boolean checkNumB(String num, int a) {
        try {
            if (Integer.parseInt(num) > 0 && Integer.parseInt(num) <= a) {
                return true;
            } else {
                msgHandling.displayGeneralErrorMsg("Invalid Number Entered! Number must be more than 0. ");
                return false;
            }
        } catch (Exception ex) {
            msgHandling.displayGeneralErrorMsg("Invalid Number Entered! Number must be integer. ");
            return false;
        }
    }

    public void filterByQuantity(ListInterface<DonationItem> itemlist, int sort, int fq) {
        ListInterface<DonationItem> itemlist2 = new ArrayList<>();
        Iterator<DonationItem> iterator = itemlist.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
            itemlist2.add(item);
        }
        int a = 0, b = 0;
        String _temp;
        while (true) {
            _temp = donationUI.inputNumA();
            if (checkNumA(_temp)) {
                a = Integer.parseInt(_temp);
                break;
            }
        }
        if (fq == 2) {
            while (true) {
                _temp = donationUI.inputNumB();
                if (checkNumB(_temp, a)) {
                    b = Integer.parseInt(_temp);
                    break;
                }
            }
        }
        Iterator<DonationItem> iterator2 = itemlist2.iterator();
        while (iterator2.hasNext()) {
            DonationItem item = iterator2.next();
            if (fq == 1) {
                if (item instanceof Money) {
                    if (((Money) item).getAmount() <= a) {
                        itemlist.remove(item);
                    }
                } else {
                    if (item.getQuantity() <= a) {
                        itemlist.remove(item);
                    }
                }
            } else if (fq == 2) {
                if (item instanceof Money) {
                    if (((Money) item).getAmount() < a || ((Money) item).getAmount() > b) {
                        itemlist.remove(item);
                    }
                } else {
                    if (item.getQuantity() < a || item.getQuantity() > b) {
                        itemlist.remove(item);
                    }
                }
            } else {
                if (item instanceof Money) {
                    if (((Money) item).getAmount() >= a) {
                        itemlist.remove(item);
                    }
                } else {
                    if (item.getQuantity() >= a) {
                        itemlist.remove(item);
                    }
                }
            }
        }
        sortBeforeDisplay(itemlist, sort);

    }

    //Summary Report
    public void donationReport() {
        donationUI.displaySummaryReportChoices();
        int option = checkMenuWithOp(3);
        if (option == 1) {
            displayReport1();
            generalFunc.enterToContinue();
        }
        if (option == 2) {
            displayReport2();
            generalFunc.enterToContinue();
        }
    }

    //Report 1 - Report of Expired Items by Category as of today
    public void displayReport1() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //display expired item by category
        //display Topic
        donationUI.displayReport1Header();
        //food
        ListInterface<DonationItem> foodList = new ArrayList<>();
        fileHandler.loadIntoFood(foodList);
        //beverage
        ListInterface<DonationItem> beverageList = new ArrayList<>();
        fileHandler.loadIntoBeverage(beverageList);
        //personal care
        ListInterface<DonationItem> pcList = new ArrayList<>();
        fileHandler.loadIntoPersonalCare(pcList);
        //medicine
        ListInterface<DonationItem> medList = new ArrayList<>();
        fileHandler.loadIntoMedicine(medList);
        ListInterface<DonationItem> expiredItemList1 = new ArrayList<>();
        ListInterface<DonationItem> expiredItemList2 = new ArrayList<>();
        ListInterface<DonationItem> expiredItemList3 = new ArrayList<>();
        ListInterface<DonationItem> expiredItemList4 = new ArrayList<>();

        Iterator<DonationItem> iterator = foodList.iterator();
        while (iterator.hasNext()) {
            DonationItem food = iterator.next();
            if ((((Food) food).getExpiryDate().isBefore(LocalDate.now()))) {
                expiredItemList1.add(food);
            }
        }
        Iterator<DonationItem> iterator2 = beverageList.iterator();
        while (iterator2.hasNext()) {
            DonationItem bg = iterator2.next();
            if ((((Beverage) bg).getExpiryDate().isBefore(LocalDate.now()))) {
                expiredItemList2.add(bg);
            }
        }
        Iterator<DonationItem> iterator3 = pcList.iterator();
        while (iterator3.hasNext()) {
            DonationItem pc = iterator3.next();
            if ((((PersonalCare) pc).getExpiryDate().isBefore(LocalDate.now()))
                    && !(((PersonalCare) pc).getExpiryDate().equals(LocalDate.parse("2000-12-31", dateFormat)))) {
                expiredItemList3.add(pc);
            }
        }
        Iterator<DonationItem> iterator4 = medList.iterator();
        while (iterator4.hasNext()) {
            DonationItem med = iterator4.next();
            if ((((Medicine) med).getExpiryDate().isBefore(LocalDate.now()))) {
                expiredItemList4.add(med);
            }
        }
        if (!expiredItemList1.isEmpty()) {
            donationUI.displayReport1Food(expiredItemList1);
        }
        if (!expiredItemList2.isEmpty()) {
            donationUI.displayReport1Bg(expiredItemList2);
        }
        if (!expiredItemList3.isEmpty()) {
            donationUI.displayReport1Pc(expiredItemList3);
        }
        if (!expiredItemList4.isEmpty()) {
            donationUI.displayReport1Med(expiredItemList4);
        }
        if (expiredItemList1.isEmpty() && expiredItemList2.isEmpty() && expiredItemList3.isEmpty() && expiredItemList4.isEmpty()) {
            donationUI.displayReport1Ntg();
        }

    }

    //Report 2 - Top 3 Most Donated Non-Money Item Category Report in 2024
    public void displayReport2() {
        ListInterface<DonationItem> fullList = new ArrayList<>();
        fileHandler.loadIntoAll(fullList);
        int total[] = new int[6];
        int max[] = {-1, -1, -1};
        int maxIndex[] = {-1, -1, -1};
        total[0] = 0;
        total[1] = 0;
        total[2] = 0;
        total[3] = 0;
        total[4] = 0;
        total[5] = 0;
        Iterator<DonationItem> iterator = fullList.iterator();
        while (iterator.hasNext()) {
            DonationItem item = iterator.next();
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
            } else if (total[i] > max[1] || total[i] == max[0]) {
                max[2] = max[1];
                max[1] = total[i];
                maxIndex[2] = maxIndex[1];
                maxIndex[1] = i;
            } else if (total[i] > max[2] || total[i] == max[1]) {
                max[2] = total[i];
                maxIndex[2] = i;
            }
        }
        donationUI.displayReport2Header();
        donationUI.displayReport2Content(max, maxIndex);
        displayTotalContributers();
    }

    public void displayTotalContributers() {
        ListInterface<DonationRecord> fullRecord = new ArrayList<>();
        ListInterface<String> usedIDList = new ArrayList<>();
        fileHandler.loadIntoDR(fullRecord);
        donationUI.displayDonationDonorHeader();
        Iterator<DonationRecord> iterator = fullRecord.iterator();
        while (iterator.hasNext()) {
            DonationRecord record = iterator.next();
            //check if existed
            boolean notRepeated = true;
            Iterator<String> iterator3 = usedIDList.iterator();
            while (iterator3.hasNext()) {
                String currentID = iterator3.next();
                if (currentID.equals(record.getDonor().getId())) {
                    notRepeated = false;
                }
            }
            if (notRepeated) {
                usedIDList.add(record.getDonor().getId());
            }
        }
        donationUI.displayNumOfDonor(usedIDList.size());
    }

}
