package com.ilija.letsgetcooking.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.activities.adapter.RecipesListAdapter;
import com.ilija.letsgetcooking.database.DBHelper;
import com.ilija.letsgetcooking.model.IngredientAPI;
import com.ilija.letsgetcooking.model.Recipe;
import com.ilija.letsgetcooking.model.RecipesAPI;
import com.ilija.letsgetcooking.model.TagAPI;
import com.ilija.letsgetcooking.utils.Constants;
import com.ilija.letsgetcooking.utils.NetworkUtils;
import com.ilija.letsgetcooking.utils.RESTCall;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Lazy loading recipes, storing or updating to database
 *
 * Created by ilija.tomic on 7/27/2016.
 */
public class RecipesActivity extends Activity implements RESTCall.DownloadListener {

    private static final String TAG = RecipesActivity.class.getSimpleName();

    private ListView lvRecipes;
    private List<Recipe> recipes = new ArrayList<>();
    private RecipesListAdapter recipesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity);

        lvRecipes = (ListView) findViewById(R.id.recipes_lv);
        View footer = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_view_item_progress, null, false);
        lvRecipes.addFooterView(footer);
        recipesListAdapter = new RecipesListAdapter(this, recipes);
        lvRecipes.setAdapter(recipesListAdapter);

        lvRecipes.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "" + firstVisibleItem + visibleItemCount + totalItemCount);
            }
        });

        checkInternetOrDatabaseExist();
    }

    private void checkInternetOrDatabaseExist() {
        if (NetworkUtils.checkInternetConnection(this.getApplicationContext())) {
            if (DBHelper.getInstance().getIngredients().size() == 0) {
                loadIngredientFromInternet();
                loadTagsFromInternet();
            }

            loadRecipesFromInternet(String.valueOf(0));
        } else {
            if (!DBHelper.getInstance().checkRecipe()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.no_internet_dialog_title);
                builder.setMessage(R.string.no_internet_dialog_message);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.try_again_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkInternetOrDatabaseExist();
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                DBHelper.getInstance().loadRecipes();
                populateRecipes(0);
            }
        }
    }

    private void loadRecipesFromInternet(String offset) {
        RESTCall restCall = new RESTCall(Constants.GET_OFFSET_RECIPES_URL + offset, RESTCall.RestType.RECIPE, this);
        restCall.start();
    }

    private void loadIngredientFromInternet() {
        RESTCall restCall = new RESTCall(Constants.GET_INGREDIENTS_URL, RESTCall.RestType.INGREDIENT, this);
        restCall.start();
    }

    private void loadTagsFromInternet() {
        RESTCall restCall = new RESTCall(Constants.GET_TAG_URL, RESTCall.RestType.TAG, this);
        restCall.start();
    }

    @Override
    public void downloadComplete(InputStream inputStream, RESTCall.RestType type) {

        Gson gson = new Gson();
        switch (type) {
            case INGREDIENT:
                IngredientAPI ingredientAPI = gson.fromJson(new InputStreamReader(inputStream), IngredientAPI.class);
                DBHelper.getInstance().insertIngredients(ingredientAPI.getIngredients());
                break;
            case RECIPE:
                RecipesAPI recipesAPI = gson.fromJson(new InputStreamReader(inputStream), RecipesAPI.class);
                DBHelper.getInstance().insertRecipes(recipesAPI.getRecipes());
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.getInstance().loadRecipes();
                        populateRecipes(0);
                    }
                });
                break;
            case TAG:
                TagAPI tagAPI = gson.fromJson(new InputStreamReader(inputStream), TagAPI.class);
                DBHelper.getInstance().insertTags(tagAPI.getTag_categories());
                break;
            default:
                break;

        }
    }

    private void populateRecipes(int offset) {
        DBHelper.getInstance().addRecipes(recipes, offset);
        recipesListAdapter.notifyDataSetChanged();
    }
}
