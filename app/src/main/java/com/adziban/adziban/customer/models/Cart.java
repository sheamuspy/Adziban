package com.adziban.adziban.customer.models;

import java.util.ArrayList;

/**
 * Created by sheamus on 24/11/2015.
 */
public class Cart {
    private static Cart ourInstance = new Cart();

    ArrayList<MenuItem> cart;

    public static Cart getInstance() {
        return ourInstance;
    }

    private Cart() {
        cart = new ArrayList<>();
    }

    public void addToCart(MenuItem menuItem){
        cart.add(menuItem);
    }

    public ArrayList<MenuItem> getCart(){
        return cart;
    }
}
