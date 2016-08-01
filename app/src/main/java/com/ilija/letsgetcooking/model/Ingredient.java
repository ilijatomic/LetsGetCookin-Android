package com.ilija.letsgetcooking.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class Ingredient extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String quantity_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity_type() {
        return quantity_type;
    }

    public void setQuantity_type(String quantity_type) {
        this.quantity_type = quantity_type;
    }

    @Override
    public String toString() {
        return name;
    }
}
