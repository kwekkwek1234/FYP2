package my.edu.tarc.user.fyp;

import com.google.firebase.database.snapshot.BooleanNode;

public class Address {
    private String receiverName, phoneNo, address,postalCode, area, state,defaultAddress;

    public Address() {
    }

    public Address(String receiverName, String phoneNo, String address, String postalCode, String area, String state,String defaultAddress) {
        this.receiverName = receiverName;
        this.phoneNo = phoneNo;
        this.address = address;
        this.postalCode = postalCode;
        this.area = area;
        this.state = state;
        this.defaultAddress = defaultAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
