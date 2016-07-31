package com.ilija.letsgetcooking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.ui.adapter.ShoppingListAdapter;
import com.ilija.letsgetcooking.database.DBHelper;
import com.ilija.letsgetcooking.model.Recipe;
import com.ilija.letsgetcooking.model.ShoppingCart;
import com.ilija.letsgetcooking.utils.Constants;

import java.util.List;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class ShoppingListActivity extends AppCompatActivity {

    private Recipe recipe;
    private ListView lvIngredients;
    private ShoppingListAdapter shoppingListAdapter;
    private List<ShoppingCart> shoppingCarts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvIngredients = (ListView) findViewById(R.id.shopping_cart_lv);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.RECIPE_DETAILS_EXTRA, 0);

        if (id > 0) {
            recipe = DBHelper.getInstance().getRecipeById(id);
            DBHelper.getInstance().addIngredientsToShoppingCart(recipe.getIngredients());
        }

        shoppingCarts = DBHelper.getInstance().getShoppingCart();
        shoppingListAdapter = new ShoppingListAdapter(this, shoppingCarts);
        lvIngredients.setAdapter(shoppingListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_shopping).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
