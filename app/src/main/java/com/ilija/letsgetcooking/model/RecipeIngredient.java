package com.ilija.letsgetcooking.model;

import io.realm.RealmObject;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class RecipeIngredient extends RealmObject {

    private int id;
    private String quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
