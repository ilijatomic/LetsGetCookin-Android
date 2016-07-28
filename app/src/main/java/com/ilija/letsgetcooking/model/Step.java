package com.ilija.letsgetcooking.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class Step extends RealmObject {

    private String text;
    private int seq_num;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSeq_num() {
        return seq_num;
    }

    public void setSeq_num(int seq_num) {
        this.seq_num = seq_num;
    }
}
