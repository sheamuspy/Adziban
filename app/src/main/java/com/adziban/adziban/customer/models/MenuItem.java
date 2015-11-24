package com.adziban.adziban.customer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sheamus on 24/11/2015.
 */
public class MenuItem {
    public int fId;
    public String foodName;
    public double foodPrice;
    public String foodPortion;

    public MenuItem(JSONObject object){
        try {

            fId = object.getInt("foodID");

            foodName = object.getString("foodName");
            foodPrice = object.getDouble("foodPrice");
            foodPortion = object.getString("foodPortion");

        }catch (JSONException e){

        }
        System.out.println("constructor "+ this.fId);
    }

    /*
        Convert JSONArray of menuItems into ArrayList
     */

    public static ArrayList<MenuItem> fromJson(JSONArray jsonObjects) {

        ArrayList<MenuItem> menu = new ArrayList<MenuItem>();

        for (int i = 0; i < jsonObjects.length(); i++) {

            try {
                MenuItem newMenuItem = new MenuItem(jsonObjects.getJSONObject(i));
                System.out.println("from new menu item class " + newMenuItem.foodPortion);
                menu.add(newMenuItem);
                System.out.println("from inside try catch class " + menu.get(0).foodPortion);
            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
        System.out.println("from the the method to convert class "+ menu.get(0).foodPortion);
        return menu;

    }
}

