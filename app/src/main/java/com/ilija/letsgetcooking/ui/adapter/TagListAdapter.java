package com.ilija.letsgetcooking.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ilija.letsgetcooking.R;
import com.ilija.letsgetcooking.model.InnerTag;
import com.ilija.letsgetcooking.model.Tag;

import java.util.List;

/**
 * Created by Ilija on 7/31/2016.
 */
public class TagListAdapter extends ArrayAdapter<Tag> {

    private Context context;
    private List<Tag> tags;
    private LayoutInflater inflater;

    public TagListAdapter(Context context, List<Tag> objects) {
        super(context, -1, objects);
        this.context = context;
        this.tags = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.lv_item_tag, parent, false);
        Tag tag = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.tag_name);
        name.setText(tag.getName());
        Spinner innerName = (Spinner) view.findViewById(R.id.tag_spinner);
        ArrayAdapter<InnerTag> innerTagArrayAdapter = new ArrayAdapter<InnerTag>(context, android.R.layout.simple_spinner_item, tag.getTags());
        innerTagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        innerName.setAdapter(innerTagArrayAdapter);

        return view;
    }

    @Override
    public Tag getItem(int position) {
        return tags.get(position);
    }
}
