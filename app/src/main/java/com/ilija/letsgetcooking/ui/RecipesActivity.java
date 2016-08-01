package com.ilija.letsgetcooking.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.database.DBHelper;
import com.ilija.letsgetcooking.model.IngredientAPI;
import com.ilija.letsgetcooking.model.InnerTag;
import com.ilija.letsgetcooking.model.Recipe;
import com.ilija.letsgetcooking.model.RecipesAPI;
import com.ilija.letsgetcooking.model.Tag;
import com.ilija.letsgetcooking.model.TagAPI;
import com.ilija.letsgetcooking.ui.adapter.RecipesListAdapter;
import com.ilija.letsgetcooking.ui.dialog.SearchTagDialog;
import com.ilija.letsgetcooking.utils.Constants;
import com.ilija.letsgetcooking.utils.NetworkUtils;
import com.ilija.letsgetcooking.utils.RESTCall;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lazy loading recipes, storing or updating to database
 *
 * Created by ilija.tomic on 7/27/2016.
 */
public class RecipesActivity extends AppCompatActivity implements RESTCall.DownloadListener, SearchTagDialog.InnerTagSelectListener {

    private static final String TAG = RecipesActivity.class.getSimpleName();
    private static final int DEFAULT_OFFSET_VALUE = 50;

    private int offset = 0;
    private boolean loading = true;

    private ListView lvRecipes;
    private View footer;
    private RecipesListAdapter recipesListAdapter;
    private List<Recipe> recipes = new ArrayList<>();
    private Map<Integer, Integer> searchTags = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvRecipes = (ListView) findViewById(R.id.recipes_lv);
        footer = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer_progress, null, false);
        lvRecipes.addFooterView(footer);

        recipesListAdapter = new RecipesListAdapter(this, recipes);
        lvRecipes.setAdapter(recipesListAdapter);

        lvRecipes.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!loading) {
                    if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                        Log.d(TAG, "Load next set of recipes");
                        loading = true;
                        offset += 1;
                        if (NetworkUtils.checkInternetConnection(getApplicationContext())) {
                            loadRecipesFromInternet(String.valueOf(offset * DEFAULT_OFFSET_VALUE));
                        } else {
                            populateRecipes();
                        }
                    }
                }
            }
        });

        lvRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, recipes.get(position).getTitle());
                Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
                intent.putExtra(Constants.RECIPE_DETAILS_EXTRA, recipes.get(position).getId());
                startActivity(intent);
            }
        });

        checkInternetOrDatabaseExist();

        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.search_fab);
        assert actionButton != null;
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Search button pressed!");
                showSearchDialog();
            }
        });
    }

    private void showSearchDialog() {
        DialogFragment searchDialog = new SearchTagDialog();
        searchDialog.show(getSupportFragmentManager(), "SearchTagDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shopping) {
            Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
            intent.putExtra(Constants.RECIPE_DETAILS_EXTRA, -1);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Calling this method on activity create
     * This is where initial check is happening
     * The logic is to check if there is an active internet connection,
     * in case of true, start lazy loading of recipes,
     * in case of false, start loading from database
     * If there is nothing saved alert user
     */
    private void checkInternetOrDatabaseExist() {
        if (NetworkUtils.checkInternetConnection(this.getApplicationContext())) {
            if (!DBHelper.getInstance().checkIngredients()) {
                loadIngredientFromInternet();
                loadTagsFromInternet();
            }

            loadRecipesFromInternet(String.valueOf(offset * DEFAULT_OFFSET_VALUE));
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
                populateRecipes();
            }
        }
    }

    /**
     * Get recipes form REST api with given offset
     * (Executed in background thread)
     *
     * @param offset offset for lazy loading
     */
    private void loadRecipesFromInternet(String offset) {
        RESTCall restCall = new RESTCall(Constants.GET_OFFSET_RECIPES_URL + offset, RESTCall.RestType.RECIPE, this);
        restCall.start();
    }

    /**
     * Get ingredients form REST api
     * (Executed in background thread)
     */
    private void loadIngredientFromInternet() {
        RESTCall restCall = new RESTCall(Constants.GET_INGREDIENTS_URL, RESTCall.RestType.INGREDIENT, this);
        restCall.start();
    }

    /**
     * Get tags form REST api
     * (Executed in background thread)
     */
    private void loadTagsFromInternet() {
        RESTCall restCall = new RESTCall(Constants.GET_TAG_URL, RESTCall.RestType.TAG, this);
        restCall.start();
    }

    /**
     * Once download is complete, parsing input stream from http connection
     *
     * @param inputStream content from http call
     * @param type        REST type
     */
    @Override
    public void downloadComplete(InputStream inputStream, RESTCall.RestType type) {

        Gson gson = new Gson();
        switch (type) {
            case INGREDIENT:
                // Getting ingredient from input stream and saving into database
                IngredientAPI ingredientAPI = gson.fromJson(new InputStreamReader(inputStream), IngredientAPI.class);
                DBHelper.getInstance().insertIngredients(ingredientAPI.getIngredients());
                break;
            case RECIPE:
                // Getting recipes from input stream and saving into database
                RecipesAPI recipesAPI = gson.fromJson(new InputStreamReader(inputStream), RecipesAPI.class);
                DBHelper.getInstance().insertRecipes(recipesAPI.getRecipes());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateRecipes();
                    }
                });
                break;
            case TAG:
                // Getting tags from input stream and saving into database
                TagAPI tagAPI = gson.fromJson(new InputStreamReader(inputStream), TagAPI.class);
                DBHelper.getInstance().insertTags(tagAPI.getTag_categories());
                break;
            case ERROR:
            default:
                break;

        }
    }

    /**
     * Populating recipes from database with current offset
     * When end of database is reached, stopping with populating recipes
     * and removing loading spinner
     */
    private void populateRecipes() {
        DBHelper.getInstance().loadRecipes();
        if (!DBHelper.getInstance().addRecipes(recipes, offset, searchTags)) {
            lvRecipes.removeFooterView(footer);
        } else {
            recipesListAdapter.notifyDataSetChanged();
            loading = false;
        }
    }

    @Override
    public void onSelect(InnerTag innerTag) {
        Tag tag = DBHelper.getInstance().getTagByInnerTagId(innerTag.getId());
        Log.d(TAG, "Tag selected: " + tag.getName() + "; Inner tag: " + innerTag.getName());
        if (innerTag.getName().isEmpty() && searchTags.containsKey(tag.getId())) {
            searchTags.remove(tag.getId());
        } else if (!innerTag.getName().isEmpty()) {
            searchTags.put(tag.getId(), innerTag.getId());
        }
    }

    @Override
    public void search() {
        offset = 0;
        recipes.clear();
        populateRecipes();
    }
}
