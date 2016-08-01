package com.ilija.letsgetcooking.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.database.DBHelper;
import com.ilija.letsgetcooking.model.RecipeIngredient;

import java.util.List;

/**
 * Adapter for ListView of ingredient for recipe
 *
 * Created by ilija.tomic on 7/27/2016.
 */
public class IngredientListAdapter extends ArrayAdapter<RecipeIngredient> {

    private Context context;
    private List<RecipeIngredient> ingredients;
    private LayoutInflater inflater;

    public IngredientListAdapter(Context context, List<RecipeIngredient> objects) {
        super(context, -1, objects);
        this.context = context;
        this.ingredients = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.lv_item_recipe_ingredients, parent, false);

        RecipeIngredient recipeIngredient = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.recipe_ingredient);
        name.setText(DBHelper.getInstance().getIngredientById(recipeIngredient.getId()).getName());
        TextView quantity = (TextView) view.findViewById(R.id.recipe_ingredient_quantity);
        quantity.setText(recipeIngredient.getQuantity());

        return view;
    }

    @Override
    public RecipeIngredient getItem(int position) {
        return ingredients.get(position);
    }
}
