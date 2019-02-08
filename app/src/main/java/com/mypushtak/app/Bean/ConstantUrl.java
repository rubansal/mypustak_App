package com.mypushtak.app.Bean;

public class ConstantUrl {

    public static String URL="http://192.168.0.109:8080/";
    public static int full_product_view_price;

    public int total_handling_cost;

    public int getTotal_handling_cost() {
        return total_handling_cost;
    }

    public void setTotal_handling_cost(int total_handling_cost) {
        this.total_handling_cost = total_handling_cost;
    }

    public static int getFull_product_view_price() {
        return full_product_view_price;
    }

    public static void setFull_product_view_price(int full_product_view_price) {
        ConstantUrl.full_product_view_price = full_product_view_price;
    }
}
