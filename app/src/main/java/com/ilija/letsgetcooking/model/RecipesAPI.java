package com.ilija.letsgetcooking.model;

import java.util.List;

/**
 * JSON model for get recipes call
 * <p/>
 * Created by ilija.tomic on 7/27/2016.
 */
public class RecipesAPI {

    private boolean status;
    private List<Recipe> recipes;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
