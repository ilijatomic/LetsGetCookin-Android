package com.ilija.letsgetcooking.database;

import com.ilija.letsgetcooking.model.Ingredient;
import com.ilija.letsgetcooking.model.Recipe;
import com.ilija.letsgetcooking.model.Tag;

import java.util.List;

import io.realm.Realm;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class DBHelper {

    private static final int DEFAULT_OFFSET_THRESHOLD = 8;

    private static DBHelper instance;


    private List<Recipe> recipes;

    public static DBHelper getInstance() {
        if (instance == null)
            instance = new DBHelper();
        return instance;
    }

    public boolean checkRecipe() {
        return Realm.getDefaultInstance().where(Recipe.class).findFirst() != null;
    }

    public void loadRecipes() {
        recipes = Realm.getDefaultInstance().where(Recipe.class).findAll();
    }

    public boolean addRecipes(List<Recipe> recipes, int offset) {

        int endOffset = (offset + 1) * DEFAULT_OFFSET_THRESHOLD;
        for (int startOffset = offset * DEFAULT_OFFSET_THRESHOLD; startOffset < endOffset; startOffset++) {
            if (startOffset < this.recipes.size()) {
                recipes.add(startOffset, this.recipes.get(startOffset));
            }
        }
        return true;
    }

    public List<Ingredient> getIngredients() {
        return Realm.getDefaultInstance().where(Ingredient.class).findAll();
    }

    public void insertTags(List<Tag> tags) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(tags);
        realm.commitTransaction();
        realm.close();
    }

    public void insertIngredients(List<Ingredient> ingredients) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(ingredients);
        realm.commitTransaction();
        realm.close();
    }

    public void insertRecipes(List<Recipe> recipes) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recipes);
        realm.commitTransaction();
        realm.close();
    }
}
