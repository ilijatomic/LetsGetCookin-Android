package com.ilija.letsgetcooking.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.model.ShoppingCart;

import java.util.List;

/**
 * Created by Ilija on 7/31/2016.
 */
public class ShoppingListAdapter extends ArrayAdapter<ShoppingCart> {

    private Context context;
    private List<ShoppingCart> ingredients;
    private LayoutInflater inflater;

    public ShoppingListAdapter(Context context, List<ShoppingCart> objects) {
        super(context, -1, objects);
        this.context = context;
        this.ingredients = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.lv_item_shopping_cart, parent, false);

        ShoppingCart shoppingCart = getItem(position);
        Log.d("Shopping cart", "Ingredient: " + shoppingCart.getIngredient());
        String sName = shoppingCart.getIngredient();
        String sQuantity = shoppingCart.getQuantity();

        TextView name = (TextView) view.findViewById(R.id.shopping_ingredient);
        name.setText(sName);
        TextView quantity = (TextView) view.findViewById(R.id.shopping_ingredient_quantity);
        quantity.setText(sQuantity);

        return view;
    }

    @Override
    public ShoppingCart getItem(int position) {
        return ingredients.get(position);
    }
}
