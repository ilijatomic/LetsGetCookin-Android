package com.ilija.letsgetcooking.model;

import java.util.List;

/**
 * JSON model for get ingredients call
 *
 * Created by ilija.tomic on 7/27/2016.
 */
public class IngredientAPI {

    private boolean status;
    private List<Ingredient> ingredients;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
