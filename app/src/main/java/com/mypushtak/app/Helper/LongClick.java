package com.mypushtak.app.Helper;

import com.mypushtak.app.Singleton.Delivery_Address;

public class LongClick {
    private Delivery_Address deliveryAddress;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Delivery_Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Delivery_Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

}
