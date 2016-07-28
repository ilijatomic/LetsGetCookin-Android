package com.ilija.letsgetcooking.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class Recipe extends RealmObject {

    private String id;
    private String title;
    private String image_file_name;
    private String image_size;
    private int difficulty;
    private int default_serving_size;
    private int preparation_time;
    private String utensils;
    private int likes;
    private int is_featured;

    private RealmList<Step> steps;
    private RealmList<RecipeTag> tags;
    private RealmList<RecipeIngredient> ingredientRecipes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }

    public String getImage_size() {
        return image_size;
    }

    public void setImage_size(String image_size) {
        this.image_size = image_size;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDefault_serving_size() {
        return default_serving_size;
    }

    public void setDefault_serving_size(int default_serving_size) {
        this.default_serving_size = default_serving_size;
    }

    public int getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(int preparation_time) {
        this.preparation_time = preparation_time;
    }

    public String getUtensils() {
        return utensils;
    }

    public void setUtensils(String utensils) {
        this.utensils = utensils;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getIs_featured() {
        return is_featured;
    }

    public void setIs_featured(int is_featured) {
        this.is_featured = is_featured;
    }

    public RealmList<Step> getSteps() {
        return steps;
    }

    public void setSteps(RealmList<Step> steps) {
        this.steps = steps;
    }

    public RealmList<RecipeTag> getTags() {
        return tags;
    }

    public void setTags(RealmList<RecipeTag> tags) {
        this.tags = tags;
    }

    public RealmList<RecipeIngredient> getIngredientRecipes() {
        return ingredientRecipes;
    }

    public void setIngredientRecipes(RealmList<RecipeIngredient> ingredientRecipes) {
        this.ingredientRecipes = ingredientRecipes;
    }
}
