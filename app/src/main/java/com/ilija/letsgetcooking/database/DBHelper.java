package com.ilija.letsgetcooking.database;

import com.ilija.letsgetcooking.model.Ingredient;
import com.ilija.letsgetcooking.model.InnerTag;
import com.ilija.letsgetcooking.model.Recipe;
import com.ilija.letsgetcooking.model.RecipeIngredient;
import com.ilija.letsgetcooking.model.RecipeTag;
import com.ilija.letsgetcooking.model.ShoppingCart;
import com.ilija.letsgetcooking.model.Tag;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class DBHelper {

    private static final int DEFAULT_OFFSET_THRESHOLD = 50;

    private static DBHelper instance;

    private RealmResults<Recipe> recipesResult;

    public static DBHelper getInstance() {
        if (instance == null)
            instance = new DBHelper();
        return instance;
    }

    public boolean checkRecipe() {
        return Realm.getDefaultInstance().where(Recipe.class).findFirst() != null;
    }

    public void loadRecipes() {
        recipesResult = Realm.getDefaultInstance().where(Recipe.class).findAll();
    }

    public boolean addRecipes(List<Recipe> recipes, int offset, Map<Integer, Integer> searchTags) {

        for (Integer integerId : searchTags.values()) {
            recipesResult = recipesResult.where().equalTo("tags.id", integerId).findAll();
        }

        int endOffset = (offset + 1) * DEFAULT_OFFSET_THRESHOLD;
        for (int startOffset = offset * DEFAULT_OFFSET_THRESHOLD; startOffset < endOffset; startOffset++) {
            if (startOffset < this.recipesResult.size()) {
                recipes.add(startOffset, this.recipesResult.get(startOffset));
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean checkIngredients() {
        return Realm.getDefaultInstance().where(Ingredient.class).findFirst() != null;
    }

    public void insertTags(List<Tag> tags) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(tags);
        realm.commitTransaction();
        realm.close();
        addEmptyInnerTag();
    }

    public void addEmptyInnerTag() {
        Realm realm = Realm.getDefaultInstance();
        List<Tag> tags = getTags();
        for (Tag temp : tags) {
            realm.beginTransaction();
            InnerTag innerTag = new InnerTag();
            int id = realm.where(InnerTag.class).max("id").intValue() + 1;
            innerTag.setId(id);
            innerTag.setName("");
            realm.copyToRealm(innerTag);
            temp.getTags().add(0, innerTag);
            realm.commitTransaction();
        }
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
        realm.copyToRealm(recipes);
        realm.commitTransaction();
        realm.close();
    }

    public Recipe getRecipeById(int id) {
        return Realm.getDefaultInstance().where(Recipe.class).equalTo("id", id).findFirst();
    }

    public Ingredient getIngredientById(int id) {
        return Realm.getDefaultInstance().where(Ingredient.class).equalTo("id", id).findFirst();
    }

    public InnerTag getTagById(int id) {
        return Realm.getDefaultInstance().where(InnerTag.class).equalTo("id", id).findFirst();
    }

    public String getRecipeTags(RealmList<RecipeTag> tags) {
        StringBuilder stringBuilder = new StringBuilder();

        for (RecipeTag temp : tags) {
            stringBuilder.append(getTagById(temp.getId()).getName()).append(" | ");
        }

        return stringBuilder.toString();
    }

    public void addIngredientsToShoppingCart(List<RecipeIngredient> ingredients) {
        Realm realm = Realm.getDefaultInstance();
        for (RecipeIngredient temp : ingredients) {
            realm.beginTransaction();
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setIngredient(getIngredientById(temp.getId()).getName());
            shoppingCart.setQuantity(temp.getQuantity());
            realm.copyToRealm(shoppingCart);
            realm.commitTransaction();
        }
        realm.close();
    }

    public void addIngredientsToShoppingCart(RecipeIngredient ingredient) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ShoppingCart shoppingCart = realm.createObject(ShoppingCart.class);
        shoppingCart.setIngredient(getIngredientById(ingredient.getId()).getName());
        shoppingCart.setQuantity(ingredient.getQuantity());
        realm.commitTransaction();
        realm.close();
    }

    public List<ShoppingCart> getShoppingCart() {
        return Realm.getDefaultInstance().where(ShoppingCart.class).findAll();
    }

    public List<Tag> getTags() {
        return Realm.getDefaultInstance().where(Tag.class).findAll();
    }

    public Tag getTagByInnerTagId(int id) {
        return Realm.getDefaultInstance().where(Tag.class).equalTo("tags.id", id).findFirst();
    }
}
