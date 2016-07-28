package com.ilija.letsgetcooking.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ilija on 7/27/2016.
 */
public class Tag extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private RealmList<InnerTag> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<InnerTag> getTags() {
        return tags;
    }

    public void setTags(RealmList<InnerTag> tags) {
        this.tags = tags;
    }
}
