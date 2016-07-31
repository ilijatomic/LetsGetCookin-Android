package com.ilija.letsgetcooking.model;

import io.realm.RealmObject;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class RecipeTag extends RealmObject {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
