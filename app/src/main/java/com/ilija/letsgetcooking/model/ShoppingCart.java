package com.ilija.letsgetcooking.model;

import io.realm.RealmObject;

/**
 * Created by Ilija on 7/30/2016.
 */
public class ShoppingCart extends RealmObject {

    private String ingredient;
    private String quantity;

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
