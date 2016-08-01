package com.ilija.letsgetcooking.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.database.DBHelper;
import com.ilija.letsgetcooking.model.InnerTag;
import com.ilija.letsgetcooking.model.Tag;
import com.ilija.letsgetcooking.ui.adapter.TagListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilija on 7/31/2016.
 */
public class SearchTagDialog extends DialogFragment {

    private InnerTagSelectListener searchListener;
    private ListView lvTags;
    private TagListAdapter tagListAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = layoutInflater.inflate(R.layout.dialog_search_tag, null, false);
        lvTags = (ListView) dialogView.findViewById(R.id.tag_lv);
        tagListAdapter = new TagListAdapter(getActivity(), DBHelper.getInstance().getTags(), searchListener);
        lvTags.setAdapter(tagListAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.search_recipes);
        builder.setView(dialogView);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            searchListener = (InnerTagSelectListener) activity;
        } catch (ClassCastException e) {
            Log.d(SearchTagDialog.class.getSimpleName(), e.getMessage());
        }
    }

    public interface InnerTagSelectListener {
        void onSelect(InnerTag tag);
    }
}
