package com.ilija.letsgetcooking.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Loading and caching images of recipe
 *
 * Created by ilija.tomic on 7/27/2016.
 */
public class ImageUtils {

    private static int RECIPE_IMAGE_WIDTH = 300;
    private static int RECIPE_IMAGE_HEIGHT = 300;

    /**
     * Loading image into image view from url (downloading or using cached)
     * resizing and using placeholder if url is null
     *
     * @param context   activity context
     * @param imageView image view for loaded image
     * @param url       url from witch image is download if it is not cached
     */
    public static void loadImage(Context context, ImageView imageView, String url) {
        Picasso.with(context).load(url).resize(RECIPE_IMAGE_WIDTH, RECIPE_IMAGE_HEIGHT).centerCrop().into(imageView);
    }
}
