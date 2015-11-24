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
            this.fId = object.getInt("foodID");
            this.foodName = object.getString("foodName");
            this.foodPrice = object.getDouble("foodPrice");
            this.foodPortion = object.getString("foodPortion");

        }catch (JSONException e){

        }
    }

    /*
        Convert JSONArray of menuItems into ArrayList
     */

    public static ArrayList<MenuItem> fromJson(JSONArray jsonObjects) {

        ArrayList<MenuItem> users = new ArrayList<MenuItem>();

        for (int i = 0; i < jsonObjects.length(); i++) {

            try {

                users.add(new MenuItem(jsonObjects.getJSONObject(i)));

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }

        return users;

    }
}

