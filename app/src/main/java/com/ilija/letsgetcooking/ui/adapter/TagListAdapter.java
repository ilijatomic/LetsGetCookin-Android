package com.ilija.letsgetcooking.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.model.InnerTag;
import com.ilija.letsgetcooking.model.Tag;
import com.ilija.letsgetcooking.ui.dialog.SearchTagDialog;

import java.util.List;

/**
 * Created by Ilija on 7/31/2016.
 */
public class TagListAdapter extends ArrayAdapter<Tag> {

    private Context context;
    private List<Tag> tags;
    private LayoutInflater inflater;
    private SearchTagDialog.InnerTagSelectListener innerTagSelectListener;

    public TagListAdapter(Context context, List<Tag> objects, SearchTagDialog.InnerTagSelectListener innerTagSelectListener) {
        super(context, -1, objects);
        this.context = context;
        this.tags = objects;
        this.innerTagSelectListener = innerTagSelectListener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.lv_item_tag, parent, false);
        final Tag tag = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.tag_name);
        name.setText(tag.getName());
        Spinner innerName = (Spinner) view.findViewById(R.id.tag_spinner);
        ArrayAdapter<InnerTag> innerTagArrayAdapter = new ArrayAdapter<InnerTag>(context, android.R.layout.simple_spinner_item, tag.getTags());
        innerTagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        innerName.setAdapter(innerTagArrayAdapter);
        innerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InnerTag innerTag = tag.getTags().get(position);
                Tag test = innerTag.getTag();
                innerTagSelectListener.onSelect(innerTag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public Tag getItem(int position) {
        return tags.get(position);
    }
}
