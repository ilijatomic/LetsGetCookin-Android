package com.ilija.letsgetcooking.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.activities.adapter.IngredientListAdapter;
import com.ilija.letsgetcooking.database.DBHelper;
import com.ilija.letsgetcooking.model.Recipe;
import com.ilija.letsgetcooking.model.RecipeIngredient;
import com.ilija.letsgetcooking.model.ShoppingCart;
import com.ilija.letsgetcooking.model.Step;
import com.ilija.letsgetcooking.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Sort;

/**
 * Details of chosen recipe
 * <p/>
 * Created by ilija.tomic on 7/27/2016.
 */
public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    private Recipe recipe;
    private View header;
    private ListView lvIngredients;
    private IngredientListAdapter ingredientListAdapter;
    private List<RecipeIngredient> ingredients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvIngredients = (ListView) findViewById(R.id.recipe_details);
        header = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_header_recipe, null, false);
        lvIngredients.addHeaderView(header);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.RECIPE_DETAILS_EXTRA, 0);

        recipe = DBHelper.getInstance().getRecipeById(id);
        if (recipe != null) {
            ingredients = recipe.getIngredients();
            ingredientListAdapter = new IngredientListAdapter(this, ingredients);
            lvIngredients.setAdapter(ingredientListAdapter);
            showRecipeDetails();
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.add_to_shopping_list_dialog_title);
            builder.setMessage(R.string.add_to_shopping_list_dialog_message);
            builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
                    intent.putExtra(Constants.RECIPE_DETAILS_EXTRA, recipe.getId());
                    startActivity(intent);
                }
            });
            builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
                    intent.putExtra(Constants.RECIPE_DETAILS_EXTRA, -1);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showRecipeDetails() {

        ImageView imageView = (ImageView) findViewById(R.id.recipe_image_background);
        Picasso.with(this).load(recipe.getImage_file_name()).into(imageView);

        TextView title = (TextView) header.findViewById(R.id.r_title);
        title.setText(recipe.getTitle());
        TextView difficulty = (TextView) header.findViewById(R.id.r_difficulty);
        difficulty.setText(getString(R.string.difficulty).concat(String.valueOf(recipe.getDifficulty())));
        TextView time = (TextView) header.findViewById(R.id.r_time);
        time.setText(getString(R.string.time).concat(String.valueOf(recipe.getPreparation_time())));
        TextView likes = (TextView) header.findViewById(R.id.r_likes);
        likes.setText(getString(R.string.likes).concat(String.valueOf(recipe.getLikes())));
        TextView servingSize = (TextView) header.findViewById(R.id.r_serving_size);
        servingSize.setText(getString(R.string.serving_size).concat(String.valueOf(recipe.getDefault_serving_size())));
        TextView needs = (TextView) header.findViewById(R.id.r_needs);
        needs.setText(getString(R.string.utensils).concat(recipe.getUtensils()));

        TextView tags = (TextView) header.findViewById(R.id.r_tags);
        tags.setText(DBHelper.getInstance().getRecipeTags(recipe.getTags()));

        TextView steps = (TextView) header.findViewById(R.id.r_steps);
        recipe.getSteps().sort("seq_num", Sort.ASCENDING);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Steps: ").append("\n");
        for (Step temp : recipe.getSteps()) {
            stringBuilder.append("\t").append("- ").append(temp.getText()).append("\n");
        }
        steps.setText(stringBuilder.toString());


    }
}
