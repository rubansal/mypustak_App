package com.mypushtak.app.Singleton;

public class EditAddressData {
    private static String reciever;
    private static String address;
    private static String landmark;
    private static String state;
    private static String city;
    private static String pincode;
    private static String total_amount;
    private static int address_id;

    public static int getAddress_id() {
        return address_id;
    }

    public static void setAddress_id(int address_id) {
        EditAddressData.address_id = address_id;
    }

    public static String getReciever() {
        return reciever;
    }

    public static void setReciever(String reciever) {
        EditAddressData.reciever = reciever;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        EditAddressData.address = address;
    }

    public static String getLandmark() {
        return landmark;
    }

    public static void setLandmark(String landmark) {
        EditAddressData.landmark = landmark;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        EditAddressData.state = state;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        EditAddressData.city = city;
    }

    public static String getPincode() {
        return pincode;
    }

    public static void setPincode(String pincode) {
        EditAddressData.pincode = pincode;
    }

    public static String getTotal_amount() {
        return total_amount;
    }

    public static void setTotal_amount(String total_amount) {
        EditAddressData.total_amount = total_amount;
    }

    public static String getContact() {
        return contact;
    }

    public static void setContact(String contact) {
        EditAddressData.contact = contact;
    }

    public EditAddressData() {

    }


    public EditAddressData(String reciever, String address, String landmark, String state, String city, String pincode, String contact, int address_id) {
        this.reciever = reciever;
        this.address = address;
        this.landmark = landmark;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.contact = contact;
        this.address_id=address_id;
    }

    private static String contact;


}
