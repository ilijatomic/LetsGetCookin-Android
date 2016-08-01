package com.ilija.letsgetcooking.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.database.DBHelper;
import com.ilija.letsgetcooking.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilija.tomic on 8/1/2016.
 */
public class AddIngredientDialog extends DialogFragment {

    private AutoCompleteTextView autoCompleteTextView;
    private Ingredient ingredient;
    private List<String> ingredientsName = new ArrayList<>();
    private AddListener addListener;

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = layoutInflater.inflate(R.layout.dialog_add_ingreient, null, false);
        autoCompleteTextView = (AutoCompleteTextView) dialogView.findViewById(R.id.actv_ingredient);
        DBHelper.getInstance().getIngredients(ingredientsName);
        ArrayAdapter<String> ingredientArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ingredientsName);
        autoCompleteTextView.setAdapter(ingredientArrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ingredient = DBHelper.getInstance().getIngredientByName(autoCompleteTextView.getText().toString());
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_ingredient);
        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ingredient != null)
                    addListener.add(ingredient);
                getDialog().dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addListener = (AddListener) activity;
        } catch (ClassCastException e) {
            Log.d(SearchTagDialog.class.getSimpleName(), e.getMessage());
        }
    }

    public interface AddListener {
        void add(Ingredient ingredient);
    }
}
