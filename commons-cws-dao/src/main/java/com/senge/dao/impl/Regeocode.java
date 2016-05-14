package com.clear.dao.impl;

/**
 * Created by Administrator on 2015/6/28.
 */
public class Regeocode {

    private String formatted_address;

    private AddressComponent addressComponent;

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
