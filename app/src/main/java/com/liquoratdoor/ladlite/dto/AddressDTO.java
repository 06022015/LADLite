package com.liquoratdoor.ladlite.dto;

/**
 * Created by ashqures on 10/20/16.
 */
public class AddressDTO extends BaseDTO{


    private String address;
    private String street;
    private String landMark;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String latitude;
    private String longitude;
    private String mobile;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getCompleteAddress(){
        return "Address: "+this.address+", "+this.street+", landmark: "+this.landMark+", "+
                this.country+", "+this.state+", "+this.country+" - "+this.zipcode;
    }

    @Override
    public String toString() {
        return "Address: "+this.address+", "+this.street+", landmark: "+this.landMark+", ";
    }
}
