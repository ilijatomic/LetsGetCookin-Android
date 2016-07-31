package com.ilija.letsgetcooking.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.model.Recipe;
import com.ilija.letsgetcooking.utils.ImageUtils;

import java.util.List;

/**
 * Adapter for list view of recipes
 * <p/>
 * Created by ilija.tomic on 7/27/2016.
 */
public class RecipesListAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private List<Recipe> recipes;
    private LayoutInflater inflater;

    public RecipesListAdapter(Context context, List<Recipe> objects) {
        super(context, -1, objects);
        this.context = context;
        this.recipes = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Recipe recipe = getItem(position);

        View view = inflater.inflate(R.layout.lv_item_recipies, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.recipe_image);
        ImageUtils.loadImage(context, imageView, recipe.getImage_file_name());
        TextView tvName = (TextView) view.findViewById(R.id.recipe_name);
        tvName.setText(recipe.getTitle());
        TextView tvLikes = (TextView) view.findViewById(R.id.recipe_likes);
        tvLikes.setText(String.valueOf(recipe.getLikes()));
        TextView tvTime = (TextView) view.findViewById(R.id.recipe_time);
        tvTime.setText(String.valueOf(recipe.getPreparation_time()));

        return view;
    }

    @Override
    public Recipe getItem(int position) {
        return recipes.get(position);
    }
}
