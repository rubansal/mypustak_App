package com.mypushtak.app.Singleton;

import java.util.ArrayList;
import java.util.List;

public class OrderBookDetails {

    private  int book_id;
    private int quantity;
    private static List<OrderBookDetails> myOrders=new ArrayList<OrderBookDetails>();

    public static List<OrderBookDetails> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(List<OrderBookDetails> myOrders) {
        this.myOrders = myOrders;
    }

    public OrderBookDetails() {
    }


    public int getBook_id() {

        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderBookDetails(int book_id, int quantity) {
        this.book_id = book_id;
        this.quantity = quantity;
    }


}
