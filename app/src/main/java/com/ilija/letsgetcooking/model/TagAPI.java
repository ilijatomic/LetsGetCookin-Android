package com.ilija.letsgetcooking.model;

import java.util.List;

/**
 * JSON model for tags call
 * <p/>
 * Created by Ilija on 7/27/2016.
 */
public class TagAPI {

    private boolean success;
    private List<Tag> tag_categories;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Tag> getTag_categories() {
        return tag_categories;
    }

    public void setTag_categories(List<Tag> tag_categories) {
        this.tag_categories = tag_categories;
    }
}
